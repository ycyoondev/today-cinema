<template>
  <div class="text-gray-800 antialiased">
    
    <main id="profile-page">
      <!-- 상단이미지 -->
      <section class="relative block" style="height: 400px;">
        <div
          class="absolute top-0 w-full h-full bg-center bg-cover"
          style='background-image: url("https://s3-us-west-2.amazonaws.com/prd-rteditorial/wp-content/uploads/2018/03/13153742/RT_300EssentialMovies_700X250.jpg");'
        >
          <span
            id="blackOverlay"
            class="w-full h-full absolute opacity-50 bg-black"
          ></span>
        </div>
        <!-- 기울이기 -->
        <!-- <div
          class="top-auto bottom-0 left-0 right-0 w-full absolute pointer-events-none overflow-hidden"
          style="height: 70px;"
        >
          <svg
            class="absolute bottom-0 overflow-hidden"
            xmlns="http://www.w3.org/2000/svg"
            preserveAspectRatio="none"
            version="1.1"
            viewBox="0 0 2560 100"
            x="0"
            y="0"
          >
            <polygon
              class="text-gray-300 fill-current"
              points="2560 0 2560 100 0 100"
            ></polygon>
          </svg>
        </div> -->
      </section>
      <!-- detail -->
      <section class="relative py-16 bg-gray-300">
        <div class="container mx-auto px-3">
          <div 
            class="relative flex flex-col min-w-0 break-words bg-white w-full mb-6 shadow-xl rounded-lg"
          >
            <div class="px-6"  >
              <div class="flex flex-wrap justify-center" >
                
                <div
                  class="w-full px-4"
                >
                </div>
                <div class="w-full px-4">
                  <div class="flex justify-center py-4 pt-8">
                    <div class="mr-4 p-3 text-center">
                      <span
                        class="text-xl font-bold block uppercase tracking-wide text-white" id="follow-cnt"
                        >{{ people.followers | counter }}</span
                      ><span class="text-sm text-white">Follower</span>
                    </div>
                    <div class="mr-4 p-3 text-center">
                      <span
                        class="text-xl font-bold block uppercase tracking-wide text-white"
                        >{{ people.followings | counter }}</span
                      ><span class="text-sm text-white">Following</span>
                    </div>
                    <div class="p-3 text-center" v-if="people.id === authUser.user_id">
                      <span
                        class="text-xl font-bold block uppercase tracking-wide text-white"
                        >{{ people.blockings | counter }}</span
                      ><span class="text-sm text-white">Black List</span>
                    </div>
                    <div class="p-3 text-center ml-auto" v-if="people.id !== authUser.user_id">
                        <!-- 팔로우 -->
                        <span v-if="isBookmark" class="profile-btn-true" @click="follow">
                          <i class="fas fa-user-plus fa-2x mx-4"></i>
                        </span>
                        <span v-else class="profile-btn-false" @click="follow">
                          <i class="fas fa-user-plus fa-2x mx-4 profile-btn"></i>
                        </span>
                        <!-- 블로킹 -->
                        <span v-if="isBlocking" class="profile-btn-true" @click="blocking">
                          <i class="fas fa-comment-slash fa-2x profile-btn"></i>
                        </span>
                        <span v-else class="profile-btn-false" @click="blocking">
                          <i class="fas fa-comment-slash fa-2x profile-btn"></i>
                        </span>
                    </div>
                    <div class="p-3 text-center ml-auto" v-if="people.id === authUser.user_id">
                        <!-- 개인정보 수정 -->
                        <button @click="openModal" class="write-profile">인사말 수정</button>
                    </div>
                  </div>
                </div>
              </div>
              <div class="text-center mt-12">
                <!-- 이름 -->
                <h3
                  class="text-4xl font-semibold leading-normal mb-2 text-white mb-2"
                >
                  {{ people.username }}
                </h3>
                <div
                  class="text-sm leading-normal mt-0 mb-2 text-white font-bold uppercase"
                >
                  <span v-if="people.introduction">
                    {{ people.introduction }}
                  </span>
                  <span v-else>
                    안녕하세요. {{ people.username }}입니다.
                  </span>
                </div>
              </div>
              <div class="mt-10 py-10 border-t border-gray-300 text-center">
                <div class="flex flex-wrap justify-center profile-movies">
                  <div class="w-full lg:w-9/12">
                    <p class="mb-1 mt-2 text-lg leading-relaxed profile-mid-title text-white">
                      북마크 모아보기
                    </p>
                    <div v-if="people.wish_movies.length" class="profile-caruser">
                      <movie-caruser :movies="people.wish_movies"></movie-caruser>
                    </div>
                    <div v-else>
                      <div>
                        <p class="empty-box">북마크를 담아주세요!</p>
                      </div>
                    </div>

                  </div>
                  <div class="w-full lg:w-9/12">
                    <p class="mb-1 mt-4 text-lg leading-relaxed profile-mid-title text-white">
                      추천 영화 모아보기
                    </p>
                    <div v-if="people.recommend_movies.length" class="profile-caruser">
                      <movie-caruser :movies="people.recommend_movies"></movie-caruser>
                    </div>
                    <div v-else>
                      <div>
                        <p class="empty-box">영화 추천을 받아보세요!</p>
                      </div>
                    </div>

                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </main>
    <!-- 모달 -->
    <modal 
      name="profile-modal" 
      class="form-container"
      width="700px"
      height="700px"
    >
        <form id="signup">
          <div class="header">
              <h3>Profile Update</h3>
              <p>자신을 소개해 주세요.</p>
          </div>
          <div class="sep"></div>
          <div class="inputs">
              <p id="input-title">자기소개</p>
              <textarea placeholder="" v-model="formData.introduction" autofocus></textarea>
              <a id="submit" href="#" @click="updateProfile">Write Profile</a>
          </div>
      </form>
    </modal>
  </div>
