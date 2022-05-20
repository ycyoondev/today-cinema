<template>

  <swiper class="swiper caruser-size" ref="mySwiper2" 
		:options="swiperOptions" 
		@slideChange="slideChangeTransitionStart"
	> 
    
    <movie-item v-for="(movie, idx) in movies" :key="idx" :movie="movie" 
      :image-source="movie | imageURL"
      background-size="100% 100%"
    ></movie-item>
		<!-- pagination -->

	</swiper>

</template>


<script>
import {Swiper, } from 'vue-awesome-swiper'
import MovieItem from '@/components/MovieItem.vue'
import movieMixin from '@/mixins/movieMixin'

export default {
  name : 'MovieCarousel',
  components : {
    Swiper,
    MovieItem,
  },
  mixins : [movieMixin],
  props : {
    movies : {
      type : Array,
    },
  },
  data : function(){
    return {
      swiperOptions: {
        slidesPerView: 3,
        spaceBetween: 10,
        freeMode: true,
        mousewheel : true,
      },
    }
  },
  computed : {
    swiper : function() {
				return this.$refs.mySwiper2.$swiper;
    }
  },
  methods : {
    slideChangeTransitionStart : function() {
      //console.log(this.swiper.activeIndex); //현재 index값 얻기
    }
  },
  filters : {
    styleSwiper : function(movie){
      return "background:url("+"https://image.tmdb.org/t/p/w500"+movie.poster_path+") no-repeat; background-position: center center;"
    },
  },
  mounted() {
			//console.log('Current Swiper instance object', this.swiper)
		this.swiper.slideTo(1, 1000, false)
  }
}

</script>

<style>
.caruser-size > .swiper-wrapper > .swiper-slide  {
	height: 32vw;
}
</style>