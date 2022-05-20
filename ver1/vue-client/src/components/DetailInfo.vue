<template>
  <div >
    <!-- 제목, 아이콘 -->
    <div>
      <div>
        <h2 class="movie-title text-4xl font-semibold leading-normal detail-movie-title">{{ movie.title }}</h2>
        <div class="movie-content gothic-font">
          <i class="fas fa-star"></i> 
          <span> {{ movie.tmdb_rating }} | {{ movie.release_date }} | </span>
          <span v-for="genre in movie.genres" :key="genre.id">{{ genre.name }} </span>
        </div>
      </div>
      <div class="movie-botton">
        <!-- <span @click="like">
          <i class="fas fa-heart"></i> <span> 좋아요   </span>
        </span> -->
        <span @click="bookmark">
          <span v-if="isBookmark" style="color:orange">
            <i class="fas fa-bookmark"></i>
          </span>
          <span v-else>
            <i class="far fa-bookmark"></i>
          </span>
           <span class="gothic-font"> 북마크</span>
        </span>
      </div>
      <div class="border-line"></div>
    </div>
    <!-- 자세한 내용 -->
    <div>
      <p class="movie-overview gothic-font">{{ movie.overview }}</p>
      <div class="border-line"></div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import jwt_decode from "jwt-decode";


const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: 'DetailInfo',
  data : function(){
    return {
      isBookmark: false,
    }
  },
  props: {
    movie : {
      type : Object,
    },
  },
  methods : {
    setToken: function () {
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `JWT ${token}`
      }
      return config
    },
    bookmark : function(){
      const url = SERVER_URL + '/movies/wish/' + this.movie.id + '/'
      axios({
        method: 'post',
        url: url,
        headers: this.setToken()
      })
        .then(res => {
          this.isBookmark = !(this.isBookmark)
        })
        .catch(err => {
          console.log(err)
        })
    },
  },
  created : function(){
    // 로컬스토리지에 jwt 이 존재하는지에 따라 로그인 여부 판단하기
    const token = localStorage.getItem('jwt')
    if(token){
      this.isLogin = true
      const user = jwt_decode(token);
      this.authUser = user.username
      this.authId = user.user_id
      if (this.movie.wish_users.includes(user.user_id)) {
        this.isBookmark = true
      }

    } 
  },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Gothic+A1&display=swap');
.movie-title {
  font-size: 3rem;
}
.movie-content {
  font-size: 1.5rem;
}
.movie-botton {
  margin-top: 0.5rem;
  margin-left: 0.2rem;
  color: rgb(214, 214, 214);
}
.movie-overview {
  font-size: 1.2rem;
}
.border-line {
  margin-top: 1rem;
  margin-bottom: 1rem;
  border-bottom: 0.2rem solid rgb(180, 180, 180)
}
.gothic-font {
  font-family: 'Noto Sans KR', sans-serif;
  font-size: 1.6rem;
}
.detail-movie-title {
  font-size: 3rem;
}
.fa-bookmark {
  font-size: 1.6rem;
}

</style>