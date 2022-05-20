<template>
  <div>
    <!-- wourdcloud -->
    <div v-if="defaultWords.length" class="wordcloud-color">
      <wordcloud
        :data="defaultWords"
        nameKey="name"
        valueKey="value"
        :color="myColors"
        :showTooltip="false"
        :wordClick="wordClickHandler">
      </wordcloud>
    </div>
    <!-- 리뷰 필터 -->
    <div class="right-position">
      <label class="switch-button"> 
        <input v-model="isShowSpoiler" type="checkbox"/> 
        <span class="onoff-switch"></span> 
      </label>
      <span class="checkbox-text outfit-font">Spoiler On</span>
    </div>
    <!-- 리뷰 내용 -->
    <div v-for="review in reviews" :key="review.id">

      <div class="review-box" v-show="isShowSpoiler || !(review.is_spoiler_self || review.is_spoiler_checked)">
        <div class="review-up">
          <p class="review-username">
            <span @click="goProfile(review.user)" class="go-profile">{{ review.user.username }} </span>
            <span class="review-star"><i class="fas fa-star"></i>{{ review.user_rating }}</span>
            <span class="review-time">{{ review.created_at.substring(0,10) }}</span>
            <span v-if="review.created_at !== review.updated_at" class="review-time">(updated: {{ review.updated_at.substring(0,10) }})</span>
          </p>
          <span v-if="authUser !== review.user.id">
            <span v-if="review.like_users.includes(authUser)" @click="checkLike(review)" class="spoiler-tag heart-tag tag-hover">
              <i class="fas fa-heart" style="color: #A85454;"></i> 
              <span id=""> {{ review.like_users | counter }}</span>
            </span>
            <span v-if="!(review.like_users.includes(authUser))" @click="checkLike(review)" class="spoiler-tag heart-tag tag-hover">
              <i class="fas fa-heart"></i> 
              <span id=""> {{ review.like_users | counter }}</span>
            </span>
            <span v-if="review.spoiler_check_users.includes(authUser)" @click="checkSpoiler(review)" class="spoiler-tag tag-hover" v-show="!(review.is_spoiler_self)">
              <i class="fas fa-user-ninja" style="color: #A85454;"></i> 
              <span id=""> {{ review.spoiler_check_users | counter }}</span>
            </span>
            <span v-if="!(review.spoiler_check_users.includes(authUser))" @click="checkSpoiler(review)" class="spoiler-tag tag-hover" v-show="!(review.is_spoiler_self)">
              <i class="fas fa-user-ninja"></i> 
              <span id=""> {{ review.spoiler_check_users | counter }}</span>
            </span>
          </span>
          <span v-if="authUser === review.user.id">
            <span class="update-review" @click="openUpdateModal">
              수정
            </span>
            <span class="delete-review" @click="deleteReview(review)">
              삭제
            </span>
            <modal 
              name="update-modal" 
              class="form-container"
              width="700px"
              height="700px"
            >
                <movie-review-update 
                  :movie="movie" 
                  :review="review" 
                  :formData="{
                    content: review.content,
                    user_rating: review.user_rating,
                    is_spoiler_self: review.is_spoiler_self,
                  }" 
                  @update-review="closeModal">
                </movie-review-update>
            </modal>
          </span>
        </div>
        <p class="review-content">
          <span v-show="review.is_spoiler_self || review.is_spoiler_checked"><span class="badge rounded-pill bg-danger">Spoiler</span></span>
          <span class="content-text"> {{ review.content }}</span>
        </p>
        <div>
          <p class="right-position">
            <button class="comment-tag" type="button" data-bs-toggle="collapse" :data-bs-target="'#collapseExample' + review.id" aria-expanded="false" aria-controls="collapseExample">
              댓글 <i class="fas fa-caret-down"></i>
            </button>
          </p>
          <div class="collapse" :id="'collapseExample' + review.id">
            <div class="card card-body">
              <movie-review-comment :movie="movie" :review="review" @update-review="closeModal"></movie-review-comment>
            </div>
          </div>
        </div>
      </div>
    </div>
    
  </div>
</template>

<script>
import MovieReviewComment from '@/components/MovieReviewComment.vue'
import MovieReviewUpdate from '@/components/MovieReviewUpdate.vue'
import axios from 'axios'
import wordcloud from 'vue-wordcloud'
import jwt_decode from "jwt-decode";



