<template>
  <span>
    <el-button size="small" @click="dialogVisible = true">Shell</el-button>

    <!-- 日志对话框 -->
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
      this.$axios
        .get("/pod/log", {
          params: {
            id: user.login,
            podName: this.$props.podName,
          },
        })
        .then(() => {});
      this.dialogVisible = false;
      this.$props.submitCallback();
    },
    onCancel() {
      this.dialogVisible = false;
    },
  },
};
</script>