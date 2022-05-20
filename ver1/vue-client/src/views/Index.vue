<template>
  <div class="index-page">
    <movie-slider :movies="best_movies"></movie-slider>
    <div class="section">
      <div class="section-header index-text">
        <span>오늘의 {{ genre_best1.genre }} 영화</span>
        </div>
      <movie-caruser :movies="genre_best1.data"></movie-caruser>
    </div>
    <div class="section">
      <div class="section-header index-text">오늘의 {{ genre_best2.genre }} 영화</div>
      <movie-caruser :movies="genre_best2.data"></movie-caruser>
    </div>

  </div>
</template>

<script>
import axios from 'axios'
import MovieSlider from '@/components/MovieSlider.vue'
import MovieCaruser from '@/components/MovieCaruser.vue'

// const SERVER_URL = 'http://127.0.0.1:8000'
const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: 'Index',
  components: {
    MovieSlider,
    MovieCaruser,
  },
  data: function (){
    return {
      best_movies : [],
      genre_best1 : {},
      genre_best2 : {},
    }
  },
  methods : {
    getBestMovies : function(){
      axios({
        method: 'get',
        url: `${SERVER_URL}/movies/best/`,
      })
        .then(res => {
          this.best_movies = res.data.best
          this.genre_best1 = res.data.genre_best1
          this.genre_best2 = res.data.genre_best2

        })
        .catch(err => {
          console.log(err)
        })
    }
  },
  created : function(){
    this.getBestMovies()
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
    $(".hover").mouseleave(
      function () {
        $(this).removeClass("hover");
      }
    );
  }
}
</script>

<style>
.section {
  margin : 20px;
}
.section-header {
  color : black;
  margin-bottom: 10px;
  padding-left: 10px;
  text-transform: uppercase;
  font-size: 2rem;
  font-weight: 700;
  display: flex;
  align-items: center;
}
.index-page {
  background-color: rgb(38, 38, 38);
  height: 2200px;
}
.index-text {
  color: rgb(255, 255, 255);
}

</style>