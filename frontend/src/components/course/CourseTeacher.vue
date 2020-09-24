<template>
    <div>
        <div style="text-align: left; margin-bottom: 10px;"><span
                style="margin-left: 20px;font-size: 20px;
                font-weight:
                bolder">我的课程
        </span></div>
        <div style="background-color: white;margin: 0 10px; padding-top: 20px;">
            <el-row style="margin-bottom: 20px">
                <div style="width: 300px; margin: auto;">
                    <el-input placeholder="搜索"
                              prefix-icon="el-icon-search"
                    ></el-input>
                </div>
            </el-row>
            <el-row >
                <el-card v-for="(key, index) in myArray" :key="index"
                         class="card_item" body-style="padding: 0px;">
                    <a href="#" style="text-decoration: none; ">
                    <div class="cover">
                       <img src="../../assets/course-cover-3.png" alt="封面">
                    </div>
                    <div class="course_title">
                        <span>大数据与云计算{{key}}</span>
                    </div>
                    <div class="course_info">
                        <i class="iconfont icon-xueqi"></i>
                        <span class="course_semester">2020秋</span>
                        <i class="iconfont icon-zongrenshu"></i>
                        <span class="course_sum">163人</span>
                    </div>
                    </a>
                </el-card>
                <el-card class="card_item card_add" body-style="padding: 0px;">

                </el-card>
            </el-row>
        </div>
    </div>
</template>

<script>
    export default {
        name: "CourseTeacher",
        data() {
            return {
                myArray: [{id: 1}, {id: 1}, {id: 1}, {id: 1}, {id: 1}, {id: 1}, {id: 1}]
            }
        }
    }
</script>

<style scoped>
    img {
        width: 268px;
        height: 150px;
        margin: 0 auto;
    }
    .card_item {
        width: 270px;
        height: 233px;
        float: left;
        margin-right: 20px;
        margin-bottom: 20px;
    }
    .course_title {
        font-size: 18px;
        color: #2c3e50;
        text-align: left;
        padding-top: 3px;
        padding-left: 5px;
    }

    .course_info {
        margin-top: 8px;
        margin-left: 5px;
        text-align: left;
        color: #9B9DA2;
    }

    .icon-xueqi, .icon-zongrenshu {
        font-size: 18px;
        padding-right: 2px;
    }

    .icon-zongrenshu {
        padding-left: 25px;
    }

    .card_add {
        
    }


</style>