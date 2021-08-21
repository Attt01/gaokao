<template>
  <div class="login-container">
    <el-row type="flex" justify="space-around">
    <el-col :span="12">
      <el-card>
    <el-form ref="regForm" :model="regForm" :rules="loginRules" auto-complete="on" label-position="left" label-width="80px">

      <div class="title-container">
        <h3 class="title"> 志愿推荐系统注册 </h3>
        <!-- <lang-select class="set-language"/> -->
      </div>

      <el-form-item prop="pnicknamehone" label="昵称">
        <el-input
          v-model="regForm.nickname"
          name="nickname"
          type="text"
          auto-complete="on"
        />
      </el-form-item>

      <el-form-item prop="phone" label="手机号">
        <el-input
          v-model="regForm.phone"
          name="phone"
          type="text"
          auto-complete="on"
        />
      </el-form-item>

      <el-form-item prop="password" label="密码">
        <el-input
          :type="pwdType"
          v-model="regForm.password"
          name="password"
          auto-complete="on"
        />
        <!-- <span class="show-pwd" @click="showPwd">
          <svg-icon icon-class="eye" />
        </span> -->
      </el-form-item>

      <el-form-item prop="password" label="确认密码">
        <el-input
          :type="pwdType"
          v-model="regForm.confirmPassword"
          name="password"
          auto-complete="on"
        />
        <!-- <span class="show-pwd" @click="showPwd">
          <svg-icon icon-class="eye" />
        </span> -->
      </el-form-item>

      <el-form-item prop="veryCode" label="验证码">
        <el-input
          v-model="regForm.veryCode"
          name="veryCode"
        />
      </el-form-item>

      <el-form-item prop="veryCode" label="成绩信息">
        <el-input
          v-model="userInfoStr"
          disabled
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
<!--
<style rel="stylesheet/scss" lang="scss" src="./register.scss"></style>
-->
