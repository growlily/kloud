<template>
    <div>
        <el-row type="flex" justify="start" style="margin-top: 20px;">
            <el-button type="primary" icon="el-icon-plus"
                       circle
                       @click="createTaskVisible=true">新建任务
            </el-button>
            <el-button type="danger" icon="el-icon-delete"
                       circle>批量删除
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
        <el-table
                :data="taskData"
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
            <el-table-column label="操作">
                <template >
                    <el-button type="info"
                               icon="el-icon-edit" size="mini">
                        详细信息
                    </el-button>
                    <el-button type="danger"
                               icon="el-icon-delete"
                               size="mini">
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
                taskData: [
                    {
                        taskName: "综合实践作品打包云项目",
                        fromTime: '2020-09-21 00:00:00',
                        endTime: '2020-09-30 08:00:00',
                        delayTime: '2020-10-07 00:00:00'
                    },
                    {
                        taskName: "大数据分析实验报告（个人）",
                        fromTime: '2020-09-28 23:59:59',
                        endTime: '2020-09-30 23:59:59',
                        delayTime: '2020-10-07 00:00:00'
                    }
                ]
            }
        },
        methods: {
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
                        window.location.reload()
                    })
            },
            cancel() {
                this.createTaskVisible = false
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
                    this.$axios.post('/task/addTaskWithoutResource', this.taskAdd)
                        .then(() => {
                            alert('任务创建成功')
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
            }
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