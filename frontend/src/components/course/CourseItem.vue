<template>
    <div>
        <div class="course_item_main">
            <div style="float: left;">
            <el-button type="primary" style="font-size: 16px;"
                       @click="$router.replace({path: '/course'})"><span
                    class="el-icon-back"></span>返回
            </el-button>
            </div>
            <div style="float:right;">
            <el-button type="danger" style="font-size: 16px;"
                       @click="deleteCourse"><span
                    class="el-icon-delete"></span>
                删除
            </el-button>
            </div>
            <div style="width: 100%; height: 50px; "></div> <!--背景填充-->
            <el-row style="margin-bottom: 40px;"><span
                    class="course_title">大数据技术综合实验
            </span><span>(2020
                秋)</span></el-row>
            <!--内容-->
            <el-collapse v-model="activeNames" accordion>
                <!--任务列表-->
                <el-collapse-item name="1">
                    <template slot="title" >
                        <span class="collapse_title">任务列表</span>
                    </template>
                    <task-list></task-list>
                </el-collapse-item>
                <!--学生列表-->
                <el-collapse-item name="2" >
                    <template slot="title">
                        <span class="collapse_title">学生列表</span>
                    </template>

                </el-collapse-item>
                <!--容器列表-->
                <el-collapse-item name="3" >
                    <template slot="title" >
                        <span class="collapse_title">容器列表</span>
                    </template>
                    <container-impl :course="$route.params.id"></container-impl>
                </el-collapse-item>
            </el-collapse>
        </div>
    </div>
</template>

<script>
    import TaskList from "@/components/course/components/TaskList";
    import ContainerImpl from "@/components/container/ContainerImpl";
    export default {
        name: "CourseItem",
        components: {ContainerImpl, TaskList},
        data() {
            return {
                activeNames: '1'
            }
        },
        methods: {
            deleteCourse() {
                this.$confirm("确认删除？")
                .then(() => {
                    let courseId = this.$route.params.id
                    let teacherId = window.sessionStorage.getItem('id')
                    this.$axios.post("/course/deleteCourse", {'id': courseId,
                        'teacherId': teacherId})
                        .then(() => {
                            this.$router.replace({path: '/course'})
                            window.location.reload()
                        })
                })
            },

        }
    }
</script>

<style scoped>
    .course_item_main {
        background-color: white;
        margin: 0 10px;
        padding: 10px 20px;
    }
    .course_title {
        font-size: 30px;
        font-weight: bolder;

    }

    .collapse_title {
        font-size: 20px;
        font-weight: bold;
        color: #9B9DA2;
    }

</style>