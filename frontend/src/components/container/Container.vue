<template>
  <el-card class="box-card">
    <el-row type="flex" justify="start">
      <create-pod :submitCallback="getPodList"></create-pod>
      <el-button icon="el-icon-refresh" @click="getPodList">刷新</el-button>
      <el-input
          placeholder="搜索"
          prefix-icon="el-icon-search"
          v-model="searchInput"
          clearable
          style="margin-left: 40%"
      ></el-input>
    </el-row>

    <el-table :data="searchResult" stripe style="width: 100%">
      <el-table-column prop="name" label="名称"> </el-table-column>
      <el-table-column prop="image" label="镜像"> </el-table-column>
      <el-table-column prop="memUsage" label="已用内存（MB）">
      </el-table-column>
      <el-table-column prop="status" label="状态"> </el-table-column>
      <el-table-column prop="action" label="操作" width="240%">
        <template slot-scope="scope">
          <pod-log :podName="scope.row.name"></pod-log>
          <pod-shell
            :podName="scope.row.name"
          ></pod-shell>
          <delete-pod
            :podName="scope.row.name"
            :submitCallback="getPodList"
          ></delete-pod>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script>
import CreatePod from "./components/CreatePod.vue";
import PodLog from "./components/PodLog.vue";
import PodShell from "./components/PodShell.vue";
import DeletePod from "./components/DeletePod.vue";

export default {
  name: "Container",
  data() {
    return {
      searchInput: "",
      podList: [],
    };
  },
  computed: {
    searchResult: function () {
      let search = this.searchInput.toLowerCase();
      if (search) {
        return this.podList.filter((podInfo) => {
          return Object.keys(podInfo).some((key) => {
            return String(podInfo[key]).toLowerCase().indexOf(search) > -1;
          });
        });
      }
      return this.podList;
    },
  },
  components: {
    CreatePod,
    PodLog,
    PodShell,
    DeletePod,
  },
  mounted() {
    this.getPodList();
  },
  methods: {
    getPodList() {
      let user = JSON.parse(window.sessionStorage.getItem("user"));
      this.$axios
        .get("/pod/list", {
          params: { id: user.login },
        })
        .then((successResp) => {
          if (successResp.data) {
            this.podList = successResp.data;
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