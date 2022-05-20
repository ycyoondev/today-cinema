<template>
  <div class="win-modal-size">
    <movie-slider :movies="recommend_movies"></movie-slider>
  </div>
</template>

<script>
import MovieSlider from '@/components/MovieSlider.vue'

const SERVER_URL = process.env.VUE_APP_SERVER_URL


export default {
  name: 'RecommendResult',
  components: {
    MovieSlider,
  },
  data: function (){
    return {

    }
  },
  props: {
    recommend_movies : {
      type : Object,
    },
  },
  methods : {
  }
}
</script>

<style>
.win-modal-size {
  width: 1100px;
}
.ccccc {
  color: #793c3c
}
</style>