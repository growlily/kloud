<template>
  <router-link :to='termLink' target="_blank">
    <el-button size="small">终端</el-button>
  </router-link>
</template>
<script>
export default {
  props: {
    podName: String,
    course: String
  },
  data() {
    return {
      termLink: '',
    };
  },
  mounted() {
    this.termLink = `/terminal/${this.$props.course}/${this.$props.podName}`
  },
};
</script>