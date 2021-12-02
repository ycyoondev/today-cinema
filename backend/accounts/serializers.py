from rest_framework import serializers
from django.contrib.auth import get_user_model
from movies.serializers import MovieSimpleSerializer
from community.serializers import ReviewProfileSerializer


class UserCreateSerializer(serializers.ModelSerializer):

    class Meta:
        model = get_user_model()
        fields = ('username',)


class UserSimpleSerializer(serializers.ModelSerializer):

    class Meta:
        model = get_user_model()
        fields = ('id', 'username',)


class UserProfileSerializer(serializers.ModelSerializer):
    followings = UserSimpleSerializer(many=True, read_only=True)
    followers = UserSimpleSerializer(many=True, read_only=True)
    blockings = UserSimpleSerializer(many=True, read_only=True)
    wish_movies = MovieSimpleSerializer(many=True, read_only=True)
    recommend_movies = MovieSimpleSerializer(many=True, read_only=True)

    class Meta:
        model = get_user_model()
        fields = (
            'id', 'last_login', 'username', 'date_joined',
            'introduction', 'followings', 'followers',
            'blockings', 'wish_movies', 'recommend_movies',
        )
        read_only_fields = (
            'id', 'last_login', 'username', 'date_joined',
        )