const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: 'MovieReview',
  components: {
    MovieReviewComment,
    MovieReviewUpdate,
    wordcloud,
  },
  data : function(){
    return {
      reviews: [],
      isShowSpoiler: false,
      isLike: false,
      isSpiler: false,
      authUser: null,
      formData: {
        content: '',
        user_rating: null,
        is_spoiler_self: null,
      },
      myColors: ['#793c3c', '#E1B4B4', '#DB6E6E', '#A85454'],
      defaultWords: [
      ]
    }
  },
  props: {
    movie: {
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
    getReview : function(){
      const url = SERVER_URL + '/community/movie/' + this.movie.id + '/reviews/'
      if (!(this.setToken().Authorization === "JWT null" )) {
        axios({
        method: 'get',
        url: url,
        headers: this.setToken()
      })
        .then(res => {
          this.reviews = res.data
          console.log(res)
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
          this.reviews = res.data
        })
        .catch(err => {
          console.log(err)
        })
      }  
    },
    goProfile: function(event) {
      // event.id = 유저아이디
      this.$router.push({name: 'Profile', params: { user_id: event.id }})
    },
    checkSpoiler: function(event) {
      // event.id = 리뷰아이디
      const url = SERVER_URL + '/community/movie/' + this.movie.id + '/review/' + event.id + '/review-spoiler-check/'
      axios({
        method: 'post',
        url: url,
        headers: this.setToken()
      })
        .then(res => {
          this.getReview()
        })
        .catch(err => {
          console.log(err)
        })
    },
    checkLike: function(event) {
      // event.id = 리뷰아이디
      const url = SERVER_URL + '/community/movie/' + this.movie.id + '/review/' + event.id + '/review-like-check/'
      axios({
        method: 'post',
        url: url,
        headers: this.setToken()
      })
        .then(res => {
          this.getReview()
        })
        .catch(err => {
          console.log(err)
        })
    },
    openUpdateModal: function() {
      this.$modal.show('update-modal')
    },
    closeModal: function(){
      this.$modal.hide('update-modal')
      document.location.reload(true);
    },
    deleteReview: function(review) {
      const url = SERVER_URL + '/community/movie/' + this.movie.id + '/review/' + review.id + '/'
      axios({
        method: 'delete',
        url: url,
        headers: this.setToken()
      })
        .then(res => {
          document.location.reload(true);
        })
        .catch(err => {
          console.log(err)
          console.log('발생')
          console.log(this.formData)
          console.log(url)
        })
    },
    wordClickHandler(name, value, vm) {
      console.log('wordClickHandler', name, value, vm);
    },
    getWorldCloud: function(){
      const url = SERVER_URL + '/community/movie/' + this.movie.id + '/review/wordcloud/'
      if (!(this.setToken().Authorization === "JWT null" )) {
        axios({
        method: 'get',
        url: url,
        headers: this.setToken()
      })
        .then(res => {
          this.defaultWords = res.data
          console.log(res.data)
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
          this.defaultWords = res.data
          console.log(res.data)
        })
        .catch(err => {
          console.log(err)
        })
      }
    },
    getUser: function(){
      const token = localStorage.getItem('jwt')
      if(token){
        this.isLogin = true
        this.authUser = jwt_decode(token).user_id;
      }
    }
  },
  created : function(){
    this.getReview()
    this.getWorldCloud()
    this.getUser()
  },
  filters: {
    counter: function (arr) {
      return arr.length
    }
  },
  
  
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Outfit&display=swap');
.review-username {
  font-weight: bold;
}
.go-profile:hover {
  color: #A85454;
}
.review-box {
  border-bottom: 0.1rem solid rgb(206, 206, 206);
  padding-bottom: 1rem;
  padding-top: 1rem;
}
.review-up {
  font-size: 2rem;
  display: flex;
  justify-content: space-between;
}
.spoiler-tag {
  color: gray;
}
.content-text {
  font-size: 1.5rem;
  vertical-align: middle;
}
.right-position {
  display: flex;
  justify-content: flex-end;
  margin-top: 0.5rem;
  margin-bottom: 0.3rem;
}
.checkbox-text {
  margin-left: 0.5rem;
  font-size: 20px;
}
.switch-button { 
  position: relative; 
  display: inline-block; 
  width: 55px; 
  height: 30px; 
  margin-left: auto;
  
} 
.switch-button input { 
  opacity: 0; 
  width: 0; 
  height: 0; 
} 
.onoff-switch { 
  position: absolute; 
  cursor: pointer; 
  top: 0; 
  left: 0; 
  right: 0; 
  bottom: 0; 
  border-radius:20px; 
  background-color: #ccc; 
  box-shadow: inset 1px 5px 1px #999; 
  -webkit-transition: .4s; transition: .4s; 
} 
.onoff-switch:before { 
  position: absolute; 
  content: ""; 
  height: 22px; 
  width: 22px; 
  left: 4px; 
  bottom: 4px; 
  background-color: #fff; 
  -webkit-transition: .5s; 
  transition: .4s; 
  border-radius:20px; 
} 
.switch-button input:checked + .onoff-switch { 
  background-color: #A85454; 
  box-shadow: inset 1px 5px 1px #864343; 
} 
.switch-button input:checked + .onoff-switch:before { 
  -webkit-transform: translateX(26px); 
  -ms-transform: translateX(26px); 
  transform: translateX(26px); 
}
.outfit-font {
  font-family: 'Outfit', sans-serif;
}
.review-star {
  font-size: 0.9em;
}
.heart-tag {
  margin-right: 6px;
}
.tag-hover:hover {
  color: rgb(53, 53, 53);
}
.review-time {
  font-size: 0.7em;
  margin-left: 5px;
  font-weight: 500;
  font-style: italic;
}
.update-review{
  font-size: 0.9em;
  color: rgb(172, 172, 172);
  cursor:pointer;
}
.delete-review{
  font-size: 0.9em;
  color: rgb(172, 172, 172);
  cursor:pointer;
}
.wordcloud-color {
  background-color: rgb(38, 38, 38);
}
.comment-tag {
  font-size: 1.5rem;
  color: rgb(241, 241, 241);
}
</style>