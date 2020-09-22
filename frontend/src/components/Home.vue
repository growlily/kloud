<template>
    <el-container>
        <el-aside width="250px">

            <el-col class="menu_col">
                <el-menu background-color="#31a6ee">
                    <el-menu-item>
                        <span slot="title">课程信息</span>
                    </el-menu-item>
                    <el-menu-item>
                        <span slot="title">容器信息</span>
                    </el-menu-item>
                    <el-menu-item>
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
                <el-menu class="header_user">
                    <el-submenu>
                        <template slot="title">
                            <span>
                                <span
                                        class="iconfont icon-xuesheng icon_universal"></span>
                                <span
                                        class="iconfont icon-jiaoshi icon_universal" v-if="iconVisible"></span>
                                <span
                                        class="iconfont icon-guanliyuan icon_universal" v-if="iconVisible"></span>

                                敖远超</span>
                        </template>
                        <el-menu-item><span><span
                                class="iconfont icon-yonghu icon_universal"></span>个人信息
                        </span></el-menu-item>
                        <el-menu-item><span><span
                                class="iconfont icon-tuichu icon_universal"></span>退出
                        </span></el-menu-item>
                    </el-submenu>
                </el-menu>
            </el-header>
            <el-main class="el_main">
                <div>
                    <p>1we</p><p>1</p><p>1</p><p>1</p><p>1</p><p>1</p><p>1
                </p><p>1</p><p>1</p><p>1</p>
                    <p>1</p><p>1</p><p>1</p><p>1</p><p>1</p><p>1</p><p>1</p><p>1</p><p>1</p><p>1</p>
                    <p>1</p><p>1</p><p>1</p><p>1</p><p>1</p><p>1</p><p>1</p><p>1</p><p>1</p><p>1</p>
                    <p>1</p><p>1</p><p>1</p><p>1</p><p>1</p><p>1</p><p>1</p><p>1</p><p>1</p><p>1</p>
                    <p>1</p><p>1</p><p>1</p><p>1</p><p>1</p><p>1</p><p>1</p><p>1</p><p>1</p><p>1</p>
                </div>

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
                iconVisible: false
            }
        },
        methods: {

        }
    }
</script>

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
    }

    .el_main {
        margin-top: 100px;
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