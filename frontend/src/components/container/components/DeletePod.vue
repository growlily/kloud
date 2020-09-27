<template>
  <span>
    <el-button type="danger" plain size="small" @click="dialogVisible = true"
      >删除</el-button
    >

    <!-- 删除对话框 -->
    <el-dialog title="删除" :visible.sync="dialogVisible" width="30%">
      <span>确定删除 Pod "{{ podName }}" ?</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="onSubmit">确定</el-button>
      </span>
    </el-dialog>
  </span>
</template>
<script>
export default {
  props: {
    course: String,
    podName: String,
    submitCallback: Function,
  },
  data() {
    return {
      dialogVisible: false,
    };
  },
  methods: {
    onSubmit() {
      let user = JSON.parse(window.sessionStorage.getItem("user"));
      let course = this.$props.course;
      this.$axios
          .post("/pod/delete", {
            id: user.login,
            course: course,
            podName: this.$props.podName,
          })
          .then(() => this.$message.info("正在删除"))
          .catch((err) => this.$message.error(err.message));
      this.dialogVisible = false;
      this.$props.submitCallback();
    },
    onCancel() {
      this.dialogVisible = false;
    },
  },
};
</script>