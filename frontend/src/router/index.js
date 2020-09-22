import Vue from 'vue'
import Router from "vue-router";

import Login from "@/components/Login";
import Home from "@/components/Home";

Vue.use(Router)

const routes = [
    {
        path: '/',
        name: 'default',
        redirect: '/login'
    },
    {
        path: '/login',
        name: 'login',
        component: Login
    },
    {
        path: '/home',
        name: 'home',
        component: Home
    }
]

const router = new Router({
    mode: 'history',
    routes
})

export default router
