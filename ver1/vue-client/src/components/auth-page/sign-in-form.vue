<template>
  <div class="form-container sign-in-container mt-12">
    <v-form
      action="#"
      :class="{
        'px-2': $vuetify.breakpoint.smAndDown,
        'px-8': $vuetify.breakpoint.mdAndUp,
      }"
    >
      <img src="team.png" class="team-img pt-8 m-auto" />
      <h1 class="pb-8 font-weight-bold h2">Login</h1>
      <v-text-field
        prepend-inner-icon="mdi-account"
        placeholder="Username"
        v-model="formData.username"
        filled
      ></v-text-field>
      <v-text-field
        class="no-background"
        prepend-inner-icon="mdi-lock"
        placeholder="Password"
        type="password"
        v-model="formData.password"
        filled
        @keyup.enter="login"
      ></v-text-field>
      <v-btn
        color="info"
        block
        dark
        tile
        class="pa-6 font-weight-bold"
        elevation="0"
        @click="login()"
        >Login</v-btn
      >
      <v-row class="justify-center py-10">
        <span
          :class="{
            'text-secondary forgot-password-sm': $vuetify.breakpoint.smAndDown,
            'text-secondary forgot-password-md': $vuetify.breakpoint.mdAndUp,
          }"
          >Forgot your password?</span
        >
      </v-row>
    </v-form>
  </div>
</template>
<script>
import axios from 'axios'
import jwt_decode from "jwt-decode";

const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  data: () => {
    return {
      formData: {
        username: null,
        password: null,
      }
    };
  },
  methods: {
    login: function () {
      axios({
        method: 'post',
        url: `${SERVER_URL}/accounts/api-token-auth/`,
        data: this.formData,
      })
        .then(res => {
          localStorage.setItem('jwt', res.data.token)
          const token = localStorage.getItem('jwt')
          const user = jwt_decode(token);
          this.$router.push({ name: 'Profile', params: { user_id: user.user_id } })
        })
        .catch(err => {
          console.log(err)
        })
    }
  },
  mounted() {
    this.$nextTick(function () {
      // 모든 화면이 렌더링된 후 실행합니다.
      const inputTags = document.querySelectorAll('input')
      inputTags.forEach(function(inputTag) {
        inputTag.classList.add('no-background')
      })
    })
  }
};
</script>
<style>
.no-background {
  background-color: transparent;
}
.sign-in-container {
  left: 0;
  width: 50%;
  z-index: 2;
}
.team-img {
  width: 50%;
}

.container.right-panel-active .sign-in-container {
  transform: translateX(100%);
}

.container.right-panel-active .overlay-container {
  transform: translateX(-100%);
}
.forgot-password-sm {
  font-size: 12px;
}
.forgot-password-md {
  font-size: 15px;
}
</style>