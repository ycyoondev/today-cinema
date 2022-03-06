# [Swagger] RESTful API 문서 만들기

### 배경

Django로 개발한 서버를 Spring 서버로 변경하려 합니다. Django 서버 API를 하나하나 파악하는것은 효율적이지 못하므로, 자동 문서화 도구를 사용해서 적용된 API를 파악하겠습니다. 

### 목표

Django REST API Doc 도구인 Swagger (drf-yasg)를 사용하여 문서화 하겠습니다.

### 과정

#### 환경 설정

1. 라이브러리 설치

```bash
pip install drf-yasg
```

2. setting.py 

```python
INSTALLED_APPS = [
    'drf_yasg',
]
```



### 결과

