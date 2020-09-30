<template>
    <div>
        <course-teacher v-if="userType === 1"></course-teacher>
        <course-student v-if="userType === 0"></course-student>
    </div>
</template>

<script>
    import CourseTeacher from "@/components/course/CourseTeacher";
    import CourseStudent from "@/components/course/CourseStudent";
    export default {
        name: "Course",
        components: {CourseStudent, CourseTeacher},
        data() {
            return {
                userType: ''
            }
        },
        mounted() {
            let user = JSON.parse(window.sessionStorage.getItem('user'))
            this.userType = user.userType;

        },
        methods: {

        }
    }
</script>

<style scoped>

</style>