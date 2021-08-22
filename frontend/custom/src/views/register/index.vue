<template>
  <div>
    <user-info-dialog></user-info-dialog>
    <el-row type="flex" justify="space-around">
    <el-col :span="12">
      <el-card>
    <el-form ref="regForm" :model="regForm" :rules="regRules" label-position="left" label-width="80px">

      <div class="title-container">
        <h3 class="title"> 志愿推荐系统注册 </h3>
        <!-- <lang-select class="set-language"/> -->
      </div>

      <el-form-item prop="nickname" label="昵称">
        <el-input
          v-model="regForm.nickname"
          name="nickname"
          type="text"
        />
      </el-form-item>

      <el-form-item prop="phone" label="手机号">
        <el-input
          v-model="regForm.phone"
          name="phone"
          type="text"
        />
      </el-form-item>

      <el-form-item prop="password" label="密码">
        <el-input
          :type="pwdType"
          v-model="regForm.password"
          name="password"
        />
        <!-- <span class="show-pwd" @click="showPwd">
          <svg-icon icon-class="eye" />
        </span> -->
      </el-form-item>

      <el-form-item prop="confirmpwd" label="确认密码">
        <el-input
          :type="pwdType"
          v-model="regForm.confirmpwd"
          name="confirmpwd"
        />
      </el-form-item>

      <el-form-item prop="veryCode" label="验证码">
        <el-input
          v-model="regForm.veryCode"
          name="veryCode"
        >
        <template slot="append">
          <el-button type="primary" @click="getVeryCode">
            获取验证码
          </el-button>
        </template>
        </el-input>
      </el-form-item>

      <el-form-item label="成绩信息">
        <el-input
          v-model="userInfoStr"
        >
        <template slot="append">
          <el-button type="primary" @click="showInfoDialog">点击修改</el-button>
        </template>
        </el-input>
      </el-form-item>

      <el-form-item>
        <router-link to="/login">
          &nbsp;已有帐号，前往登录
        </router-link>
      </el-form-item>

      <el-button :loading="loading" type="warning" style="width:100%;margin-bottom:30px;" @click.native.prevent="handleRegister"> 注册 </el-button>
    </el-form>
      </el-card>
  </el-col>
  </el-row>

  </div>
</template>

<script src="./register.js"></script>