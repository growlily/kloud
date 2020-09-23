<template>
    <div>
        <div style="text-align: left;"><span
                style="margin-left: 20px;font-size: 20px;
                font-weight:
                bolder">个人信息
        </span></div>
        <el-card style="margin-top: 20px">
            <div slot="header">
                <el-menu :default-active="activeItem"
                         mode="horizontal"
                         @select="handleSelect">
                    <el-menu-item index="1">个人信息</el-menu-item>
                    <el-menu-item index="2">修改信息</el-menu-item>
                    <el-menu-item index="3">修改密码</el-menu-item>
                </el-menu>
            </div>
            <div class="card_body" v-if="activeItem === '1'">
                <p>姓名：
                    <span style="margin-left: 30px;">{{this.realName}}</span></p>
                <p>邮箱：<span style="margin-left: 30px;">{{this.email}}</span></p>
            </div>
            <div class="card_body" v-if="activeItem === '2'">
                <el-form :inline="true">
                    <el-form-item label="姓名：" >
                        <el-input v-model="changeInfoItem.realName"
                                  placeholder="请输入你的姓名"></el-input>
                    </el-form-item>
                    <br>
                    <el-form-item label="邮箱：" >
                        <el-input v-model="changeInfoItem.email"
                                  placeholder="请输入你的邮箱"></el-input>
                    </el-form-item>
                    <br>
                    <el-form-item>
                        <span style="margin-left: 52px"></span>
                        <el-button type="primary" v-on:click="changeInfo">提交
                        </el-button>
                    </el-form-item>
                </el-form>
            </div>
            <div class="card_body" v-if="activeItem === '3'">
                <el-form :inline="true" label-position="left"
                         label-width="100px">
                    <el-form-item label="旧密码：" >
                        <el-input type="password" v-model="changePwdItem.oldPwd"
                                  placeholder="请输入你的旧密码"></el-input>
                    </el-form-item>
                    <br>
                    <el-form-item label="新密码： " >
                        <el-input type="password"
                                  v-model="changePwdItem.password"
                                  placeholder="请输入你的新密码"></el-input>
                    </el-form-item>
                    <br>
                    <el-form-item label="确认密码：" >
                        <el-input type="password" v-model="repeat"
                                  placeholder="请确保两次输入的密码一致"></el-input>
                    </el-form-item>
                    <br>
                    <el-form-item>
                        <span style="margin-left: 100px"></span>
                        <el-button type="primary" v-on:click="changePwd">提交
                        </el-button>
                    </el-form-item>
                </el-form>
            </div>
        </el-card>
    </div>
</template>

<script>
    export default {
        name: "Private",
        data() {
            return {
                activeItem: "1",
                realName: null,
                email: null,
                changeInfoItem: {
                    id: '',
                    realName: '',
                    email: '',
                },

                changePwdItem: {
                    id: '',
                    password: '',
                    oldPwd:''
                },
                repeat: ''
            }
        },

        mounted() {
            let user = JSON.parse(window.sessionStorage.getItem('user'))
            this.realName =
                        user.realName
            this.email =
                    user.email
            this.changeInfoItem.id = user.id
            this.changePwdItem.id = user.id
        },
        methods: {
            handleSelect(index) {
                this.activeItem = index
            },
            changeInfo() {

                this.$axios
                    .post('/change/info', this.changeInfoItem)
                    .then(successResponse => {
                        window.sessionStorage.setItem('user', JSON.stringify(successResponse.data))
                        window.location.reload()
                    })
            },
            changePwd() {
                if(this.repeat !== this.changePwdItem.password) {
                    this.handlePwdError('两次密码输入不一致！')
                }
                else {
                    this.$axios
                        .post('/change/pwd', this.changePwdItem)
                        .then(() => {
                            window.location.reload()
                        })
                        .catch(() => {
                            alert('旧密码输入错误！')
                        })
                }
            },
            handlePwdError(msg) {
                this.changePwdItem.oldPwd = ''
                this.changePwdItem.password = ''
                this.repeat = ''
                alert(msg)
            }
        }
    }
</script>

<style scoped>

    .card_body{
        text-align: left;
        padding-left: 30px;
    }
</style>