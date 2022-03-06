from django.db import models
from django.contrib.auth.models import AbstractUser


class User(AbstractUser):
    followings = models.ManyToManyField('self', symmetrical=False, related_name='followers')
    blockeds = models.ManyToManyField('self', symmetrical=False, related_name='blockings')
    introduction = models.CharField(max_length=200, blank=True)