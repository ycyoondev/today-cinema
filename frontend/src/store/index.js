import Vue from 'vue'
import Vuex from 'vuex'
import { authPageModule } from './auth-store/index.js';
Vue.use(Vuex)
export const store = new Vuex.Store({
    state: {
        user: {},
        nextPage : '',
        nextparams: {},
    },
    mutations: {
        GET_USER: function (state, decoded) {
            state.user = decoded
        },
        SETNEXTPAGE : function(state, res){

            if (res.name) {
              state.nextPage=res.name
            } else {
              state.nextPage=''
              state.nextparams= {}
            }
            
            console.log(res.params)
            if (res.params !={} ) { // 키가 한개라도 있으면
              state.nextparams= res.params
            }else {
              state.nextparams= {}
            }
        },
    },
    actions: {
        getUser: function ({ commit }, decoded) {
            commit('GET_USER', decoded)
        },
        setNextPage : function({commit}, res){
            commit('SETNEXTPAGE', res)
        },
    },
    getters: {},
    modules: {
        authPageModule,
    }
});