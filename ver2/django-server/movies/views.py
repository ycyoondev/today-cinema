from django.http.response import JsonResponse
from django.db.models import Max, Min
from rest_framework.decorators import api_view, permission_classes
from rest_framework.permissions import AllowAny
from rest_framework.response import Response

from .serializers import (
    MovieDatabaseSerializer,
    MovieDetailSerializer, MovieMainSerializer,
)
from .models import Movie, Genre
from _env import ENV_TMDB_KEY
from .recommend_ml import find_sim_movie

import requests
import random
import numpy as np 

BASE_URL = 'https://api.themoviedb.org/3'

def add_movie(movies_dict):
    for movie_dict in movies_dict:
        movie_serializer = MovieDatabaseSerializer(data=movie_dict)
        if movie_serializer.is_valid(raise_exception=True):
            dict_id = movie_dict.get('id')
            videos_url = BASE_URL + f'/movie/{dict_id}/videos'
            videos_query = {
                'api_key': ENV_TMDB_KEY,
                'language': 'ko-KR',
            }
            videos_response = requests.get(videos_url, params=videos_query)
            videos_response_dictionary = videos_response.json()
            videos_dict = videos_response_dictionary.get('results')
            main_video = {}
            teaser_video = {}
            for video_dict in videos_dict:
                video_type = video_dict.get('type')
                if video_type == 'Trailer':
                    main_video = video_dict
                    break

                elif video_type == 'Teaser':
                    teaser_video = video_dict
                    
            else:
                main_video = teaser_video
                if not main_video:
                    main_video = {'key': 'no video'}
            
            movie_obj = movie_serializer.save(video_key=main_video.get('key'))
            for genre_id in movie_dict['genre_ids']:
                genre = Genre.objects.get(tmdb_id=genre_id)
                movie_obj.genres.add(genre)
    
    if movies_dict:
        print(f'{len(movies_dict)} movies added.')


@api_view(['GET'])
@permission_classes([AllowAny])
def best(request):
    best_movies = Movie.objects.order_by('-tmdb_rating')[:20]
    best_movies_serializer = MovieMainSerializer(best_movies, many=True)
    max_id_genre = Genre.objects.all().aggregate(Max('id'))['id__max']
    min_id_genre = Genre.objects.all().aggregate(Min('id'))['id__min']
    genre1 = Genre.objects.get(id=random.randint(min_id_genre, max_id_genre))
    genre2 = Genre.objects.get(id=random.randint(min_id_genre, max_id_genre))
    while genre1.id == max_id_genre - 3:
        genre1 = Genre.objects.get(id=random.randint(min_id_genre, max_id_genre))

    while genre1 == genre2 or genre2.id == 16:
        genre2 = Genre.objects.get(id=random.randint(min_id_genre, max_id_genre))
        
    genre_best_movies1 = genre1.movies.order_by('-tmdb_rating')[:20]
    genre_best_movies2 = genre2.movies.order_by('-tmdb_rating')[:20]
    genre1_best_movies_serializer = MovieMainSerializer(genre_best_movies1, many=True)
    genre2_best_movies_serializer = MovieMainSerializer(genre_best_movies2, many=True)
    context = {
        'best': best_movies_serializer.data,
        'genre_best1': {
            'genre': genre1.name,
            'data': genre1_best_movies_serializer.data,
        },
        'genre_best2': {
            'genre': genre2.name,
            'data': genre2_best_movies_serializer.data,
        },
    }
    return Response(context)


@api_view(['GET'])
@permission_classes([AllowAny])
def genre_best(request, genre_id):
    genre = Genre.objects.get(id=genre_id)
    movies = genre.movies.order_by('-tmdb_rating')[:20]
    movies_serializer = MovieMainSerializer(movies, many=True)
    return Response(movies_serializer.data)


@api_view(['GET'])
@permission_classes([AllowAny])
def movie_detail(request, movie_id):
    movie = Movie.objects.get(id=movie_id)
    movie_serializer = MovieDetailSerializer(movie)
    return Response(movie_serializer.data)


@api_view(['POST'])
def movie_wish(request, movie_id):
    movie = Movie.objects.get(id=movie_id)
    user = request.user
    if movie.wish_users.filter(id=user.id).exists():
        movie.wish_users.remove(user)
        return Response({'notice': 'Wish movie removed.'})
    else:
        movie.wish_users.add(user)
        return Response({'notice': 'Wish movie added.'})


@api_view(['POST'])
def movie_recommend(request, movie_id):
    movie = Movie.objects.get(id=movie_id)
    user = request.user
    if movie.recommend_users.filter(id=user.id).exists():
        movie.recommend_users.remove(user)
        return Response({'notice': 'Recommend movie removed.'})
    else:
        movie.recommend_users.add(user)
        return Response({'notice': 'Recommend movie added.'})


