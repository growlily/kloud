<template>
    <div>
        <div style="text-align: left; margin-bottom: 10px;"><span
                style="margin-left: 20px;font-size: 20px;
                font-weight:
                bolder"><el-button type="text" style="font-size: 20px;
                font-weight:
                bolder"
                                   >我的课程
        </el-button>
<!--            <span class="el-icon-arrow-right" v-if="isCourseItem()"></span>-->
<!--            <span style="margin-left: 6px" v-if="isCourseItem()">详细信息</span>-->
        </span></div>
        <div style="background-color: white;margin: 0 10px;
        padding: 20px 50px 20px;">
            <el-row type="flex" justify="start">
                <el-input placeholder="搜索任务"
                          prefix-icon="el-icon-search"
                          v-model="search" type="text"
                          style="margin-left: 25%; margin-right: 30%;"
                ></el-input>
            </el-row>
            <el-table :data="taskDataShow" stripe border
                      >
                <el-table-column type="index">

                </el-table-column>
                <el-table-column prop="taskName" label="任务名">

                </el-table-column>
                <el-table-column prop="courseName" label="所属课程">

                </el-table-column>
                <el-table-column prop="fromTime" label="开始时间">

                </el-table-column>
                <el-table-column prop="endTime" label="结束时间">

                </el-table-column>
                <el-table-column prop="delayTime" label="延期时间">

                </el-table-column>
                <el-table-column label="操作" min-width="100px">
                    <template slot-scope="scope">
                        <el-button type="info"
                                size="mini"
                                @click="detailTask(scope.row)">
                            详细信息
                        </el-button>
                        <el-button type="primary"
                                   size="mini"
                                   @click="submitTask(scope.row)">
                            提交作业
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <!--详细信息对话框-->
            <el-dialog title="详细信息" :visible.sync="detailDialogVisible"
                       width="30%" style="padding-left: 30px;padding-right: 30px">
                <span style="font-size: 16px;font-weight: bold">{{detailedTask
                    .taskInfo}}</span>
                <el-divider></el-divider>
                <span>课程资源：</span>
                <br>
                <span style="margin-bottom: 20px;">{{detailedTask
                    .taskResourceName}}</span>
                <br>
                <el-button type="info" style="margin-top: 20px;"
                           v-if="detailedTask.taskResource !== null"
                           @click="download">下载资源
                </el-button>
                <el-divider></el-divider>
            </el-dialog>
            <!--上传作业-->
            <el-dialog title="提交作业" :visible.sync="submitDialogVisible"
                       width="30%" style="padding-left: 30px;padding-right: 30px">

            </el-dialog>
        </div>
        <div style="text-align: left; margin-top: 20px; margin-left: 20px;">
            <span style="font-size: 20px; color: #409EFF">课程共享容器</span>
        </div>
    </div>
</template>

<script>
    export default {
        name: "CourseStudent",
        data() {
            return {
                search: '',
                taskData: [
                ],
                detailDialogVisible: false,
                submitDialogVisible: false,
                detailedTask: {
                    taskInfo: ''
                },
                homework: {

                }
            }
        },
        mounted() {
            this.getAllTask()
        },
        computed: {
            taskDataShow() {
                if(this.search === '' || this.search == null) {
                    return this.taskData
                }
                else return this.taskData.filter(task => {
                    for(let item in task) {
                        if(item !== 'id' && task[item].indexOf(this.search)
                            !== -1) return true
                    }
                    return false
                })
            }
        },
        methods: {
            getAllTask() {
                let id = window.sessionStorage.getItem('id')
                this.$axios.get("/task/getAllStudentTasks", {
                    params: {
                        id: id
                    }
                }).then(successResponse => {
                    this.taskData =successResponse.data
                })
            },
            detailTask(row) {
                this.detailedTask = row
                this.detailDialogVisible = true
                this.$axios.get("/task/getOne", {
                    params: {
                        id: row.id
                    }
                }).then(successResponse => {
                    this.detailedTask = successResponse.data
                })
            },
            download() {
                window.open(this.detailedTask.taskResource)
            },
            submitTask(row) {
                this.homework = row
                this.submitDialogVisible = true
            }
        }
    }
</script>

<style scoped>

</style>