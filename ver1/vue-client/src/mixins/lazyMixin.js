export default {
  //https://github.com/darrynten/vue-lazy-background-images
  props: {
    imageSource: {
      type: String,
      required: true
    },
    imageErrorCallback: {
      type: Function,
      required: false,
      default: function() {}
    },
    imageSuccessCallback: {
      type: Function,
      required: false,
      default: function() {}
    },
    backgroundSize: {
      type: String,
      required: false,
      default: 'cover'
    }
  },
  data() {
    return {
      imageWidth: 0,
      imageHeight: 0,
      imageState: 'loading',
      asyncImage: new Image(),
      errorImage : require("@/assets/movies/error.png"),
      //https://icons8.com/preloaders/
      loadingImage : require("@/assets/movies/loadimage.gif"),
    }
  },
  computed: {
    computedStyle() {
      if (this.imageState === 'loading') {
        return 'background-image: url(' + this.loadingImage + '); background-size: ' + this.backgroundSize
      }
      if (this.imageState === 'error') {
        return 'background-image: url(' + this.errorImage + '); background-size: ' + this.backgroundSize
      }
      if (this.imageState === 'loaded') {
        return 'background-image: url(' + this.asyncImage.src + '); background-size: ' + this.backgroundSize
      }
      return '';
    }
  },
  methods: {
    fetchImage() {
      // 이미지가 성공적으로 load 되었을 때
      this.asyncImage.onload = this.imageOnLoad
      //이미지가 load에서 error 났을 경우
      this.asyncImage.onerror = this.imageOnError
      this.imageState = 'loading'
      this.asyncImage.src = this.imageSource
    },
    imageOnLoad() {
      this.imageState = 'loaded'
      this.imageWidth =  this.asyncImage.naturalWidth
      this.imageHeight = this.asyncImage.naturalHeight
      this.imageSuccessCallback()
    },
    imageOnError() {
      this.imageState = 'error'
      this.imageErrorCallback()
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.fetchImage()
    })
  }
}