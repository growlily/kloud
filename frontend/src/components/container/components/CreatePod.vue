<template>
  <span>
    <el-button type="primary" icon="el-icon-plus" @click="dialogVisible = true"
      >新建</el-button
    >

    <!-- 新建对话框 -->
    <el-dialog title="新建" :visible.sync="dialogVisible" width="30%">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="镜像">
          <el-input v-model="form.image"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="onSubmit">立即创建</el-button>
          <el-button @click="onCancel">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </span>
</template>
<script>
const emptyForm = {
  image: "",
};
export default {
  props: {
    submitCallback: Function,
  },
  data() {
    return {
      dialogVisible: false,
      form: emptyForm,
    };
  },
  methods: {
    onSubmit() {
      let user = JSON.parse(window.sessionStorage.getItem("user"));
      this.$axios
        .post("/pod/create", {
          id: user.login,
          image: this.form.image,
        })
        .then((successResp) => {
          console.log(successResp);
        });
      this.dialogVisible = false;
      this.$props.submitCallback();
    },
    onCancel() {
      this.dialogVisible = false;
    },
  },
};
</script>
