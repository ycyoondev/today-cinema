from rest_framework import serializers
from .models import Movie, Genre
from django.core.validators import MaxValueValidator, MinValueValidator


class GenreDatabaseSerializer(serializers.ModelSerializer):
    id = serializers.IntegerField(source='tmdb_id', write_only=True)

    class Meta:
        model = Genre
        fields = '__all__'
        read_only_fields = ('tmdb_id',)


class GenreSimpleSerializer(serializers.ModelSerializer):

    class Meta:
        model = Genre
        fields = ('id', 'name',)


class MovieDatabaseSerializer(serializers.ModelSerializer):
    vote_average = serializers.DecimalField(source='tmdb_rating', write_only=True, max_digits=3, decimal_places=1, validators=[MinValueValidator(0), MaxValueValidator(10)])
    id = serializers.IntegerField(source='tmdb_id', write_only=True)

    class Meta:
        model = Movie
        fields = '__all__'
        read_only_fields = ('wish_users', 'recommend_users', 'tmdb_rating', 'genres', 'video_key', 'tmdb_id',)


class MovieMainSerializer(serializers.ModelSerializer):
    
    class Meta:
        model = Movie
        fields = ('id', 'title', 'tmdb_rating', 'overview', 'poster_path',)


class MovieDetailSerializer(serializers.ModelSerializer):
    genres = GenreSimpleSerializer(read_only=True, many=True)
    
    class Meta:
        model = Movie
        fields = '__all__'


class MovieSimpleSerializer(serializers.ModelSerializer):
    
    class Meta:
        model = Movie
        fields = ('id', 'title', 'tmdb_rating', 'poster_path',)
