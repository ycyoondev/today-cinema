<template>
  <div class="ibox-content">
    <div class="wleft" :style="first_movie |imageURL |backgroundStyle" @click="selectMovie(first_movie)" ref="wleft"></div>
    <div class="wright" :style="second_movie |imageURL |backgroundStyle" @click="selectMovie(second_movie)" ref="wright"></div>
    <div class="versus" ref="versus"></div>
    <div class="title">
      
      <span v-if="max_count>7">
        16강&nbsp;&nbsp;&nbsp;
        {{ count }} / {{ max_count }}
      </span>
      <span v-else-if="max_count>3">
        8강&nbsp;&nbsp;&nbsp;
        {{ count }} / {{ max_count }}
      </span>
      <span v-else-if="max_count===2">
        4강 {{ count }} / {{ max_count }}
      </span>
      <span v-else>
        결승전
      </span>
      
    </div>
    <div class="text">
      <div id="wleftn" ref="wleft-text"></div>
      <div id="wrightn" ref="wright-right"></div>
    </div>
    <modal 
      name="win-modal" 
      class=""
      width="1100px"
      height="780px"
    >
    <h1 class="win-text">오늘의 추천 영화!</h1>
        <recommend-result :recommend_movies="recommend_movies"></recommend-result>
    </modal>
  </div>
</template>

<script>
import axios from 'axios'
import _ from 'lodash'
import movieMixin from "@/mixins/movieMixin"
import anime from 'animejs/lib/anime.es.js';
import RecommendResult from '@/components/RecommendResult.vue'
import { mapState } from 'vuex'
import { mapActions } from 'vuex'

const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name : "Recommend", 
  components: {
    RecommendResult,
  },
  data : function(){
    return {
      moives : [],
      first_movie : Object,
      second_movie : Object,
      count : 1,
      max_count : 8,
      checkDouble : true,
      recommend_movies : [],
    }
  },
  methods : {
    ...mapActions([
      'setNextPage'
    ]),
    setToken: function () {
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `JWT ${token}`
      }
      return config
    },
    getMovies : function(){
      const url = SERVER_URL + '/movies/tournament/16/'
      axios({
        method : 'get',
        url: url,
        headers: this.setToken(),
      })
      .then( res => {
        this.movies = res.data
        //console.log(res.data)
        this.first_movie = this.movies.shift()
        this.second_movie = this.movies.shift()
        this.movies = _.shuffle(this.movies)
      })
    },
    selectMovie : function(select_movie){
      
      if (this.checkDouble){
        this.checkDouble = false
        this.count+=1
        if (this.count > this.max_count){
          this.count = 1
          this.max_count = this.max_count / 2

        }
        this.movies.push(select_movie)
        // 애니메이션 관련
        const wleft = this.$refs.wleft
        const wright = this.$refs.wright

        if (select_movie === this.first_movie){

          this.selectAnimation(wleft)

        }else {
          this.selectAnimation(wright)
        }
        // 데이터 관련 - 딜레이 0.7초
        setTimeout(() => {
          if(this.movies.length > 1 ) {
          this.first_movie = this.movies.shift()
          this.second_movie = this.movies.shift()
          }else {
            const movie = this.movies.shift()
            this.postWinMovie(movie)
          }
          this.checkDouble = true
        }, 700);
        
      }
    },
    postWinMovie : function(movie){
      const url = SERVER_URL + '/movies/tournament/'
      axios({
        method: 'post',
        url: url + `${movie.id}/`,
        headers: this.setToken(),
      })
      .then((res) =>{
        // 우슬 결과
        console.log('우승 결과')
        console.log(res)
        this.recommend_movies = res.data
        this.openModal()
        // this.$router.push({name: 'Detail', params: { movie_pk: movie.id }})
      })
      .catch((err) =>{
        
        this.$router.push({name: 'Detail', params: { movie_pk: movie.id }})
      })
    },
    selectAnimation : function(ele){
      const xMax = 16
      anime({
        targets : ele,
        duration: 550,
        translateX: [
          {
            value: xMax * -1,
          },
          {
            value: xMax,
          },
          {
            value: xMax/-2,
          },
          {
            value: xMax/2,
          },
          {
            value: 0
          }
        ],
      })
    },
    openModal: function(){
      this.$modal.show('win-modal')
    },
  },
  mixins : [movieMixin],
  computed : {
    ...mapState([
      'token'
    ])
  },
  filters : {
    backgroundStyle : function(url){
      return `background-image: url('${url}');`
    }
  },
  created : function(){
    
    this.getMovies()
  }
}
</script>

<style>
.win-text {
  font-size: 50px;
  background-color: black;
  color: aliceblue;
  font-family: 'Dongle', sans-serif;
}
.ibox-content {
  height:90vh;
  text-align:center;
  padding:0px;
  background-color:#181818;
}
.ibox-content .wleft{
  width:50%;
  height:100%;
  max-width:50%;
  max-height:100%;
  
  background-repeat:no-repeat;
  background-size:contain;
  background-position:right center;
  position:relative;
  z-index:1;  
}
.ibox-content .wright{
  width:50%;
  height:100%;
  max-width:50%;
  max-height:100%;
  
  background-repeat:no-repeat;
  background-size:contain;
  background-position:left center;
  position:relative;
  left:50%;top:-100%;
  z-index:2;
}
.ibox-content .versus{
  width:100%;
  height:calc(7 * (1vw + 1vh - 1vmin));
  max-width:100%;
  max-height:50%;
  background-image:url('../assets/tournament/versus2.png');
  background-repeat:no-repeat;
  background-size:contain;
  background-position:center top;
  position:relative;
  left:0%;
  top:-155%;
  z-index:3;
  pointer-events:none;
}

.ibox-content .title{
  font-family: 'Dongle', sans-serif;
  width:100%;
  height:calc(3 * (1vw + 1vh - 1vmin));
  max-width:100%;
  max-height:50%;
  position:relative;
  left:0%;
  top:-200%;
  transform: translateY(-100%);
  z-index:4;
  line-height:calc(3 * (1vw + 1vh - 1vmin));
  background-color: rgba( 0, 0, 0, 0.5 );
  color:white;
  font-size:calc(3 * (1vw + 1vh - 1vmin))  
} 


.ibox-content .text {
  width:100%;
  height:calc(7 * (1vw + 1vh - 1vmin));
  max-width:100%;
  max-height:50%;
  position:relative;
  left:0%;
  top:-155%;
  z-index:4;
  color:white;
  font-size:calc(2 * (1vw + 1vh - 1vmin));
  text-shadow:-1px 0 #000000,0 1px #000000,1px 0 #000000,0 -1px #000000;
  -moz-text-shadow:-1px 0 #000000,0 1px #000000,1px 0 #000000,0 -1px #000000;
  -webkit-text-shadow:-1px 0 #000000,0 1px #000000,1px 0 #000000,0 -1px #000000;
  pointer-events:none;
}
.ibox-content .text #wleftn{
  width:50%;
  display:inline-block;
  text-align:right;
  padding-right:10%
}



.ibox-content .text #wrightn{
  width:50%;
  display:inline-block;
  text-align:left;
  padding-left:10%
}
</style>