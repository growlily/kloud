<template>
  <span>
    <el-button size="small" @click="getLog">日志</el-button>

    <!-- 日志对话框 -->
    <el-dialog title="日志" :visible.sync="dialogVisible" width="60%">
      <span>Pod "{{ podName }}"</span>
      <span>{{ log }}</span>
    </el-dialog>
  </span>
</template>
<script>
export default {
  props: {
    podName: String,
  },
  data() {
    return {
      dialogVisible: false,
      log: "",
    };
  },
  methods: {
    getLog() {
      this.dialogVisible = true;
      let user = JSON.parse(window.sessionStorage.getItem("user"));
      this.$axios
        .get("/pod/log", {
          params: {
            id: user.login,
            podName: this.$props.podName,
          },
        })
        .then((resp) => {
          this.log = resp.data;
        });
    },
    onCancel() {
      this.dialogVisible = false;
    },
  },
};
</script>