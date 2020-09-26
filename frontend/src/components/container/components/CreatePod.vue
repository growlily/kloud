<template>
  <span>
    <el-button type="primary" icon="el-icon-plus" @click="dialogVisible = true"
      >新建</el-button
    >

    <!-- 新建对话框 -->
    <el-dialog title="新建" :visible.sync="dialogVisible" width="40%">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="镜像" prop="image">
          <el-input v-model="form.image"></el-input>
        </el-form-item>
        <el-form-item
          v-for="(env, index) in form.envVarList"
          label="环境变量"
          :key="index"
        >
          <el-col :span="10">
            <el-input v-model="env.name"></el-input>
          </el-col>
          <el-col :span="2"> = </el-col>
          <el-col :span="10">
            <el-input v-model="env.value"></el-input>
          </el-col>
          <el-col span="1" offset="1">
            <el-button
              v-if="index === 0"
              size="mini"
              icon="el-icon-plus"
              plain
              circle
              @click.prevent="addEnv()"
            ></el-button>
            <el-button
              v-else
              size="mini"
              type="danger"
              icon="el-icon-minus"
              plain
              circle
              @click.prevent="removeEnv(env)"
            ></el-button>
          </el-col>
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
export default {
  props: {
    submitCallback: Function,
  },
  data() {
    return {
      dialogVisible: false,
      form: {
        image: "",
        envVarList: [{ name: "", value: "" }],
      },
      rules: {
        image: [{ required: true, message: "请输入镜像名称", trigger: "blur" }],
      },
    };
  },
  methods: {
    removeEnv(item) {
      const index = this.form.envVarList.indexOf(item);
      if (index !== -1) {
        this.form.envVarList.splice(index, 1);
      }
    },
    addEnv() {
      this.form.envVarList.push({
        name: "",
        value: "",
      });
    },
    onSubmit() {
      let user = JSON.parse(window.sessionStorage.getItem("user"));
      this.$axios
        .post("/pod/create", {
          id: user.login,
          param: this.form,
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
