from django.db import models
from django.conf import settings
from django.core.validators import MaxValueValidator, MinValueValidator


class Genre(models.Model):
    name = models.CharField(max_length=10)
    tmdb_id = models.IntegerField()

    def __str__(self):
        return self.name

    
class Movie(models.Model):
    movie_id = models.IntegerField(default=0)
    title = models.CharField(max_length=100)
    tmdb_rating = models.DecimalField(max_digits=3, decimal_places=1, validators=[MinValueValidator(0), MaxValueValidator(10)])
    overview = models.TextField(blank=True)
    tmdb_id = models.IntegerField()
    poster_path = models.CharField(max_length=100)
    genres = models.CharField(max_length=100)
    
    def __str__(self):
        return self.title
