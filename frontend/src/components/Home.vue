<template>
    <el-container>
        <el-aside width="250px">

            <el-col class="menu_col">
                <el-menu background-color="#31a6ee" router v-if="userType == 0">
                    <el-menu-item index="/course">
                        <span slot="title">课程信息</span>
                    </el-menu-item>
                    <el-menu-item index="/container">
                        <span slot="title">容器信息</span>
                    </el-menu-item>
                    <el-menu-item index="/logging">
                        <span slot="title">日志记录</span>
                    </el-menu-item>
                </el-menu>
                <el-menu background-color="#31a6ee" router v-if="userType == 1">
                    <el-menu-item index="/course">
                        <span slot="title">我的课程</span>
                    </el-menu-item>
                    <el-menu-item index="/logging">
                        <span slot="title">日志记录</span>
                    </el-menu-item>
                </el-menu>
            </el-col>
        </el-aside>
        <el-container>
            <el-header height="100px" class="el_header">
                <div id="header_block">
                    <img src="../assets/school_logo.png" class="img_logo">
                </div>
                <span class="header_title">云平台</span>
                <el-menu class="header_user" default-active="0">
                    <el-submenu index="1" >
                        <template slot="title">
                            <span>
                                <span
                                        class="iconfont icon-xuesheng icon_universal" v-if="userType == 0"></span>
                                <span
                                        class="iconfont icon-jiaoshi icon_universal" v-if="userType == 1"></span>
                                <span
                                        class="iconfont icon-guanliyuan icon_universal" v-if="userType == 2"></span>

                                {{this.username}}</span>
                        </template>
                        <el-menu-item index="10" @click="toPrivate"><span><span
                                class="iconfont icon-yonghu icon_universal"></span>个人信息
                        </span></el-menu-item>
                        <el-menu-item index="11" @click="logout"><span><span
                                class="iconfont icon-tuichu icon_universal"></span>退出
                        </span></el-menu-item>
                    </el-submenu>
                </el-menu>
            </el-header>
            <el-main class="el_main">
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
    import "../assets/icon/identity/iconfont.css"
    export default {
        name: "Home",
        data() {
            return {
                userType: null,
                username: null

            }
        },

        mounted() {
            let user = JSON.parse(window.sessionStorage.getItem('user'))
            this.userType = user.userType
            this.username = user.realName
        },

        methods: {
            toPrivate() {
                this.$router.replace({path: '/private'})
            },
            logout() {
                window.sessionStorage.removeItem('user')
                this.$router.replace({path: '/login'})
            }
        }
    }
</script>

<style>
    body {
        background-color: #f5f7f9;
    }
</style>

<style scoped>
    .el-aside {
        background-color: #31a6ee;
        position: fixed;
        height: 100%;

    }
    .img_logo {
        width: 195px;
        height: 40px;
        margin-top: 25px;
    }
    .menu_col {
        margin-top: 150px;
    }
    .menu_col span {
        font-size: 18px;
        color: white;
        font-weight: 600;
        text-align: center;
        letter-spacing: .6px;
    }

    .el_header {
        position: fixed;
        width: 100%;
        padding-left: 0;
        background-color: white;
    }

    .el_main {
        margin-top: 100px;
        padding-left: 256px;
        padding-right: 20px;
        background-color: #f5f7f9;
    }

    #header_block {
        left: 0;
        height: 100px;
        background-color: #31a6ee;
        width: 250px;
    }

    .header_title {
        position: fixed;
        left: 275px;
        top: 35px;
        font-size: 22px;
        font-weight: bolder;
    }

    .header_user {
        position: fixed;
        right: 20px;
        top: 25px;
        width: 200px;
    }

    .icon_universal {
        padding-right: 10px;
        font-size: 24px;
    }

</style>