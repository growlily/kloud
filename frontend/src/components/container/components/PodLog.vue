<template>
  <span>
    <el-button size="small" @click="getLog">日志</el-button>

    <!-- 日志对话框 -->
    <el-dialog
      :title="podName"
      :visible.sync="dialogVisible"
      width="60%"
      :before-close="onCancel"
    >
      <div ref="xtermContainer" class="xtermContainer"></div>
    </el-dialog>
  </span>
</template>
<script>
import { Terminal } from "xterm";
import "xterm/css/xterm.css";
import { FitAddon } from "xterm-addon-fit";

export default {
  props: {
    course: String,
    podName: String,
  },
  data() {
    return {
      dialogVisible: false,
      log: "",
      xterm: null,
    };
  },
  methods: {
    getLog() {
      this.dialogVisible = true;
      let user = JSON.parse(window.sessionStorage.getItem("user"));
      this.$axios
        .get("/pod/logt", {
          params: {
            id: user.login,
            course: this.$props.course,
            podName: this.$props.podName,
          },
        })
        .then((resp) => {
          this.log = resp.data;
          if (this.xterm === null) {
            this.initTerm();
          }
          this.writeTerm();
        });
    },
    initTerm() {
      this.xterm = new Terminal();
      const fitAddon = new FitAddon();
      this.xterm.loadAddon(fitAddon);
      this.xterm.open(this.$refs.xtermContainer);
      fitAddon.fit();
    },
    writeTerm() {
      this.log.split("\n").map((line) => this.xterm.writeln(line));
    },
    clearTerm() {
      if (this.xterm !== null) {
        this.xterm.clear();
      }
    },
    onCancel() {
      this.clearTerm();
      this.dialogVisible = false;
    },
  },
};
</script>
