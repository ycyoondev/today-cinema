from django.db import models
from django.conf import settings
from django.core.validators import MaxValueValidator, MinValueValidator
from movies.models import Movie


class Review(models.Model):
    user = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE)
    movie = models.ForeignKey(Movie, on_delete=models.CASCADE)
    like_users = models.ManyToManyField(settings.AUTH_USER_MODEL, related_name='like_reviews')
    spoiler_check_users = models.ManyToManyField(settings.AUTH_USER_MODEL, related_name='spoiler_check_reviews')
    content = models.CharField(max_length=2000, blank=True)
    is_spoiler_self = models.BooleanField(default=False)
    is_spoiler_checked = models.BooleanField(default=False)
    user_rating = models.IntegerField(validators=[MinValueValidator(0), MaxValueValidator(10)])
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)


class Comment(models.Model):
    user = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE)
    review = models.ForeignKey(Review, on_delete=models.CASCADE)
    content = models.CharField(max_length=200)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)
