from django.urls import path
from . import views

urlpatterns = [
    path('update-movies/', views.update_movies),
    path('create-fakes/', views.create_fakes),
]
