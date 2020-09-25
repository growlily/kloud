<template>
  <div class="container">
    <h2>{{ this.podName }}</h2>
    <div ref="xtermContainer" class="xtermContainer"></div>
  </div>
</template>
<script>
import {Terminal} from "xterm";
import {FitAddon} from "xterm-addon-fit";
import {AttachAddon} from "xterm-addon-attach";
import "xterm/css/xterm.css";

export default {
  name: "terminal",
  data() {
    return {
      podName: "",
    };
  },
  xterm: null,
  mounted() {
    this.podName = this.$route.params.pod;
    const login = JSON.parse(window.sessionStorage.getItem("user")).login;
    const baseUrl = this.$axios.defaults.baseURL.replace("http://", "");
    const WS_URL = `ws://${baseUrl}/pod/shell?id=${login}&podName=${this.podName}`;
    this.socket = new WebSocket(WS_URL);
    this.socket.onopen = this.onSocketOpen;
    this.socket.onclose = this.onSocketClose;

    this.xterm = new Terminal({
      cursorBlink: true,
    });

    const attachAddon = new AttachAddon(this.socket);
    this.xterm.loadAddon(attachAddon);
    const fitAddon = new FitAddon();
    this.xterm.loadAddon(fitAddon);
    this.xterm.open(this.$refs.xtermContainer);
    fitAddon.fit();
    this.xterm.focus();
  },
  beforeDestroy() {
    if (this.xterm) {
      this.xterm.dispose();
    }
    if (this.socket) {
      this.socket.close();
    }
  },
  methods: {
    onSocketOpen() {
      if (this.socketHeart) {
        clearInterval(this.socketHeart);
      }
      this.xterm.attach(this.socket);
      this.socket.send("\n");
      this.socketHeart = setInterval(() => {
        this.socket.send("[ping]");
      }, 5000);
    },
    onSocketClose() {
      if (this.xterm) {
        this.xterm.dispose();
        this.$alert("断开");
      }
    },
  },
};
</script>