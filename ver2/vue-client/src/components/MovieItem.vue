<template>
  <!-- <img class="swiper-slide" :src="imgSrc"/> -->
  <div
    :class="[imageClass, imageState]" 
    :style="computedStyle" 
    :data-width="imageWidth" 
    :data-height="imageHeight" 
    :data-state="imageState"
  >
    <h2>{{ movie.title }}</h2>

    <div class="color">
      <h3>{{ movie.release_date }}</h3>
    </div>
    <router-link class="btn-detail" :to="{name : 'Detail', params : {movie_pk : movie.id }}">Detail</router-link>
  
  </div>
</template>

<script>
import lazyMixin from '@/mixins/lazyMixin'


export default {
  name : 'MovieItem',
  components : {
  },
  mixins : [lazyMixin],
  data : function(){
    return {
      imageClass : "swiper-slide contentBx"
    }
  }, 
  props : {
    movie : {
      type : Object
    }
  },
  computed : {
    backgroundImage : function(){
      return "https://image.tmdb.org/t/p/w500"+this.movie.poster_path
    },
  },
  mounted : function(){
    $(".hover").mouseleave(
      function () {
        $(this).removeClass("hover");
      }
    );
  }
}
</script>

<style>
.btn-detail{
  color: black;
  background-color: white;
}

</style>