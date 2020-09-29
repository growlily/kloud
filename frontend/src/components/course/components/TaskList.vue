<template>
    <div>
        <el-row type="flex" justify="start" style="margin-top: 20px;">
            <el-button type="primary" icon="el-icon-plus"
                       circle
                       @click="createTaskVisible=true">新建任务
            </el-button>
            <el-input
                    placeholder="搜索"
                    prefix-icon="el-icon-search"
                    v-model="searchTask"
                    style="left: 30%; width: 200px;"
            ></el-input>
        </el-row>
        <!--新建任务的对话框-->
        <el-dialog
                title="新建任务"
                width="30%"
                :visible.sync="createTaskVisible"
                style="padding-left: 30px;padding-right: 30px">
            <el-form label-position="left">
                <el-form-item label="任务名:"
                              :label-width="formLabelWidth">
                    <el-input placeholder="请输入任务名"
                              v-model="taskAdd.taskName">

                    </el-input>
                </el-form-item>
                <el-form-item label="任务说明:"
                              :label-width="formLabelWidth">
                    <el-input type="textarea"
                              v-model="taskAdd.taskInfo"
                              :autosize="{minRows: 2}"
                              placeholder="(可不填)">

                    </el-input>
                </el-form-item>
                <el-form-item label="开始时间:" :label-width="formLabelWidth">
                    <el-date-picker
                            v-model="taskAdd.fromTime"
                            type="datetime"
                            placeholder="请选择时间日期"
                            :value-format="timeFormat">

                    </el-date-picker>
                </el-form-item>
                <el-form-item label="结束时间:"
                              :label-width="formLabelWidth" >
                    <el-date-picker
                            v-model="taskAdd.endTime"
                            type="datetime"
                            placeholder="请选择时间日期"
                            :value-format="timeFormat">

                    </el-date-picker>
                </el-form-item>
                <el-form-item label="延期时间:"
                              :label-width="formLabelWidth">
                    <el-date-picker
                            v-model="taskAdd.delayTime"
                            type="datetime"
                            placeholder="请选择时间日期(可不填)"
                            :value-format="timeFormat">

                    </el-date-picker>
                </el-form-item>
                <el-form-item label="参考资源:">
                    <el-upload action="/task/addTask"
                               :auto-upload="false"
                               :limit="1"
                               :on-exceed="handleExceed"
                               :on-remove="handleRemove"
                               :http-request="handleUpload"
                               :file-list="uploadFileList"
                               :on-change="handleChange"
                               ref="upload_attach">
                        <el-button size="small"
                                   type="success"
                                   class="el-icon-upload" slot="trigger">点击上传
                        </el-button>
                        <div slot="tip"
                             class="upload_tip">只能上传单个文件
                        </div>
                    </el-upload>
                </el-form-item>
                <div style="text-align: center; margin-top:
                                20px;">
                    <el-button type="primary" @click="onSubmit"
                    >提交
                    </el-button>
                    <el-button type="info" style="margin-left: 20px;"
                               @click="cancel">取消
                    </el-button>
                </div>
            </el-form>
        </el-dialog>
        <!--详细信息对话框-->
        <el-dialog title="详细信息" :visible.sync="detailDialogVisible"
                   width="30%" style="padding-left: 30px;padding-right: 30px">
            <el-form>
                <el-form-item label="任务名:"
                              :label-width="formLabelWidth">
                    <el-input placeholder="请输入任务名"
                              v-model="detailedTask.taskName">

                    </el-input>
                </el-form-item>
                <el-form-item label="任务说明:"
                              :label-width="formLabelWidth">
                    <el-input type="textarea"
                              v-model="detailedTask.taskInfo"
                              :autosize="{minRows: 2}"
                              placeholder="(可不填)">

                    </el-input>
                </el-form-item>
                <el-form-item label="开始时间:" :label-width="formLabelWidth">
                    <el-date-picker
                            v-model="detailedTask.fromTime"
                            type="datetime"
                            placeholder="请选择时间日期"
                            :format="timeFormat"
                            :value-format="timeFormat">

                    </el-date-picker>
                </el-form-item>
                <el-form-item label="结束时间:"
                              :label-width="formLabelWidth" >
                    <el-date-picker
                            v-model="detailedTask.endTime"
                            type="datetime"
                            placeholder="请选择时间日期"
                            :value-format="timeFormat">

                    </el-date-picker>
                </el-form-item>
                <el-form-item label="延期时间:"
                              :label-width="formLabelWidth">
                    <el-date-picker
                            v-model="detailedTask.delayTime"
                            type="datetime"
                            placeholder="请选择时间日期(可不填)"
                            :value-format="timeFormat">

                    </el-date-picker>
                </el-form-item>
                <el-form-item label="参考资源:">
                    <span>{{detailedTask.taskResourceName}}</span>
                    <el-upload action="/task/updateTask"
                               :auto-upload="false"
                               :limit="1"
                               :on-exceed="handleExceed"
                               :on-remove="handleRemove2"
                               :http-request="handleUpload2"
                               :file-list="updateFileList"
                               :on-change="handleChange2"
                               ref="upload_attach2">
                        <el-button size="small"
                                   type="success"
                                   class="el-icon-upload" slot="trigger">重新上传
                        </el-button>
                        <div slot="tip"
                             class="upload_tip"
                             style="padding-left: 0px;">只能上传单个文件
                        </div>
                    </el-upload>
                </el-form-item>
                <div style="text-align: center; margin-top:
                                20px;">
                    <el-button type="warning" @click="onConfirm"
                    >修改
                    </el-button>
                    <el-button type="info" style="margin-left: 20px;"
                               @click="cancel">取消
                    </el-button>
                </div>
            </el-form>
        </el-dialog>
        <el-table
                :data="taskDataShow"
                stripe
                border
        >
            <el-table-column type="index">

            </el-table-column>
            <el-table-column prop="taskName"
                             label="任务名">

            </el-table-column>
            <el-table-column prop="fromTime"
                             label="开始时间">

            </el-table-column>
            <el-table-column prop="endTime"
                             label="结束时间">

            </el-table-column>
            <el-table-column prop="delayTime"
                             label="延期时间">

            </el-table-column>
            <el-table-column label="操作" min-width="140px">
                <template slot-scope="scope">
                    <el-button type="info"
                               icon="el-icon-edit" size="mini"
                               @click="detailTask(scope.row)">
                        详细信息
                    </el-button>
                    <el-button type="primary"
                               icon="el-icon-download"
                               size="mini" @click="taskHomework(scope.row)">
                        学生作业
                    </el-button>
                    <el-button type="danger"
                               icon="el-icon-delete"
                               size="mini" @click="deleteTask(scope.row)">
                        删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>

    </div>
