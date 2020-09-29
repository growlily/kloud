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
                          v-model="searchUndergoing" type="text"
                          style="margin-left: 25%; margin-right: 30%;"
                ></el-input>
            </el-row>
            <!--正在进行的任务-->
            <el-table :data="taskDataShowUndergoing" stripe border
                      >
                <el-table-column type="index">

                </el-table-column>
                <el-table-column prop="taskName" label="任务名">

                </el-table-column>
                <el-table-column prop="courseName" label="所属课程">

                </el-table-column>
                <el-table-column prop="fromTime" label="开始时间" sortable>

                </el-table-column>
                <el-table-column prop="endTime" label="结束时间" sortable>

                </el-table-column>
                <el-table-column prop="delayTime" label="延期时间" sortable>

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
            <!--分割线-->
            <el-divider></el-divider>
            <!--已结束的任务-->
            <el-collapse v-model="activeNames2">
                <el-collapse-item name="1">
                    <template slot="title" >
                        <span class="collapse_title">所有任务</span>
                    </template>
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
                        <el-table-column prop="fromTime" label="开始时间" sortable>

                        </el-table-column>
                        <el-table-column prop="endTime" label="结束时间" sortable>

                        </el-table-column>
                        <el-table-column prop="delayTime" label="延期时间" sortable>

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
                </el-collapse-item>
            </el-collapse>
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
            <!--上传作业对话框-->
            <el-dialog title="提交作业" :visible.sync="submitDialogVisible"
                       width="30%" style="padding-left: 30px;padding-right: 30px">
                <el-row justify="start">
                    <span>提交状态:</span><span
                        style="font-weight: bold;">{{isSubmitted}}
                </span>
                </el-row>
                <el-row>
                    <el-upload action="/taskHomework/upload"
                               :auto-upload="false" :limit="1"
                               :on-exceed="handleExceed"
                               :on-remove="handleRemove"
                               :on-change="handleChange"
                               :http-request="handleUpload"
                               :file-list="uploadFileList"
                               ref="upload_homework">
                        <el-button size="small"
                                   type="success"
                                   class="el-icon-upload" slot="trigger">点击上传
                        </el-button>
                        <div slot="tip"
                             class="upload_tip"
                             style="padding-left: 0px;">只能上传单个文件
                        </div>
                    </el-upload>
                </el-row>
                <div style="text-align: center; margin-top:
                                20px;">
                    <el-button type="primary" @click="onSubmit"
                    >确认提交
                    </el-button>
                    <el-button type="info" style="margin-left: 20px;"
                               @click="cancel">取消
                    </el-button>
                </div>
            </el-dialog>
        </div>
        <div style="text-align: left; margin-top: 20px; margin-left: 20px;">
            <span style="font-size: 20px; color: #409EFF; ">
                课程共享容器</span>
        </div>
        <div style="background-color: white;margin: 20px 10px;
        padding: 20px 50px 20px;">
            <el-collapse v-model="activeNames">
                <el-collapse-item v-for="(item, i) in myCourses" :key="i"
                                  :name="i">
                    <template slot="title" >
                        <span class="collapse_title">{{item.courseName}}</span>
                    </template>
                    <container-impl :course="item.id"></container-impl>
                </el-collapse-item>
            </el-collapse>
        </div>
    </div>
</template>

<script>
    import ContainerImpl from "@/components/container/ContainerImpl";
    export default {
        name: "CourseStudent",
        components: {ContainerImpl},
        data() {
            return {
                search: '',
                searchUndergoing: '',
                taskData: [
                ],
                detailDialogVisible: false,
                submitDialogVisible: false,
                detailedTask: {
                    taskInfo: ''
                },
                homework: {

                },
                uploadFileList: [],
                isSubmitted: '未提交',
                myCourses: [
                ],
                activeNames: [],
                activeNames2: [],
            }
        },
        mounted() {
            this.getAllTask()
            this.getAllCourses()
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
            },
            taskDataShowUndergoing() {
                let now = new Date()
                if(this.searchUndergoing === '' || this.searchUndergoing == null) {
                    return this.taskData.filter(task => {
                        return now < this.dateFormat(task['delayTime'])
                    })
                }
                else return this.taskData.filter(task => {
                    return now < this.dateFormat(task['delayTime'])
                }).filter(task => {
                    for(let item in task) {
                        if(item !== 'id' && task[item].indexOf(this.searchUndergoing)
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
            getAllCourses() {
                let id = window.sessionStorage.getItem('id')
                this.$axios.get("/course/getStudentCourses", {
                    params: {
                        id: id
                    }
                }).then(successResponse => {
                    this.myCourses =successResponse.data
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
                this.$axios.get('/taskHomework/isSubmited', {
                    params: {
                        userId: window.sessionStorage.getItem('id'),
                        taskId: row.id
                    }
                }).then(successResponse => {
                    if(successResponse.data == false) {
                        this.isSubmitted = '未提交'
                    }
                    else {
                        this.isSubmitted = '已提交'
                    }
                })
            },
            handleExceed() {
                alert('最多上传一个文件')
            },
            handleChange(file, fileList) {
                this.uploadFileList = fileList
            },
            handleRemove(file, fileList) {
                this.uploadFileList = fileList
            },
            handleUpload(param) {
                let data = new FormData()
                let userId = window.sessionStorage.getItem('id')
                let login = window.sessionStorage.getItem('login')
                let realName = window.sessionStorage.getItem('realName')
                let taskId = this.homework.id
                let taskName = this.homework.taskName
                data.append('userId', userId)
                data.append('login', login)
                data.append('realName', realName)
                data.append('taskId', taskId)
                data.append('taskName', taskName)
                data.append('file', param.file)
                let config = {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }
                this.$axios.post(param.action, data, config)
                    .then(() => {
                        alert('上传成功')
                        this.uploadFileList = []
                        this.cancel()
                    })
            },
            cancel() {
                this.submitDialogVisible = false
            },
            onSubmit() {
                if(this.uploadFileList.length == 0) {
                    alert('提交文件不能为空！')

                    return
                }
                let delayTime = this.dateFormat(this.homework.delayTime)
                if(delayTime < new Date()) {
                    alert('已超过文件提交时间！')
                    return
                }
                this.$refs.upload_homework.submit()
            },

            dateFormat(dateStr) {
                let year = dateStr.slice(0, 4)
                let month = dateStr.slice(5, 7)
                let day =  dateStr.slice(8, 10)
                let hour = dateStr.slice(11, 13)
                let min = dateStr.slice(14, 16)
                let second = dateStr.slice(17)
                return new Date(year, parseInt(month) - 1, day, hour, min,
                    second)
            }
        }
    }
</script>

<style scoped>
    .collapse_title {
        font-size: 20px;
        font-weight: bold;
        color: #9B9DA2;
    }
</style>