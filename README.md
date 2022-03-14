# 오늘의 영화 (TodayCinema)

모든 사람이 자주 영화를 보는것은 아닙니다. 하지만 대부분의 영화 커뮤니티는 '단골'을 타겟하여 서비스를 제공합니다. 그렇다면 **가끔 영화가 보고싶어졌을 때 찾아갈 서비스가 있다면 어떨까요?** 저희는 이 고민으로부터 프로젝트를 출발하였습니다. 

본 서비스는 가끔 오는 사람들을 위해 `오늘의 영화`를 추천해 드립니다. 16강 영화 월드컵을 통해 심심함을 달래며, 기존 정보가 없더라도 알맞는 `오늘의 영화`를 추천받을 수 있습니다. 추천받은 영화 리뷰를 빠르게 검토하기 위해 `워드클라우드`를 제공하며, `스포일러`에 안전한 리뷰를 보여드립니다. 그럼 **모든 형태의 사용자가 최고의 서비스**를 누리는 저희 프로젝트를 자세히 소개합니다.



## 🔥 Features

- 영화를 선택하는데 필요한 **정보와 커뮤니티를 제공**합니다.
- 영화 정보 + 리뷰 + 티저 영상을 하나의 페이지에서 확인함으로써 **몰입감**을 해치지 않습니다.
- 영화 리뷰를 **워드 클라우드**로 제작하여 한눈에 파악할 수 있습니다.
- **스포일러와 블랙리스트 필터**를 통해 더욱 쾌적한 커뮤니티 환경을 제공합니다.
- 영화 월드컵을 기반으로 **비슷한 장르 영화를 추천**해드립니다.



## 🗂️ Release

| Version    | Release Date      | Source                | Detail                               |
| ---------- | ----------------- | --------------------- | ------------------------------------ |
| 1.0.1 (v1) | November 30, 2021 | [Source](/backend-v1) | [Detail Note](/backend-v1/README.md) |

 :bulb: (22.03.06) 서버를 Spring으로 전환 작업 중입니다. (v1 버전은 정상 사용 가능합니다)



## 📄 Documentation

- [Detail Description](/document/Detail_Description.md)
- [Architecture](/document/Architecture.md)
- [Version 1 README](/backend-v1/README.md)



---



## ⬇️ Installation

### For Server

```
 $ git clone https://github.com/ycyoondev/today-cinema.git
 $ cd today-cinema/backend-v1
 $ pip install -r requirements.txt
```

**To use Virtual Environments**

(customize if necessary)

```
 $ cd backend-v1
 $ python -m venv venv
 $ source venv/Scripts/activate/
 $ pip install -r requirements.txt
```

### For Client

```
 $ git clone https://github.com/ycyoondev/today-cinema.git
 $ cd today-cinema/frontend
 $ npm i
```



## 🚀 Getting started

### Key Setting

#### For Server

Server 폴더(venv위치)에 `_env.py` 파일을 생성 후 아래 내용을 저장한다.

- SECRET_KEY는 Django settings.py에서 얻을 수 있다.
- ENV_TMDB_KEY는 TMDB API에서 발급받을 수 있다.

```
SECRET_KEY = 
ENV_TMDB_KEY = 
```

#### For Client

Root 폴더(.git위치)에 `.env.local`파일을 생성 후 아래 내용을 저장한다. 

아래 값은 예시이며, 해당하는 로컬 주소나 배포된 주소를 입력하면 된다.

```
VUE_APP_SERVER_URL=http://127.0.0.1:8000
```

### 실행

#### For Server

```
$ python manage.py migrate
$ python manage.py loaddata TodayCinemaDumpdata.json
$ python manage.py runserver
```

#### For Client

```
$ npm run serve
```



## ©️ License

[Apache License 2.0](https://github.com/ycyoondev/JORLDY/blob/master/LICENSE.md)

