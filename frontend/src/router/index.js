import Vue from 'vue'
import Router from "vue-router";

import Login from "@/components/Login";
import Home from "@/components/Home";
import Course from "@/components/course/Course";
import Container from "@/components/container/Container";
import Logging from "@/components/logging/Logging";
import Private from "@/components/private/Private";

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
        component: Home,
        redirect: '/course',
        children: [
            {
                path: '/course',
                name: 'course',
                component: Course
            },
            {
                path: '/container',
                name: 'container',
                component: Container
            },
            {
                path: '/logging',
                name: 'logging',
                component: Logging
            },
            {
                path: '/private',
                name: 'private',
                component: Private
            }
        ]
    }
]

const router = new Router({
    mode: 'history',
    routes
})

export default router