</template>
<script>
import axios from 'axios'
import MovieCaruser from '@/components/MovieCaruser.vue'
import jwt_decode from "jwt-decode";


const SERVER_URL = process.env.VUE_APP_SERVER_URL


export default {
  name: "Profile",
  components: {
    MovieCaruser,
  },
  data: function() {
    return {
      people: null,
      authUser: '방문자', // user_id, username
      isBookmark: false,
      isBlocking: false,
      recommendMovies: false,
      bookmarkMovies: false,
      formData: {
        introduction: ''
      }
    }
  },
  methods : {
    setToken: function () {
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `JWT ${token}`
      }
      return config
    },
    getPeople : function(){
      const url = SERVER_URL + '/accounts/profile/' + this.$route.params.user_id
      if (!(this.setToken().Authorization === "JWT null" )) {
        axios({
        method: 'get',
        url: url,
        headers: this.setToken()
      })
        .then(res => {
          this.people = res.data
          this.getButton()
        })
        .catch(err => {
          console.log(err)
        })
      } else {
        axios({
        method: 'get',
        url: url,
      })
        .then(res => {
          this.people = res.data
        })
        .catch(err => {
          console.log(err)
        })
      }
    },
    getUser: function() {
      const token = localStorage.getItem('jwt')
      if(token){
        this.isLogin = true
        this.authUser = jwt_decode(token)
      } else {
        this.authUser = '방문자'
      }
    },
    follow: function() {
      const url = SERVER_URL + '/accounts/follow/' + this.people.id + '/'
      if (!(this.setToken().Authorization === "JWT null" )) {
        axios({
        method: 'post',
        url: url,
        headers: this.setToken()
      })
        .then(res => {
          this.isBookmark = !(this.isBookmark)
          if (this.isBookmark) {
            this.people.followers.push({'a': 1})
          } else {
            this.people.followers.pop()
          }          
        })
        .catch(err => {
          console.log(err)
        })
      } else {
        window.alert("로그인이 필요한 기능입니다.")
      }
    },
    blocking: function() {
      const url = SERVER_URL + '/accounts/block/' + this.people.id + '/'
      if (!(this.setToken().Authorization === "JWT null" )) {
        axios({
        method: 'post',
        url: url,
        headers: this.setToken()
      })
        .then(res => {
          this.isBlocking = !(this.isBlocking)
          if (this.isBlocking) {
            this.people.blockings.push({'a': 1})
          } else {
            this.people.blockings.pop()
          }          
        })
        .catch(err => {
          console.log(err)
        })
      } else {
        window.alert("로그인이 필요한 기능입니다.")
      }
    },
    getButton: function() {
      if (this.people.followers.some(e => e.id === this.authUser.user_id)) {
          this.isBookmark = true
      }
      if (this.people.blockings.some(e => e.id === this.authUser.user_id)) {
          this.isBlocking = true
      }
    },
    openModal: function(){
      if (!(this.setToken().Authorization === "JWT null" )) {
        this.$modal.show('profile-modal')
        console.log(this.setToken())
      } else {
        window.alert("로그인을 해주세요.")
      }
    },
    
    closeModal: function(){
      this.$modal.hide('profile-modal')
      // this.getMovie()
      this.$router.go()
      // document.location.reload(true);
    },
    updateProfile: function() {
      const url = SERVER_URL + '/accounts/profile/' + this.people.id + '/'
      if (!(this.setToken().Authorization === "JWT null" )) {
        axios({
        method: 'put',
        url: url,
        data: this.formData,
        headers: this.setToken()
      })
        .then(res => {
          console.log(res)
          this.closeModal()
        })
        .catch(err => {
          console.log(err)
        })
      } else {
        axios({
        method: 'put',
        url: url,
        data: this.formData,
      })
        .then(res => {
          console.log(res)
          this.closeModal()
        })
        .catch(err => {
          console.log(err)
        })
      }
    }
  },
  computed: {
    followerCount: function() {
      return this.people.followers.length
    }
  },
  filters: {
    counter: function (arr) {
      return arr.length
    }
  },
  
  created : function(){
    this.getPeople()
    this.getUser()
    
  },

  mounted : function(){
    var lazyBackgrounds = [].slice.call(document.querySelectorAll(".lazy-background"));

    if ("IntersectionObserver" in window && "IntersectionObserverEntry" in window && "intersectionRatio" in window.IntersectionObserverEntry.prototype) {
      let lazyBackgroundObserver = new IntersectionObserver(function(entries) {
        entries.forEach(function(entry) {
          if (entry.isIntersecting) {
            //console.log("evisible")
            entry.target.classList.add("visible");
            lazyBackgroundObserver.unobserve(entry.target);
          }
        });
      });

      lazyBackgrounds.forEach(function(lazyBackground) {
        lazyBackgroundObserver.observe(lazyBackground);
      });
    }
  }
}
</script>

<style>
#profile-page {
  background-color: black;
}
.profile-btn-false {
  color: gray;
}
.profile-btn-false:hover {
  color: rgb(107, 107, 107);
}
.profile-btn-true {
  color: black;
}
.profile-btn-true:hover {
  color: rgb(37, 37, 37);
}
.empty-box {
  position: relative;
  text-align: center;
  padding: 30px 0;
  font-weight: 800;
  font-size: 30px;
  color: gray;
}

.profile-mid-title {
  font-size: 50px;
  font-family: 'Dongle', sans-serif;
}
.write-profile {
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  background-color: #555555;
  border-radius: 8px;
}
.write-profile:hover {
  box-shadow: 0 6px 12px 0 rgba(0,0,0,0.24), 0 14px 30px 0 rgba(0,0,0,0.19);
}
.profile-caruser .caruser-size > .swiper-wrapper > .swiper-slide {
  height: 20vw; 
}
.bg-gray-300 {
  background-color: black;
}
section > .container {
  background: radial-gradient(rgb(214, 214, 214), rgb(22, 22, 22))
}
section > .container > .w-full > .px-6 {
  background-color: rgb(38, 38, 38);
}
</style>