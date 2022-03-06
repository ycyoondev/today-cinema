from django.contrib.auth import get_user_model
from rest_framework.decorators import api_view, permission_classes
from rest_framework.permissions import AllowAny, IsAdminUser
from rest_framework.response import Response
from rest_framework import status

from movies.serializers import (
    GenreDatabaseSerializer,
)
from movies.models import Movie, Genre
from movies.views import add_movie
from community.models import Review, Comment
from community.views import spoiler_check
from _env import ENV_TMDB_KEY

import requests
from faker import Faker
from django_seed import Seed
import random


BASE_URL = 'https://api.themoviedb.org/3'


@api_view(['GET'])
@permission_classes([AllowAny])
def update_movies(request):
    Genre.objects.all().delete()
    genres_url = BASE_URL + '/genre/movie/list'
    genres_query = {
        'api_key': ENV_TMDB_KEY,
        'language': 'ko-KR',
    }
    genres_response = requests.get(genres_url, params=genres_query)
    genres_response_dictionary = genres_response.json()
    genres = genres_response_dictionary.get('genres')
    genres_serializer = GenreDatabaseSerializer(data=genres, many=True)
    if genres_serializer.is_valid(raise_exception=True):
        genres_serializer.save()

    Movie.objects.all().delete()
    for pagenum in range(1, 50):
        popularmovies_url = BASE_URL + '/movie/popular'
        movies_query = {
            'api_key': ENV_TMDB_KEY,
            'language': 'ko-KR',
            'page': pagenum,
            'region': 'KR',
        }
        movies_response = requests.get(popularmovies_url, params=movies_query)
        movies_response_dictionary = movies_response.json()
        movies_dict = movies_response_dictionary.get('results')
        add_movie(movies_dict)

    return Response({'response': 'Database seeding completed'}, status=status.HTTP_201_CREATED)


@api_view(['GET'])
@permission_classes([AllowAny])
def create_fakes(request):
    fake = Faker('en-US')
    seeder = Seed.seeder('ko_KR')

    NUMBER_FAKE_USERS = 100

    for _ in range(NUMBER_FAKE_USERS) :
        fake_name = fake.name()
        get_user_model().objects.create(
            username=fake_name,
            password=seeder.faker.password(),
            introduction=fake.text(),
        )

    movies_number = Movie.objects.count()
    for _ in range(50):
        random_movie = Movie.objects.get(id=random.randrange(1, movies_number+1))
        for __ in range(5):
            Review.objects.create(
                user_id=random.randrange(1, NUMBER_FAKE_USERS+1),
                movie=random_movie,
                content=fake.text(),
                is_spoiler_self=random.choice([True, False]),
                user_rating=random.randrange(1, 11),
            )
            
            random_user = get_user_model().objects.get(id=random.randrange(1, NUMBER_FAKE_USERS+1))
            if random.choice([True, False]):
                random_movie.wish_users.add(random_user)
            
            if random.choice([True, False]):
                random_movie.recommend_users.add(random_user)

    reviews_number = Review.objects.count()
    for _ in range(100):
        random_review = Review.objects.get(id=random.randrange(1, reviews_number+1))
        for __ in range(5):
            random_user = get_user_model().objects.get(id=random.randrange(1, NUMBER_FAKE_USERS+1))
            Comment.objects.create(
                user=random_user,
                review=random_review,
                content=fake.text(),
            )
        
        for __ in range(20):
            random_user = get_user_model().objects.get(id=random.randrange(1, NUMBER_FAKE_USERS+1))
            if random.choice([True, False]):
                random_review.like_users.add(random_user)

            if random.choice([True, False]):
                spoiler_check(random_review, random_user)

    for _ in range(NUMBER_FAKE_USERS//5):
        random_user1 = get_user_model().objects.get(id=random.randrange(1, NUMBER_FAKE_USERS+1))
        for __ in range(10):
            random_user2 = get_user_model().objects.get(id=random.randrange(1, NUMBER_FAKE_USERS+1))
            if random_user1 == random_user2:
                continue

            if random.choice([True, False]):
                random_user1.followers.add(random_user2)
            else:
                if random_user1.followers.filter(id=random_user2.id).exists():
                    random_user1.followers.remove(random_user2)
                
                random_user1.blockeds.add(random_user2)

    return Response({'notice': 'Fake data created'}, status=status.HTTP_201_CREATED)