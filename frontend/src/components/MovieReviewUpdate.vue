<template>
  <div>
    <div id="review-update-form">
      <form id="signup">
          <div class="header">
              <h3>New Review</h3>
              <p>오늘 본 영화는 어땠나요?</p>
          </div>
          <div class="sep"></div>
          <div class="inputs">
              <p id="input-title">영화 평점</p>
              <select v-model="formData.user_rating" id="vote">
                <option disabled value="">영화 평점</option>
                <option>1</option>
                <option>2</option>
                <option>3</option>
                <option>4</option>
                <option>5</option>
                <option>6</option>
                <option>7</option>
                <option>8</option>
                <option>9</option>
                <option>10</option>
              </select>
              <p id="input-title">리뷰</p>
              <textarea placeholder="" v-model="formData.content" autofocus></textarea>
              <div class="checkboxy">
                  
                  <input name="cecky" id="checky" v-model="formData.is_spoiler_self" type="checkbox" />
                  <label class="terms" for="checky">스포일러가 담겨있습니다.</label>
              </div>
              <a id="submit" href="#" @click="createReview">Write Review</a>
          </div>
      </form>
    </div>
​
  </div>
</template>

<script>
import axios from'axios'
const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: 'MovieReviewUpdate',
  data: function () {
    return {
      
    }
  },
  props: {
    movie: {
      type : Object,
    },
    review: {
      type : Object,
    },
    formData: {
      type : Object,
    },
  },
  methods: {
    setToken: function () {
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `JWT ${token}`
      }
      return config
    },
    createReview: function() {
      const url = SERVER_URL + '/community/movie/' + this.movie.id + '/review/' + this.review.id + '/'
      if (this.formData.content) {
        axios({
          method: 'put',
          url: url,
          data: this.formData,
          headers: this.setToken()
        })
          .then(res => {
            this.$emit('update-review')
            document.location.reload(true);
          })
          .catch(err => {
            console.log(err)
            console.log('발생')
            console.log(this.formData)
            console.log(url)
          })
      }
    }
  }
}

</script>

<style>
#input-title {
  font-size: 1.2rem;
  margin-bottom: 0.5rem;
  color: #424242;
  font-weight: 600;
}

#vote {
  background: #f5f5f5;
  -moz-border-radius: 3px;
  -webkit-border-radius: 3px;
  border-radius: 3px;
  /* border: none; */
  padding: 13px 10px;
  width: 100%;
  margin-bottom: 10px;
  box-shadow: inset 0px 2px 3px rgba( 0,0,0,0.1 );
  clear: both;
  color: black;
}

#signup {
    width: 670px;
    height: 670px;
    margin: auto;
    margin-top: 1rem;
    padding: 0px 25px 25px;
    background: #fff;
    box-shadow: 
        0px 0px 0px 5px rgba( 255,255,255,0.4 ), 
        0px 4px 20px rgba( 0,0,0,0.33 );
    -moz-border-radius: 5px;
    -webkit-border-radius: 5px;
    border-radius: 5px;
    display: table;
    position: static;
}

#signup .header {
    margin-bottom: 20px;
}

#signup .header h3 {
    color: #333333;
    font-size: 24px;
    font-weight: bold;
    margin-top: 1rem;
    margin-bottom: 5px;
}

#signup .header p {
    color: #8f8f8f;
    font-size: 14px;
    font-weight: 300;
    
}

#signup .sep {
    height: 1px;
    background: #e8e8e8;
    /* width: 406px; */
    /* margin: 0px -25px; */
}

#signup .inputs {
    margin-top: 25px;
}

#signup .inputs label {
    color: #8f8f8f;
    font-size: 12px;
    font-weight: 300;
    letter-spacing: 1px;
    margin-bottom: 7px;
    display: block;
}

input::-webkit-input-placeholder {
    color:    #b5b5b5;
}

input:-moz-placeholder {
    color:    #b5b5b5;
}

#signup .inputs textarea {
    background: #f5f5f5;
    height: 200px;
    font-size: 0.8rem;
    -moz-border-radius: 3px;
    -webkit-border-radius: 3px;
    border-radius: 3px;
    border: none;
    padding: 13px 10px;
    width: 100%;
    margin-bottom: 10px;
    box-shadow: inset 0px 2px 3px rgba( 0,0,0,0.1 );
    clear: both;
    color: black;
}

#signup .inputs textarea:focus {
    background: #fff;
    box-shadow: 0px 0px 0px 3px #fff38e, inset 0px 2px 3px rgba( 0,0,0,0.2 ), 0px 5px 5px rgba( 0,0,0,0.15 );
    outline: none;   
}

#signup .inputs .checkboxy {
    display: block;
    position: static;
    height: 25px;
    /* margin-top: 10px; */
    clear: both;
    
}

#signup .inputs input[type=checkbox] {
    float: left;
    margin-right: 10px;
    margin-top: 1px;
    border: 1px solid gray;
    
}

#signup .inputs label.terms {
    float: left;
    font-size: 14px;
    font-style: italic;
}

#signup .inputs #submit {
    width: 100%;
    margin-top: 20px;
    padding: 15px 0;
    color: #fff;
    font-size: 20px;
    font-weight: 500;
    letter-spacing: 1px;
    text-align: center;
    text-decoration: none;
        background: -moz-linear-gradient(
        top,
        #b9c5dd 0%,
        #a4b0cb);
    background: -webkit-gradient(
        linear, left top, left bottom, 
        from(#b9c5dd),
        to(#a4b0cb));
    -moz-border-radius: 5px;
    -webkit-border-radius: 5px;
    border-radius: 5px;
    border: 1px solid #737b8d;
    -moz-box-shadow:
        0px 5px 5px rgba(000,000,000,0.1),
        inset 0px 1px 0px rgba(255,255,255,0.5);
    -webkit-box-shadow:
        0px 5px 5px rgba(000,000,000,0.1),
        inset 0px 1px 0px rgba(255,255,255,0.5);
    box-shadow:
        0px 5px 5px rgba(000,000,000,0.1),
        inset 0px 1px 0px rgba(255,255,255,0.5);
    text-shadow:
        0px 1px 3px rgba(000,000,000,0.3),
        0px 0px 0px rgba(255,255,255,0);
    display: table;
    position: static;
    clear: both;
}

#signup .inputs #submit:hover {
    background: -moz-linear-gradient(
        top,
        #a4b0cb 0%,
        #b9c5dd);
    background: -webkit-gradient(
        linear, left top, left bottom, 
        from(#a4b0cb),
        to(#b9c5dd));
}
</style>