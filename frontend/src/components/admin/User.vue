<template>
    <div>
        <div style="background-color: white;margin: 0 10px;
        padding: 20px 50px 20px;">
            <el-row type="flex" justify="start" style="margin-top: 20px;">
                <el-button type="primary" icon="el-icon-plus"
                           circle
                           @click="createUserVisible=true">新建用户
                </el-button>
                <el-input
                        placeholder="搜索"
                        prefix-icon="el-icon-search"
                        v-model="searchUser"
                        style="left: 30%; width: 500px;"
                ></el-input>
            </el-row>
            <el-dialog title="新建用户"
                       :visible.sync="createUserVisible" width="30%"
                       style="padding-left: 30px; padding-right: 30px;">
                <el-form>
                    <el-form-item label="账号:"
                                  :label-width="formLabelWidth">
                        <el-input placeholder="请输入账号"
                                  v-model="addUser.login">

                        </el-input>
                    </el-form-item>
                    <el-form-item label="密码:"
                                  :label-width="formLabelWidth">
                        <el-input placeholder="请输入密码"
                                  v-model="addUser.password" type="password">

                        </el-input>
                    </el-form-item>
                    <el-form-item label="姓名:"
                                  :label-width="formLabelWidth">
                        <el-input placeholder="请输入姓名"
                                  v-model="addUser.realName">

                        </el-input>
                    </el-form-item>
                    <el-form-item label="邮箱:"
                                  :label-width="formLabelWidth">
                        <el-input placeholder="请输入邮箱"
                                  v-model="addUser.email" type="email">

                        </el-input>
                    </el-form-item>
                    <el-form-item label="用户类型:"
                                  :label-width="formLabelWidth">
                        <el-select v-model="addUser.userType">
                            <el-option label="学生" value="0"></el-option>
                            <el-option label="教师" value="1"></el-option>
                            <el-option label="管理员" value="2"></el-option>
                        </el-select>
                    </el-form-item>
                    <div style="text-align: center; margin-top:
                                20px;">
                        <el-button type="primary" @click="onSubmit"
                        >确定
                        </el-button>
                        <el-button type="info" style="margin-left: 20px;"
                                   @click="cancel">取消
                        </el-button>
                    </div>
                </el-form>
            </el-dialog>
            <el-table :data="userDataShow" stripe border>
                <el-table-column type="index">

                </el-table-column>
                <el-table-column prop="login" label="账号">

                </el-table-column>
                <el-table-column prop="password" label="密码">

                </el-table-column>
                <el-table-column prop="realName" label="姓名">

                </el-table-column>
                <el-table-column prop="email" label="邮箱" min-width="120px">

                </el-table-column>
                <el-table-column label="账号类型">
                    <template slot-scope="scope">
                        <span v-if="scope.row.userType === 0">学生</span>
                        <span v-if="scope.row.userType === 1">教师</span>
                        <span v-if="scope.row.userType === 2">管理员</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作" min-width="100px">
                    <template slot-scope="scope">
                        <el-button type="info"
                                size="mini"
                                   @click="resetPwd(scope.row)">
                            重置密码
                        </el-button>
                        <el-button type="danger"
                                   icon="el-icon-delete"
                                   size="mini" @click="deleteUser(scope.row)">
                            删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
    export default {
        name: "User",
        data() {
            return {
                formLabelWidth: '80px',
                createUserVisible: false,
                searchUser: '',
                userData: [
                ],
                addUser: {
                    login: '',
                    password: '',
                    realName: '',
                    email: '',
                    userType: '',
                }
            }
        },
        computed: {
            userDataShow() {
                if(this.searchUser === '' || this.searchUser == null) {
                    return this.userData
                }
                else return this.userData.filter(user => {
                    if("管理员".indexOf(this.searchUser) !== -1 &&
                        user['userType'] === 2) return true
                    else if("教师".indexOf(this.searchUser) !== -1 &&
                        user['userType'] === 1) return true
                    else if("学生".indexOf(this.searchUser) !== -1 &&
                        user['userType'] === 0) return true
                    else {
                        for(let item in user) {
                            if(item !== 'id' && item !== 'userType' &&
                                user[item].indexOf(this.searchUser) !== -1)
                                return true
                        }
                        return false
                    }
                })
            }
        },
        mounted() {
            this.getAll()
        },
        methods: {
            getAll() {
                this.$axios.post('/getAll')
                    .then(successResponse => {
                        this.userData = successResponse.data
                    })
            },
            resetPwd(row) {
                this.$axios.get('resetPwd', {
                    params: {
                        id: row.id
                    }
                }).then(() => {
                    alert('重置成功！')
                    this.getAll()
                })
            },
            deleteUser(row) {
                this.$axios.get('/deleteUser', {
                    params: {
                        id: row.id
                    }
                }).then(() => {
                    alert('删除成功！')
                    this.getAll()
                })
            },
            onSubmit() {
                for(let key in this.addUser) {
                    if(this.addUser[key] === '') {
                        this.sendMsg(key)
                        return
                    }
                }
                this.addUser.createdBy =
                    window.sessionStorage.getItem('realName')
                this.$axios.post("/addUser", this.addUser)
                    .then(() => {
                        alert('创建成功')
                        window.location.reload()
                    })
            },

            sendMsg(str) {
                let msg = '';
                switch (str) {
                    case 'login': msg='账号';break;
                    case 'password': msg='密码';break;
                    case 'realName': msg='姓名';break;
                    case 'email': msg='邮箱';break;
                    case 'userType': msg='用户类型'; break;
                }
                alert(msg + '不能为空');
            },

            cancel() {
                this.createUserVisible = false

            }
        }
    }
</script>

<style scoped>

</style>