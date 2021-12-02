from rest_framework.decorators import api_view, permission_classes
from rest_framework.response import Response
from rest_framework import status
from rest_framework.permissions import AllowAny

from .models import Comment, Review
from movies.models import Movie
from .serializers import (
    ReviewSerializer, ReviewsetSerializer,
    CommentSerializer, CommentsetSerializer, 
)

from krwordrank.word import summarize_with_keywords


@api_view(['GET', 'POST'])
def reviews(request, movie_id):
    movie = Movie.objects.get(id=movie_id)
    if request.method == 'GET':
        all_reviews = movie.review_set.all()
        if request.user.is_authenticated:
            blocked_user_reviews = Review.objects.none()
            temp_list = []
            for blocked_user in request.user.blockings.all():
                temp_list.append(blocked_user.review_set.all())

            blocked_user_reviews = blocked_user_reviews.union(*temp_list)
            none_blocked_user_reviews = all_reviews.difference(blocked_user_reviews)
            reviews_serializer = ReviewsetSerializer(none_blocked_user_reviews[::-1], many=True)
        else:
            reviews_serializer = ReviewsetSerializer(all_reviews, many=True)
        return Response(reviews_serializer.data)

    elif request.method == 'POST':
        if movie.review_set.filter(user=request.user).exists():
            return Response({'error': 'You can write ONLY ONE review per movie!'}, status=status.HTTP_400_BAD_REQUEST)

        review_serializer = ReviewSerializer(data=request.data)
        if review_serializer.is_valid(raise_exception=True):
            review_serializer.save(user=request.user, movie=movie)
            return Response(review_serializer.data, status=status.HTTP_201_CREATED)


@api_view(['GET', 'PUT', 'DELETE'])
def review_detail(request, movie_id, review_id):
    movie = Movie.objects.get(id=movie_id)
    review = movie.review_set.get(id=review_id)
    if request.method == 'GET':
        review_serializer = ReviewSerializer(review)
        return Response(review_serializer.data)

    if review.user != request.user:
        return Response({'error': 'Only writer can handle review!'}, status=status.HTTP_403_FORBIDDEN)

    if request.method == 'PUT':
        review_serializer = ReviewSerializer(review, data=request.data)
        if review_serializer.is_valid(raise_exception=True):
            review_serializer.save()
            return Response(review_serializer.data)

    elif request.method == 'DELETE':
        review.delete()
        return Response({'notice': 'Deleted!'}, status=status.HTTP_204_NO_CONTENT)


@api_view(['GET', 'POST'])
def comments(request, movie_id, review_id):
    movie = Movie.objects.get(id=movie_id)
    review = movie.review_set.get(id=review_id)
    if request.method == 'GET':
        comments = review.comment_set.all()
        comment_serializer = CommentsetSerializer(comments, many=True)
        return Response(comment_serializer.data)

    elif request.method == 'POST':
        comment_serializer = CommentSerializer(data=request.data)
        if comment_serializer.is_valid(raise_exception=True):
            comment_serializer.save(user=request.user, review=review)
            return Response(comment_serializer.data, status=status.HTTP_201_CREATED)


@api_view(['PUT', 'DELETE'])
def comment_detail(request, movie_id, review_id, comment_id):
    movie = Movie.objects.get(id=movie_id)
    review = movie.review_set.get(id=review_id)
    comment = review.comment_set.get(id=comment_id)
    if comment.user != request.user:
        return Response({'error': 'Only writer can handle review!'}, status=status.HTTP_403_FORBIDDEN)
    
    if request.method == 'PUT':
        comment_serializer = CommentSerializer(comment, data=request.data)
        if comment_serializer.is_valid(raise_exception=True):
            comment_serializer.save()
            return Response(comment_serializer.data)

    elif request.method == 'DELETE':
        comment.delete()
        return Response({'notice': 'Deleted!'}, status=status.HTTP_204_NO_CONTENT)
        

@api_view(['POST'])
def review_like_check(request, movie_id, review_id):
    movie = Movie.objects.get(id=movie_id)
    review = movie.review_set.get(id=review_id)
    user = request.user
    if review.like_users.filter(id=user.id).exists():
        review.like_users.remove(user)
        return Response({'notice': 'Like check canceled.'})
    else:
        review.like_users.add(user)
        return Response({'notice': 'Like checked.'})


def spoiler_check(object, user):
    spoiler_count = object.spoiler_check_users.count()
    if object.spoiler_check_users.filter(id=user.id).exists():
        object.spoiler_check_users.remove(user)
        if spoiler_count == 7:
            object.is_spoiler_checked = False
            object.save()
            return {'notice': 'Spoiler check canceled. Now it is NOT spoiler!'}

        return {'notice': 'Spoiler check canceled.'}

    else:
        if object.is_spoiler_self or spoiler_count >= 15:
            return {'warning': 'It is already spoiler! double jeopardy!'}
        
        object.spoiler_check_users.add(user)
        if spoiler_count == 9:
            object.is_spoiler_checked = True
            object.save()
            return {'notice': 'Spoiler checked. Now it is spoiler!'}
        
        return {'notice': 'Spoiler checked.'}


@api_view(['POST'])
def review_spoiler_check(request, movie_id, review_id):
    movie = Movie.objects.get(id=movie_id)
    review = movie.review_set.get(id=review_id)
    if request.user == review.user:
        return Response({'warning': 'If it is spoiler, please check spoiler checkbox in correction form!'})
    
    user = request.user
    return Response(spoiler_check(review, user))


@api_view(['GET'])
@permission_classes([AllowAny])
def review_wordcloud(request, movie_id):
    movie = Movie.objects.get(id=movie_id)
    all_reviews = movie.review_set.all()
    texts = []
    for review in all_reviews:
        texts.append(review.content)
    print(texts)
    stopwords = {'영화', '관람객', '너무', '정말', '보고', '일부', '완전히'}
    keywords = summarize_with_keywords(texts, min_count=3, max_length=10,
        beta=0.85, max_iter=10, stopwords=stopwords, verbose=True)

    wordlist = []
    count = 0
    for key, val in keywords.items():
        temp = {'name': key, 'value': int(val*100)}
        wordlist.append(temp)
        count += 1
        if count >= 30:
            break

    return Response(wordlist)
