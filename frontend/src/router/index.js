import Vue from 'vue'
import VueRouter from 'vue-router'
import authPage from '@/views/auth-page.vue'
import Profile from '@/views/Profile.vue'
import Index from '@/views/Index.vue'
import Detail from '@/views/Detail.vue'
import Recommend from '@/views/Recommend.vue'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Index',
        component: Index,
    },
    {
        path: '/auth',
        name: 'authPage',
        component: authPage,
    },
    {
        path: '/profile/:user_id',
        name: 'Profile',
        component: Profile,
    },
    {
        path: '/movies/:movie_pk',
        name: 'Detail',
        component: Detail,
    },
    {
        path: '/recommend',
        name: 'Recommend',
        component: Recommend,
    },
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router