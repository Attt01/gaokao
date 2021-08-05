<template>
<div class="user-content">
  <div class="app-container">
    <el-row class="user-content" style="">
      <el-col :span="6" class="profile">
        <img class="user-avatar" src="@/assets/img/avatar.gif"><br>
        <!-- <p><span class="title"><i class="el-icon-phone"></i>&nbsp;&nbsp;{{ user.phone }}</span></p>
        <p><span class="title"><i class="el-icon-message"></i>&nbsp;&nbsp;{{ user.email }}</span></p>
        <p><span class="title"><i class="el-icon-open"></i>&nbsp;&nbsp;{{ user.status == '1'?'启用' : '禁用' }}</span></p>
        <p><span class="title"><i class="el-icon-location-outline"></i>&nbsp;&nbsp;上海市浦东大道290弄</span></p> -->
      </el-col>
      <el-col :span="18" style="padding-left:10px;">
        <el-form  label-width="80px">
          <h3>基本信息</h3>
          <el-form-item label="高考分数">
            <span>{{ user.name }}</span>
          </el-form-item>
          <el-form-item label="排名预测">
            <span> {{ user.sex = 1 ? '男' : '女' }}</span>
          </el-form-item>
          <el-form-item label="选课">
            <span> {{ user.birthday }}</span>
          </el-form-item>
          <h3>额外信息</h3>
          <el-form-item label="手机">
            <span>{{ user.phone }}</span>
          </el-form-item>
          <el-form-item label="邮箱">
            <span> {{ user.email }}</span>
          </el-form-item>
          <el-form-item label="其它">
            <span> 暂时不知道还有什么信息</span>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</div>
</template>

<script src="./profile.js"></script>


<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/common.scss";
</style>