def tmdb_recommendation(movie_id):
    movie = Movie.objects.get(id=movie_id)
    recommendation_url = BASE_URL + f'/movie/{movie.tmdb_id}/recommendations'
    recommendation_query = {
        'api_key': ENV_TMDB_KEY,
        'language': 'ko-KR',
        'page': 1,
    }
    recommendation_response = requests.get(recommendation_url, params=recommendation_query)
    recommendation_response_dictionary = recommendation_response.json()
    recommendation_dict = recommendation_response_dictionary.get('results')
    return recommendation_dict


@api_view(['GET'])
@permission_classes([AllowAny])
def movie_recommendation(request, movie_id):
    return JsonResponse(tmdb_recommendation(movie_id), safe=False)
    

@api_view(['GET', 'POST'])
@permission_classes([AllowAny])
def tournament(request, number):
    if request.method == 'GET':
        movies_number = number
        if movies_number < 11:
            movies_number = 4
        elif movies_number > 64:
            movies_number = 64
        else:
            cnt = -1
            while movies_number:
                movies_number //= 2
                cnt += 1
                
            movies_number = 2 ** cnt

        max_id_movie = Movie.objects.all().aggregate(Max('id'))['id__max']
        min_id_movie = Movie.objects.all().aggregate(Min('id'))['id__min']
        random_ids_movie = random.sample(range(min_id_movie, max_id_movie+1), movies_number)
        random_movies = []
        for random_id_movie in random_ids_movie:
            random_movie = Movie.objects.get(id=random_id_movie)
            random_movies.append(random_movie)
        movies_serializer = MovieMainSerializer(random_movies, many=True)
        return Response(movies_serializer.data)
    
    elif request.method == 'POST':
        win_movie = Movie.objects.get(id=number)
        if request.user.is_authenticated:
            request.user.recommend_movies.add(win_movie)

        temp_id = win_movie.tmdb_id
        similar_movies = find_sim_movie(temp_id) 
        machine_learning_recommend_movies_list = list(np.array(similar_movies[['id']]['id'].tolist()))
        if machine_learning_recommend_movies_list:
            recommend_movies = []
            for recommend_movie_tmdb_id in machine_learning_recommend_movies_list:
                detail_url = BASE_URL + f'/movie/{recommend_movie_tmdb_id}'
                detail_query = {
                    'api_key': ENV_TMDB_KEY,
                    'language': 'ko-KR',
                }
                recommendation_response = requests.get(detail_url, params=detail_query)
                recommendation_dict = recommendation_response.json()
                genre_ids = []
                for genre in recommendation_dict['genres']:
                    genre_ids.append(genre['id'])
                recommendation_dict['genre_ids'] = genre_ids
                recommend_movies.append(recommendation_dict)
            print('성공')
            return Response(recommend_movies) # 임시
        else:
            recommend_movies = tmdb_recommendation(number)[:5]

        movie_to_add = []
        for recommend_movie in recommend_movies:
            recommend_movie['vote_average'] = round(recommend_movie['vote_average'], 1)
            if not Movie.objects.filter(tmdb_id=recommend_movie['id']).exists():
                movie_to_add.append(recommend_movie)

        add_movie(movie_to_add)
        recommend_movie_objs = []
        for recommend_movie in recommend_movies:
            recommend_movie_obj = Movie.objects.get(tmdb_id=recommend_movie['id'])
            recommend_movie_objs.append(recommend_movie_obj)
            movies_serializer = MovieMainSerializer(recommend_movie_objs, many=True)
            if request.user.is_authenticated:
                recommend_movie_obj.recommend_users.add(request.user)
        
        return Response(movies_serializer.data)

"""
### views.py
from rest_framework.response import Response
from .models import Movie, Genre
from .recommend_ml import find_sim_movie
import requests
import random
import numpy as np 

BASE_URL = 'https://api.themoviedb.org/3'

# 기반 영화를 받아서 추천영화를 반환 함수
def movie_recommendation(movie_id):
	# ML 모델에 넣기위한 id 추출
	base_movie = Movie.objects.get(id=movie_id)
	model_input_id = base_movie.tmdb_id

	# ML 모델을 통해 추천 결과 획득
    raw_movies = find_sim_movie(model_input_id) 
    
    # 결과 후처리
    machine_learning_recommend_movies_list = list(np.array(raw_movies[['id']]['id'].tolist()))
    recommend_movies = []
    for recommend_movie_tmdb_id in machine_learning_recommend_movies_list:
        detail_url = BASE_URL + f'/movie/{recommend_movie_tmdb_id}'
        detail_query = {
          'api_key': ENV_TMDB_KEY,
          'language': 'ko-KR',
        }
        recommendation_response = requests.get(detail_url, params=detail_query)
        recommendation_dict = recommendation_response.json()
        recommend_movies.append(recommendation_dict)
    return Response(recommend_movies)
"""