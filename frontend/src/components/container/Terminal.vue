<template>
  <div class="container">
    <h1>xterm</h1>
    <p>{{ this.containerName }}</p>
    <div ref="xtermContainer" class="xtermContainer"></div>
  </div>
</template>
<script>
import { Terminal } from "xterm";
import { FitAddon } from "xterm-addon-fit";
import { AttachAddon } from "xterm-addon-attach";
import "xterm/css/xterm.css";
const WS_URL = "ws://localhost:8765";
export default {
  name: "pod shell",
  data() {
    return {
      containerName: "",
    };
  },
  xterm: null,
  mounted() {
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
      this.xterm.destroy();
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
        this.xterm.destroy();
        this.$alert("断开");
      }
    },
  },
};
</script>