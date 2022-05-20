from rest_framework import serializers
from django.contrib.auth import get_user_model
from .models import Review, Comment
from movies.serializers import MovieSimpleSerializer


class UserSimpleSerializer(serializers.ModelSerializer):

        class Meta:
            model = get_user_model()
            fields = ('id', 'username',)


class ReviewSerializer(serializers.ModelSerializer):
    user = UserSimpleSerializer(read_only=True)

    class Meta:
        model = Review
        fields = '__all__'
        read_only_fields = ('user', 'movie', 'like_users', 'spoiler_check_users', 'is_spoiler_checked',)


class ReviewsetSerializer(serializers.ModelSerializer):
    user = UserSimpleSerializer(read_only=True)

    class Meta:
        model = Review
        exclude = ('movie',)


class ReviewProfileSerializer(serializers.ModelSerializer):
    user = UserSimpleSerializer(read_only=True)
    movie = MovieSimpleSerializer(read_only=True)

    class Meta:
        model = Review
        fields = (
            'movie', 'user_rating',
        )


class CommentSerializer(serializers.ModelSerializer):
    user = UserSimpleSerializer(read_only=True)

    class Meta:
        model = Comment
        fields = '__all__'
        read_only_fields = ('user', 'review',)


class CommentsetSerializer(serializers.ModelSerializer):
    user = UserSimpleSerializer(read_only=True)

    class Meta:
        model = Comment
        exclude = ('review',)
