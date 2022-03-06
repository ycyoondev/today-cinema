from django.urls import path
from . import views


urlpatterns = [
    path('movie/<int:movie_id>/reviews/', views.reviews),
    path('movie/<int:movie_id>/review/<int:review_id>/', views.review_detail),
    path('movie/<int:movie_id>/review/<int:review_id>/comments/', views.comments),
    path('movie/<int:movie_id>/review/<int:review_id>/comment/<int:comment_id>/', views.comment_detail),
    path('movie/<int:movie_id>/review/<int:review_id>/review-like-check/', views.review_like_check),
    path('movie/<int:movie_id>/review/<int:review_id>/review-spoiler-check/', views.review_spoiler_check),
    path('movie/<int:movie_id>/review/wordcloud/', views.review_wordcloud),
]
