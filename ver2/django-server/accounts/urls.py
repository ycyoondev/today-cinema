from django.urls import path
from rest_framework_jwt.views import obtain_jwt_token
from . import views


urlpatterns = [
    path('api-token-auth/', obtain_jwt_token),
    path('signup/', views.signup),
    path('profile/<int:user_id>/', views.profile),
    path('follow/<int:user_id>/', views.follow),
    path('block/<int:user_id>/', views.block),
]
