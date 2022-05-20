from django.db import models
from django.conf import settings
from django.core.validators import MaxValueValidator, MinValueValidator


class Genre(models.Model):
    name = models.CharField(max_length=10)
    tmdb_id = models.IntegerField()

    def __str__(self):
        return self.name

    
class Movie(models.Model):
    wish_users = models.ManyToManyField(settings.AUTH_USER_MODEL, related_name='wish_movies')
    recommend_users = models.ManyToManyField(settings.AUTH_USER_MODEL, related_name='recommend_movies')
    genres = models.ManyToManyField(Genre, related_name='movies')
    release_date = models.DateField()
    title = models.CharField(max_length=100)
    tmdb_rating = models.DecimalField(max_digits=3, decimal_places=1, validators=[MinValueValidator(0), MaxValueValidator(10)])
    overview = models.TextField(blank=True)
    poster_path = models.CharField(max_length=100)
    tmdb_id = models.IntegerField()
    video_key = models.CharField(max_length=100)
    adult = models.BooleanField()
    
    def __str__(self):
        return self.title
