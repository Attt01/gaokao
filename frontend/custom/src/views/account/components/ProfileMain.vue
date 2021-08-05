<template>
<section class="app-main">
    <!-- <transition name="fade" mode="out-in">
      <router-view></router-view>
    </transition> -->
  <div class="app-container">
    <el-row type="flex" justify="space-around">
      <el-col :span="3">
        <!--这个tab不好看的话考虑手写-->
        <el-tabs v-model="activeTabName" tabPosition="left" type="card" @tab-click="onTabClick">
          <el-tab-pane label="高考信息" name="profile"></el-tab-pane>
          <el-tab-pane label="我的志愿表" name="myPreferenceList"></el-tab-pane>
          <el-tab-pane label="我的订单" name="myOrder"></el-tab-pane>
          <el-tab-pane label="修改密码" name="updatePwd"></el-tab-pane>
        </el-tabs>
      </el-col>
      <el-col :span="21">
        <div class="main-container">
          <router-view></router-view>
        </div>
      </el-col>
    </el-row>
  </div>
  
</section>
</template>

<script>
export default {
  name: 'ProfileMain',
  data() {
    return {
      activeTabName: 'profile'
    }
  },
  methods: {
    onTabClick(tab, event) {
      console.log(tab, event, tab.name);
      this.$router.push(tab.name);
    }
  },
  mounted() {
    console.log(this.activeTabName);
  }
}
</script>

<style scoped>
.app-main {
  /*50 = navbar  */
  min-height: calc(100vh - 50px);
  position: relative;
  overflow: hidden;
}
.app-container {
  margin-top: 50px;
  max-width: 1200px;
  margin-left: auto;
  margin-right: auto;
  background-color: tan;
}
/* .tab-container {
} */
.main-container {
  border: solid black;
  /* background-color: aqua; */
}
</style>
