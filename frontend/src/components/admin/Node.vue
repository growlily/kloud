<template>
  <el-card class="box-card" v-if="userType === 2">
    <el-row type="flex" justify="start">
      <el-button icon="el-icon-refresh" @click="getNodeList">刷新</el-button>
      <el-input
        placeholder="搜索"
        prefix-icon="el-icon-search"
        v-model="searchInput"
        clearable
        style="margin-left: 40%"
      ></el-input>
    </el-row>

    <el-table :data="searchResult" stripe style="width: 100%">
      <el-table-column prop="hostname" label="主机名"></el-table-column>
      <el-table-column prop="os" label="操作系统"></el-table-column>
      <el-table-column prop="cpuCores" label="CPU核数"></el-table-column>
      <el-table-column prop="cpuPercent" label="CPU占用（%）"></el-table-column>
      <el-table-column
        prop="memTotal"
        label="内存总量（MiB）"
      ></el-table-column>
      <el-table-column
        prop="memUsage"
        label="内存占用（MiB）"
      ></el-table-column>
      <el-table-column
        prop="memPercent"
        label="内存占用（%）"
      ></el-table-column>
      <el-table-column prop="address" label="IPv4地址"></el-table-column>
    </el-table>
  </el-card>
</template>

<script>
import router from "@/router";

export default {
  name: "node",
  data() {
    return {
      userType: "",
      searchInput: "",
      nodeList: [],
    };
  },
  computed: {
    searchResult: function () {
      let search = this.searchInput.toLowerCase();
      if (search) {
        return this.nodeList.filter((nodeInfo) => {
          return Object.keys(nodeInfo).some((key) => {
            return String(nodeInfo[key]).toLowerCase().indexOf(search) > -1;
          });
        });
      }
      return this.nodeList;
    },
  },
  components: {},
  mounted() {
    let user = JSON.parse(window.sessionStorage.getItem("user"));
    this.userType = user.userType;
    if (this.userType !== 2) {
      router.go(-1);
    }
    this.getNodeList();
  },
  methods: {
    getNodeList() {
      let user = JSON.parse(window.sessionStorage.getItem("user"));
      this.$axios
        .get("/node/list", {
          params: { id: user.login },
        })
        .then((successResp) => {
          if (successResp.data) {
            this.nodeList = successResp.data;
          }
        });
    },
  },
};
</script>
<style>
.el-row {
  margin-bottom: 20px;
}

.el-button + .el-button {
  margin-left: 0 !important;
}

.el-button {
  margin-right: 10px !important;
}

.el-button:last-child {
  margin-right: 0;
}
</style>