<template>
  <div>
    <!-- Navbar -->
    <nav class="navbar navbar-expand navbar-light bg-black ">
      <div class="container-fluid text-white">
        <router-link :to="{name: 'Index'}" class="navbar-brand gamja-font text-white" href="#">오늘의영화</router-link>
        <!-- 공통 -->
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item" v-if="isLogin">
              <router-link :to="{name: 'Recommend'}" class="nav-link active dongle-font text-white"><i class="fas fa-gift"></i> 추천 받기</router-link>
            </li>
            <li class="nav-item" v-if="!(isLogin)">
              <router-link :to="{name: 'authPage'}" class="nav-link active dongle-font"><i class="fas fa-gift"></i> 추천 받기</router-link>
            </li>
          </ul>
          <li class="nav-item dropdown nomarker">
            <a class="nav-link dropdown-toggle jua-font" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
              {{ authUser }}
            </a>
            <ul class="dropdown-menu nomarker dropdown-menu-end" aria-labelledby="navbarDropdown">
              <div v-if="isLogin">
                <li><router-link :to="{name: 'Profile', params: { user_id: authId }}" class="nav-link active">Profile</router-link></li>
                <li><hr class="dropdown-divider"></li>
                <li><router-link to="#" @click.native="logout" class="nav-link active">Logout</router-link></li>
              </div>
              <div v-else>
                <li><router-link :to="{name: 'authPage'}" class="nav-link active">Login</router-link></li>
                <li><hr class="dropdown-divider"></li>
                <li><router-link :to="{name: 'authPage'}" class="nav-link active">Sign Up</router-link></li>
              </div>
            </ul>
          </li>
        </div>
      </div>
    </nav>
    <!-- View -->
    <router-view/>
  </div>
</template>

<script>
import Vue from 'vue'
// import { mapState } from 'vuex'
import VueAwesomeSwiper from 'vue-awesome-swiper'
import 'swiper/css/swiper.css'
import jwt_decode from "jwt-decode";


Vue.use(VueAwesomeSwiper);

export default {
  name: 'App',
  data: function () {
    return {
      isLogin: false,
      authUser: '방문자',
      authId: 1,
    }
  },
  methods: {
    logout: function () {
      this.isLogin = false
      localStorage.removeItem('jwt')
      this.$router.go()
    }
  },
  created : function(){
    // 로컬스토리지에 jwt 이 존재하는지에 따라 로그인 여부 판단하기
    const token = localStorage.getItem('jwt')
    if(token){
      this.isLogin = true
      const user = jwt_decode(token);
      this.authUser = user.username
      this.authId = user.user_id
    } else {
      this.authUser = '방문자'
    }
  },
  updated : function(){
    // 로컬스토리지에 jwt 이 존재하는지에 따라 로그인 여부 판단하기
    const token = localStorage.getItem('jwt')
    if(token){
      this.isLogin = true
      const user = jwt_decode(token);
      this.authUser = user.username
      this.authId = user.user_id
    } else {
      this.authUser = '방문자'
    }
  },

}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Gamja+Flower&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Dongle&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap');

.nomarker {
  list-style-type: none;
}
.gamja-font {
  font-family: 'Gamja Flower', cursive;
  font-size: 40px;
}
.dongle-font {
  font-family: 'Dongle', sans-serif;
  padding-top: 17px;
  font-size: 30px;
}
.jua-font {
  font-family: 'Noto Sans KR', sans-serif;
  font-size: 22px;
}
body > div {
  background-color: rgb(38, 38, 38);
}
.jua-font:hover {
  color: #A85454
}
/* nav {
  background-color: black;
} */
</style>
