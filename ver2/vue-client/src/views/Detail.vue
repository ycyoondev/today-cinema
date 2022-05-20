<template>
  <div class="detail-page">
    <!-- 영화 디테일 -->
    <div id="container detail-width">
      <div class="section">
        <div class="ratio ratio-16x9">
          <iframe width="700px" :src="movie | videoURL" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
        </div>
      </div>
      <div class="section text-white">
        <detail-info :movie="movie"></detail-info>
      </div>
      
    </div>
    <!-- 커뮤니티 -->
    <div class="section text-white"> 
      <movie-review :movie="movie"></movie-review>
      <div class="right-position">
        <button type="button" class="fill" @click="openModal">리뷰 작성</button>
      </div>
    </div>

    <!-- 비슷한 장르 -->
    <div class="section">
        <div class="section-header text-white">이런 영화는 어떠세요?</div>
        <movie-caruser :movies="same_genres"></movie-caruser>
    </div>
    <modal 
      name="write-modal" 
      class="form-container"
      width="700px"
      height="700px"
    >
        <detail-write :movie="movie" @write-review="closeModal"></detail-write>
    </modal>
  </div>
</template>

<script>
import DetailInfo from '@/components/DetailInfo.vue'
import MovieReview from '@/components/MovieReview.vue'
import MovieCaruser from '@/components/MovieCaruser.vue'
import DetailWrite from '@/components/DetailWrite.vue'
import axios from 'axios'


const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: 'Detail',
  components: {
    DetailInfo,
    MovieReview,
    MovieCaruser,
    DetailWrite,
  },
  data : function(){
    return {
      movie : null,
      same_genres : []
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
    getRecommendMovies: function(genre_id){
      const url = SERVER_URL + '/movies/genre/' + genre_id + '/best/'
      console.log(url)
      
      axios({
        method: 'get',
        url: url,
        // headers: this.setToken()
      })
        .then(res => {
          console.log('확인용')
          console.log(res.data)
          this.same_genres = res.data
        })
        .catch(err => {
          console.log(err)
        })
    },
    
    getMovie : async function(){
      const url = SERVER_URL + '/movies/' + this.$route.params.movie_pk
      axios({
        method: 'get',
        url: url,
        // headers: this.setToken()
      })
        .then(res => {
          this.movie = res.data
          const url = SERVER_URL + '/movies/genre/' + res.data.genres[0].id + '/best/'
          axios({
            method: 'get',
            url: url,
            // headers: this.setToken()
          })
            .then(res => {
              console.log(res.data)
              this.same_genres = res.data
            })
            .catch(err => {
              console.log(err)
            })
        })
        .catch(err => {
          console.log(err)
        })
    },
    
    openModal: function(){
      if (!(this.setToken().Authorization === "JWT null" )) {
        this.$modal.show('write-modal')
      } else {
        window.alert("로그인을 해주세요.")
      }
    },
    
    closeModal: function(){
      this.$modal.hide('write-modal')
      // this.getMovie()
      // this.$router.go()
      document.location.reload(true);
    },
  },
  filters : {
    videoURL : function(movie) {
      const youtubeURL = "https://www.youtube.com/embed/"
      return youtubeURL+movie.video_key
    }
  },
  created : function(){
    this.getMovie()
    this.show()
  },

  
}
</script>

<style>

.right-position {
  display: flex;
  justify-content: flex-end;
}

#detail-width {
  width: 100%;
}

.fill {
  margin-top: 1rem;
  font-size: 20px;
  font-weight: 200;
  letter-spacing: 1px;
  padding: 13px 50px 13px;
  outline: 0;
  border: 1px solid black;
  cursor: pointer;
  position: relative;
  background-color: rgba(0, 0, 0, 0);
}

.fill::after {
  content: "";
  /* background-color: #ffef95; */
  background-color: #cecece;
  width: 100%;
  z-index: -1;
  position: absolute;
  height: 100%;
  top: 7px;
  left: 7px;
  transition: 0.2s;
}

.fill:hover::after {
  top: 0px;
  left: 0px;
}
.detail-page {
  background-color: rgb(38, 38, 38);
  /* height: 2800px; */
  max-height: 100%;
}
.text-white {
  color: whitesmoke
}
</style>