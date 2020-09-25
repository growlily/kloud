<template>
    <div>
        <div class="course_item_main">
            <div style="float: left;">
            <el-button type="primary" style="font-size: 16px; "><span
                    class="el-icon-back" @click.native="$router.replace({path:
                    '/course'})"></span>返回
            </el-button>
            </div>
            <div style="float:right;">
            <el-button type="danger" style="font-size: 16px;"><span
                    class="el-icon-delete"></span>
                删除
            </el-button>
            </div>
            <div style="width: 100%; height: 50px; "></div> <!--背景填充-->
            <el-row style="margin-bottom: 40px;"><span
                    class="course_title">大数据技术综合实验
            </span><span>(2020
                秋)</span></el-row>
            <el-collapse v-model="activeNames">
                <el-collapse-item name="1">
                    <template slot="title" >
                        <span class="collapse_title">任务列表</span>
                    </template>
                    <div>这次不lorem了</div>
                </el-collapse-item>
                <el-collapse-item name="2" >
                    <template slot="title">
                        <span class="collapse_title">学生列表</span>
                    </template>
                    <div>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab architecto, atque commodi corporis culpa, dicta dignissimos eligendi ex facilis ipsa iure magni minus nisi rerum sed sint tenetur ut vitae.</div>
                </el-collapse-item>
                <el-collapse-item name="3" >
                    <template slot="title" >
                        <span class="collapse_title">容器列表</span>
                    </template>
                    <div>
                        这里要显示一堆容器哈哈哈哈
                    </div>
                </el-collapse-item>
            </el-collapse>
        </div>
    </div>
</template>

<script>
    export default {
        name: "CourseItem",
        data() {
            return {
                activeNames: ['1']
            }
        },
        methods: {

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