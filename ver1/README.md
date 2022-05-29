# 오늘의 영화 (TodayCinema) Ver.1

## ❗ Abstract

> 21.11.29
>
> SSAFY 프로젝트에서 2명이 팀으로 개발하였습니다.

### 개발 툴

![image-20211125134044316](README.assets/image-20211125134044316-16538066363661.png)

### 기능

![image-20211125142559941](README.assets/image-20211125142559941-16538066789542.png)



## 📄 Entity Relationship Model (ERD)

![Today_Cinema_ERD.drawio](README.assets/Today_Cinema_ERD.drawio-16538066992033.png)



## 👥 Contributors

### 윤영철

**Frontend, Machine Learning**

FE 개발과 ML 모델 학습 및 서빙을 담당했습니다.

### 이호형

**Backend, Database Administer**

BE 개발과 DB 설계를 담당했습니다.



## 🗂️ Release

AWS 배포 (현재 비용 문제로 OFF 상태 입니다.)



---



## ⬇️ Installation

### For Server

```
 $ git clone https://github.com/ycyoondev/today-cinema.git
 $ cd ver1/django-server
 $ pip install -r requirements.txt
```

**To use Virtual Environments**

(customize if necessary)

```
 $ python -m venv venv
 $ source venv/Scripts/activate/
 $ pip install -r requirements.txt
```

### For Client

```
 $ git clone https://github.com/ycyoondev/today-cinema.git
 $ cd ver1/vue-client
 $ npm i
```



## 🚀 Getting started

### Key Setting

#### For Server

Django Server 폴더에 `_env.py` 파일을 생성 후 아래 내용을 저장한다.

- SECRET_KEY는 Django settings.py에서 얻을 수 있다.
- ENV_TMDB_KEY는 [TMDB API](https://developers.themoviedb.org/3)에서 발급받을 수 있다.

```
### _env.py
SECRET_KEY = 
ENV_TMDB_KEY = 
```

#### For Client

Root 폴더(.git위치)에 `.env.local`파일을 생성 후 아래 내용을 저장한다. 

아래 값은 예시이며, 해당하는 로컬 주소나 배포된 주소를 입력하면 된다.

```
### .
VUE_APP_SERVER_URL=http://127.0.0.1:8000
```

### 실행

#### For Server

```
$ python manage.py migrate
$ python manage.py runserver
```

#### For Client

```
$ npm run serve
```

