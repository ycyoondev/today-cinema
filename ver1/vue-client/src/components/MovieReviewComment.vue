<template>
  <div>
    <!-- 리뷰 댓글 내용 -->
    <div>
      <div v-for="comment in comments" :key="comment.id">
        <p class="comment-flex">
          <span class="comment-size">
            <span class="comment-user">{{ comment.user.username }}</span>
            <span class="comment-content">{{ comment.content }}</span>
            <span class="comment-content italic-type"> {{ comment.created_at.substring(0,10) }}</span>
          </span>
          <button v-if="comment.user.id === authUser" @click="deleteComment(comment)" class="comment-delete">X</button>
        </p>
      </div>
    </div>
    <!-- 리뷰 댓글 작성 -->
    <div >
      <form class="comment-form">
        <textarea class="" placeholder="" v-model="formData.content"></textarea>
        <button href="#" @click="createComment">작성</button>
        
      </form>
    </div>
  </div>
</template>

<script>
import axios from'axios'
import jwt_decode from "jwt-decode";

const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: 'MovieReviewComment',
  data: function () {
    return {
      formData: {
        content: null,
      },
      comments: [],
      authUser: null,
    }
  },
  props: {
    review : {
      type : Object,
    },
    movie : {
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
    createComment: function() {
      const url = SERVER_URL + '/community/movie/' + this.movie.id + '/review/' + this.review.id + '/comments/'
      if (this.formData.content) {
        axios({
          method: 'post',
          url: url,
          data: this.formData,
          headers: this.setToken()
        })
          .then(res => {
            this.comments.push(res.data)
            this.formData.content = ''
            
          })
          .catch(err => {
            console.log(err)
          })
      }
    },
    deleteComment: function(comment) {
      const url = SERVER_URL + '/community/movie/' + this.movie.id + '/review/' + this.review.id + '/comment/' + comment.id + '/'
      axios({
        method: 'delete',
        url: url,
        data: this.formData,
        headers: this.setToken()
      })
        .then(res => {
          this.getComment()
        })
        .catch(err => {
          console.log(err)
        })
    },
    getComment: function() {
      const url = SERVER_URL + '/community/movie/' + this.movie.id + '/review/' + this.review.id + '/comments/'
      if (!(this.setToken().Authorization === "JWT null" )) {
        axios({
        method: 'get',
        url: url,
        headers: this.setToken()
      })
        .then(res => {
          this.comments = res.data
          console.log('확인용')
          console.log(url)
          console.log(this.comments)
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
          this.comments = res.data
        })
        .catch(err => {
          console.log(err)
        })
      }
    },
    getUser: function(){
      const token = localStorage.getItem('jwt')
      if(token){
        this.authUser = jwt_decode(token).user_id;
      }
    },
  },
  mounted : function(){
    this.getComment()
    this.getUser()
  },
}

</script>

<style>
.italic-type {
  font-style: italic;
  font-size: 0.8em;
  color: rgb(196, 195, 195);
}
.comment-size {
  font-size: 1.3rem;
}
.comment-user {
  font-weight: 700;
  margin-right: 4px;
}
.comment-form {
  display: flex;
  justify-content: space-between;
}
.comment-form textarea {
    background: gray;
    height: 50px;
    font-size: 1.3rem;
    -moz-border-radius: 3px;
    -webkit-border-radius: 3px;
    border-radius: 3px;
    border: none;
    /* padding: 13px 10px; */
    width: 100%;
    /* margin-right: 5px; */
    /* margin-bottom: 10px; */
    box-shadow: inset 0px 2px 3px rgba( 0,0,0,0.1 );
    clear: both;
}
.comment-form textarea:focus {
    background: rgb(105, 105, 105);
    box-shadow: 0px 0px 0px 3px #5c5c5c, inset 0px 2px 3px rgba( 0,0,0,0.2 ), 0px 5px 5px rgba( 0,0,0,0.15 );
    outline: none;  
}
.comment-form button {
  font-size: 1.3em;
  -moz-border-radius: 3px;
  -webkit-border-radius: 3px;
  border-radius: 3px;
  border: none;
  background-color: #5C2E2E;
  width: 10%;
  text-align: center;
  display:table-cell;
  vertical-align:middle; 
  color: rgb(240, 240, 240);
}
.comment-flex {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
}
.comment-delete {
  background-color: rgb(211, 211, 211);
  padding: 0.01em 0.4em;
  margin: 2px;
  border: 1px solid black;
  border-radius: 5px;
  font-size: 0.5em;
  color: black
}
.comment-delete:hover {
  background-color: rgb(124, 124, 124);
}
.card {
  background-color: rgb(54, 54, 54);
}
</style>