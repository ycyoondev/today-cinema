<template>
  <div class="form-container sign-up-container">
    <v-form
      action="#"
      :class="{
        'px-2 mt-12': $vuetify.breakpoint.smAndDown,
        'px-8': $vuetify.breakpoint.mdAndUp,
      }"
    >
      <img src="team.png" class="team-img pt-8 m-auto" />
      <h1 class="pb-8 font-weight-bold h3">Create Account</h1>
      <v-text-field
        placeholder="Username"
        prepend-inner-icon="mdi-account"
        filled
        v-model="formData.username"
      ></v-text-field>
      <v-text-field
        placeholder="Password"
        prepend-inner-icon="mdi-lock"
        filled
        v-model="formData.password"
        type="password"
      ></v-text-field>
      <v-text-field
        placeholder="Password Confirmation"
        prepend-inner-icon="mdi-lock"
        filled
        v-model="formData.passwordConfirmation"
        type="password"
        @keyup.enter="signup"
      ></v-text-field>
      <v-btn
        color="info"
        block
        dark
        tile
        class="pa-6 font-weight-bold"
        elevation="0"
        @click.native="signup()"
        >Sign Up</v-btn
      >
    </v-form>
  </div>
</template>
<script>
import axios from 'axios'
import jwt_decode from "jwt-decode";

const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  data: function () {
    return {
      formData: {
        username: null,
        password: null,
        passwordConfirmation: null,
      }
    }
  },
  methods: {
    signup: function () {
      axios({
        method: 'post',
        url: `${SERVER_URL}/accounts/signup/`,
        data: this.formData
      })
        .then(() => {
          // 바로 로그인

          axios({
            method: 'post',
            url: `${SERVER_URL}/accounts/api-token-auth/`,
            data: this.formData
          })
            .then(res => {
              localStorage.setItem('jwt', res.data.token)
          const token = localStorage.getItem('jwt')
          const user = jwt_decode(token);
          this.$router.push({ name: 'Profile', params: { user_id: user.user_id } })
            })
        })
        .catch(err => {
          console.log(err)
        })
    }
  }
};
</script>
<style scoped>
.sign-up-container {
  left: 0;
  width: 50%;
  opacity: 0;
  z-index: 1;
}
.team-img {
  width: 50%;
}

.container.right-panel-active .sign-up-container {
  transform: translateX(100%);
  opacity: 1;
  z-index: 5;
  animation: show 0s;
}

@keyframes show {
  0%,
  49.99% {
    opacity: 0;
    z-index: 1;
  }

  50%,
  100% {
    opacity: 1;
    z-index: 5;
  }
}
</style>