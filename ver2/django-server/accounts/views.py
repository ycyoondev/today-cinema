from rest_framework.decorators import api_view, permission_classes
from rest_framework.permissions import AllowAny
from rest_framework.response import Response
from rest_framework import status

from django.contrib.auth import get_user_model
from .serializers import UserCreateSerializer, UserProfileSerializer


@api_view(['POST'])
@permission_classes([AllowAny])
def signup(request):
    password = request.data.get('password')
    password_confirmation = request.data.get('passwordConfirmation')

    if password != password_confirmation:
        return Response({'error': '비밀번호 불일치'}, status=status.HTTP_400_BAD_REQUEST)
    
    user_serializer = UserCreateSerializer(data=request.data)
    if user_serializer.is_valid(raise_exception=True):
        user = user_serializer.save()
        user.set_password(password)
        user.save()
        return Response(user_serializer.data, status=status.HTTP_201_CREATED)

    
@api_view(['GET', 'PUT'])
@permission_classes([AllowAny])
def profile(request, user_id):
    user = get_user_model().objects.get(id=user_id)
    if request.method == 'GET':
        user_serializer = UserProfileSerializer(user)
        return Response(user_serializer.data)
    
    elif request.method == 'PUT':
        if not request.user.is_authenticated:
            return Response({'error': 'Please login to edit profile!'}, status=status.HTTP_401_UNAUTHORIZED)

        if request.user != user:
            return Response({'error': 'You can eidt ONLY your profile!'}, status=status.HTTP_403_FORBIDDEN)

        user_serializer = UserProfileSerializer(user, data=request.data)
        if user_serializer.is_valid(raise_exception=True):
            user_serializer.save()
            return Response(user_serializer.data)


@api_view(['POST'])
def follow(request, user_id):
    user = get_user_model().objects.get(id=user_id)
    if user == request.user:
        return Response({'warning': 'You cannot follow yourself!'})

    if user.followers.filter(id=request.user.id).exists():
        user.followers.remove(request.user)
        return Response({'notice': f'you unfollowed {user.username}.'})
    else:
        if request.user.blockings.filter(id=user.id).exists():
            request.user.blockings.remove(user)

        user.followers.add(request.user)
        return Response({'notice': f'you followed {user.username}!'})


@api_view(['POST'])
def block(request, user_id):
    user = get_user_model().objects.get(id=user_id)
    if user == request.user:
        return Response({'warning': 'You cannot block yourself!'})

    if request.user.blockings.filter(id=user.id).exists():
        request.user.blockings.remove(user)
        return Response({'notice': f'you unblocked {user.username}.'})
    else:
        if request.user.followings.filter(id=user.id).exists():
            request.user.followings.remove(user)

        request.user.blockings.add(user)
        return Response({'notice': f'you blocked {user.username}!'})
