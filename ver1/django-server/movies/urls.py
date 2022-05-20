from django.urls import path
from . import views

urlpatterns = [
    path('best/', views.best),
    path('genre/<int:genre_id>/best/', views.genre_best),
    path('<int:movie_id>/', views.movie_detail),
    path('wish/<int:movie_id>/', views.movie_wish),
    path('recommend/<int:movie_id>/', views.movie_recommend),
    path('movie-recommendation/<int:movie_id>/', views.movie_recommendation),
    path('tournament/<int:number>/', views.tournament),
]