</template>

<script>
    export default {
        name: "TaskList",
        data() {
            return {
                timeFormat: 'yyyy-MM-dd HH:mm:ss',
                formLabelWidth: '80px',
                taskAdd: {
                    taskName: '',
                    taskInfo: '',
                    fromTime: '',
                    endTime: '',
                    delayTime: '',
                    file: null
                },
                uploadFileList: [],
                searchTask: '',
                createTaskVisible: false,
                taskData: [],
                detailDialogVisible: false,
                detailedTask: {
                    taskName: '',
                    taskInfo: '',
                    fromTime: '',
                    endTime: '',
                    delayTime: '',
                    taskResourceName: '',
                    file: null
                },
                updateFileList: []
            }
        },
        computed: {
            taskDataShow() {
                if(this.searchTask === '' || this.searchTask == null) {
                    return this.taskData
                }
                else return this.taskData.filter(task => {
                    return task.taskName.indexOf(this.searchTask) !== -1
                })
            }
        },
        mounted() {
            this.getTasks()
        },
        methods: {
            getTasks() {
                this.$axios
                    .post('/task/getAll', {'courseId': this.$route.params.id})
                    .then(successResponse => {
                        this.taskData = successResponse.data
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
                this.taskAdd.file = param.file
                for(let key in this.taskAdd) {
                    data.append(key, this.taskAdd[key])
                }
                data.append('courseId', this.$route.params.id)
                let config = {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }
                this.$axios.post(param.action, data, config)
                    .then(() => {
                        alert('任务创建成功！')
                        for(let property in this.taskAdd) {
                            this.taskAdd[property] = null
                        }
                        window.location.reload()
                    })
            },
            handleChange2(file, fileList) {
                this.updateFileList = fileList
            },
            handleRemove2(file, fileList) {
                this.updateFileList = fileList
            },
            handleUpload2(param) {
                let data = new FormData()
                this.detailedTask.file = param.file
                for(let key in this.detailedTask) {
                    data.append(key, this.detailedTask[key])
                }
                let config = {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }
                this.$axios.post(param.action, data, config)
                    .then(() => {
                        alert('任务修改成功！')
                        for(let property in this.detailedTask) {
                            this.detailedTask[property] = null
                        }
                        this.getTasks()
                        this.cancel()
                    })
            },
            cancel() {
                this.createTaskVisible = false
                this.detailDialogVisible =false
            },
            onSubmit() {
                for(let key in this.taskAdd) {
                    if((key === 'taskName' || key === 'fromTime' || key ===
                        'endTime') && this.taskAdd[key] === '') {
                        this.sendMsg(key)
                        return
                    }
                }
                if(this.uploadFileList.length !== 0) {
                    this.$refs.upload_attach.submit()
                }
                else {
                    this.taskAdd.courseId = this.$route.params.id
                    this.$axios.post('/task/addTaskWithoutResource',
                        this.taskAdd)
                        .then(() => {
                            alert('任务创建成功!')
                            window.location.reload()
                        })
                }
            },

            sendMsg(str) {
                let msg = '';
                switch (str) {
                    case 'taskName': msg='任务名';break;
                    case 'fromTime': msg='开始时间';break;
                    case 'endTime': msg='结束时间';break;
                }
                alert(msg + '不能为空');
            },

            deleteTask(row) {
                this.$confirm("确认要删除吗？")
                    .then(() => {
                        this.$axios.post('/task/deleteTask', {id: row.id})
                            .then(() => {
                                this.getTasks()
                            })
                    })
            },

            detailTask(row) {
                this.detailedTask = row
                this.detailDialogVisible = true
            },

            onConfirm() {
                for(let key in this.detailedTask) {
                    if((key === 'taskName' || key === 'fromTime' || key ===
                        'endTime') && this.detailedTask[key] === '') {
                        this.sendMsg(key)
                        return
                    }
                }
                if(this.updateFileList.length !== 0) {
                    this.$refs.upload_attach2.submit()
                }
                else {
                    this.$axios.post('/task/updateTaskWithoutResource',
                        this.detailedTask)
                        .then(() => {
                            alert('任务修改成功!')
                            for(let property in this.detailedTask) {
                                this.detailedTask[property] = null
                            }
                            this.getTasks()
                            this.cancel()
                        })
                }
            }

            // dateFormat(dateStr) {
            //
            //
            //
            //     let year = dateStr.slice(0, 4)
            //     let month = dateStr.slice(5, 7)
            //     let day =  dateStr.slice(8, 10)
            //     let hour = dateStr.slice(11, 13)
            //     let min = dateStr.slice(14, 16)
            //     let second = dateStr.slice(17)
            //     return new Date(year, parseInt(month) - 1, day, hour, min,
            //         second)
            // }
        }
    }
</script>

<style scoped>
    .upload_tip {
        font-size: 8px;
        vertical-align: text-top;
        line-height: 10px;
        padding-left: 56px;
    }
</style>