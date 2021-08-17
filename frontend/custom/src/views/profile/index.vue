<template>
<div class="user-content">
  <div class="app-container">
    <el-row class="user-content">
      <el-col :span="6" class="profile">
        <el-avatar class="avatar" :src="require('@/assets/img/avatar.gif')" shape="square" :size="120" fit="fill"></el-avatar>
        <!-- <img class="user-avatar" src="@/assets/img/avatar.gif"><br>
        <p><span class="title"><i class="el-icon-phone"></i>&nbsp;&nbsp;{{ user.phone }}</span></p>
        <p><span class="title"><i class="el-icon-message"></i>&nbsp;&nbsp;{{ user.email }}</span></p>
        <p><span class="title"><i class="el-icon-open"></i>&nbsp;&nbsp;{{ user.status == '1'?'启用' : '禁用' }}</span></p>
        <p><span class="title"><i class="el-icon-location-outline"></i>&nbsp;&nbsp;上海市浦东大道290弄</span></p> -->
        <br>
        <el-form class="base-info">
          <el-form-item label="用户名">
            <span>{{ user.username }}</span>
          </el-form-item>
          <el-form-item label="昵称">
            <span>{{ user.nickname }}</span>
          </el-form-item>
          <el-form-item label="VIP">
            <span>{{ user.vipIsOrNot ? "会员用户" : "您还不是会员" }}</span>
          </el-form-item>
        </el-form>
      </el-col>

      <el-col :span="18" style="padding-left:50px;">
        <el-form class="form1"  label-width="80px">
          <h4>基本信息</h4>
          <el-form-item label="高考分数">
            <span>{{ user.score }}</span>
          </el-form-item>
          <el-form-item label="排名预测">
            <span>{{ user.provinceRank }}</span>
          </el-form-item>
          <el-form-item label="选课">
            <span>{{ user.subject }}</span>
          </el-form-item>
        </el-form>
        <el-divider></el-divider>

        <el-form class="form2" label-width="80px">
          <h4>额外信息</h4>
          <el-form-item label="手机">
            <span>{{ user.phone }}</span>
          </el-form-item>
          <el-form-item label="邮箱">
            <span>{{ user.email }}</span>
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

<script src="./profile.js">
</script>


<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/common.scss";
  .profile{
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
  }
  .avatar{
    margin-top: 10px;
    margin-left: auto;
    margin-right: auto;
  }
  .base-info{
    margin-left: 40px;
  }
  .form1{
    margin-bottom: 25px ;
  }
  .form2{
    margin-top: 40px;
  }
</style>

