<template>
    <div>
        <el-row type="flex" justify="start" style="margin-top: 20px;">
            <el-button type="primary" icon="el-icon-plus"
                       circle
                       @click="getStudentsNotInCourse">添加学生
            </el-button>
            <el-button type="danger" icon="el-icon-delete"
                       circle
                       @click="deleteOp" v-if="deleteButtonVisible">删除
            </el-button>
            <el-button type="danger"
                       circle
                       @click="realDeleteOp" v-if="!deleteButtonVisible">确认删除
            </el-button>
            <el-button type="info"
                       circle
                       @click="cancelDeleteOp" v-if="!deleteButtonVisible">取消
            </el-button>
            <el-input
                    placeholder="搜索"
                    prefix-icon="el-icon-search"
                    v-model="searchStudent"
                    style="left: 30%; width: 200px;"
            ></el-input>
        </el-row>
        <!--添加学生的对话框-->
        <el-dialog
                title="添加学生到课程"
                width="30%"
                :visible.sync="addStudentVisible"
                style="padding-left: 30px;padding-right: 30px">
            <el-button type="primary" icon="el-icon-plus"
                       circle @click="addStudentOp"
            >添加
            </el-button>
            <el-input
                    placeholder="搜索"
                    prefix-icon="el-icon-search"
                    v-model="addStudent"
                    style="width: 200px;"
            ></el-input>
            <el-table :data="studentAddDataShow" stripe border
                      height="250px" style="margin-top: 20px;" ref="table1">
                <el-table-column type="selection">

                </el-table-column>
                <el-table-column prop="login"
                                 label="学号">

                </el-table-column>
                <el-table-column prop="realName"
                                 label="姓名">

                </el-table-column>
            </el-table>
            <el-row style="margin-top: 30px;">
                <el-button size="medium" type="info"
                           @click="closeDialog">关闭</el-button>
            </el-row>
        </el-dialog>
        <el-table :data="studentDataShow" stripe border height="300px"
                  ref="table2">
            <el-table-column type="selection"
            >
            </el-table-column>
            <el-table-column type="index"
                             >
            </el-table-column>
            <el-table-column prop="login"
                             label="学号">

            </el-table-column>
            <el-table-column prop="realName"
                             label="姓名">

            </el-table-column>
            <el-table-column prop="email"
                             label="联系方式">

            </el-table-column>
        </el-table>
    </div>
</template>

<script>
    export default {
        name: "StudentList",
        data() {
            return {
                searchStudent: '',
                addStudentVisible: false,
                studentData: [
                ],
                addStudent: '',
                studentAddData: [
                ],
                deleteButtonVisible: true,
            }
        },
        mounted() {
            this.getStudents()
        },
        computed: {
            studentDataShow() {
                if(this.searchStudent === '' || this.searchStudent == null) {
                    return this.studentData
                }
                else return this.studentData.filter(student => {
                    for(let key in student) {
                        if(key !== 'id' &&
                            student[key].indexOf(this.searchStudent) !== -1)
                            return true
                    }
                    return false
                })
            },
            studentAddDataShow() {
                if(this.addStudent === '' || this.addStudent == null) {
                    return this.studentAddData
                }
                else return this.studentAddData.filter(student => {
                    for(let key in student) {
                        if(key !== 'id' &&
                            student[key].indexOf(this.addStudent) !== -1)
                            return true
                    }
                    return false
                })
            }
        },
        methods: {
            getStudents() {
                this.$axios
                    .post('/student/getAll', {'courseId':
                        this.$route.params.id})
                    .then(successResponse => {
                        this.studentData = successResponse.data
                    })
            },
            getStudentsNotInCourse() {
                this.addStudentVisible = true
                this.$axios
                    .post('/student/getAllNotInCourse', {'courseId':
                        this.$route.params.id})
                    .then(successResponse => {
                        this.studentAddData = successResponse.data

                    })
            },
            deleteOp() {
                this.deleteButtonVisible = false
            },
            cancelDeleteOp() {
                this.deleteButtonVisible = true
                this.$refs.table2.clearSelection()
            },
            realDeleteOp() {
                let mySelection = this.$refs.table2.selection
                if(mySelection.length == 0) {
                    this.cancelDeleteOp()
                    return
                }
                let list = []
                mySelection.forEach(item => {list.push(item['id'])})
                this.$axios.post('/student/deleteStudent',
                    {courseId: this.$route.params.id, list:
                    list})
                    .then(() => {
                        alert("删除成功！")
                        this.getStudents()
                        this.cancelDeleteOp()
                    })
            },
            closeDialog() {
                this.addStudentVisible = false;
                this.$refs.table1.clearSelection()
            },
            addStudentOp() {
                let mySelection = this.$refs.table1.selection
                if(mySelection.length == 0) {
                    return
                }
                let list = []
                mySelection.forEach(item => {list.push(item['id'])})
                let teacherName = window.sessionStorage.getItem('realName')
                this.$axios.post('/student/addStudent',
                    {realName: teacherName,courseId:
                    this.$route.params.id,list: list})
                    .then(() => {
                        alert("添加成功！")
                        this.getStudents()
                        this.closeDialog()
                    })
            }
        }
    }
</script>

<style scoped>

</style>