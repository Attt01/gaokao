<template>
  <div class="login-container"  style=" background-image: url(static/img/bg.jpg);">

    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">

      <div class="title-container">
        <h3 class="title"> 志愿推荐系统注册 </h3>
        <!-- <lang-select class="set-language"/> -->
      </div>

      <el-form-item prop="phone" label="手机号">
        <el-input
          v-model="loginForm.phone"
          name="phone"
          type="text"
          auto-complete="on"
        />
      </el-form-item>

      <el-form-item prop="password" label="密码">
        <el-input
          :type="pwdType"
          v-model="loginForm.password"
          :placeholder="$t('login.password')"
          name="password"
          auto-complete="on"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon icon-class="eye" />
        </span>
      </el-form-item>

      <!-- <el-form-item prop="password" label="确认密码">
        <el-input
          :type="pwdType"
          v-model="loginForm.confirmPassword"
          :placeholder="$t('login.password')"
          name="password"
          auto-complete="on"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon icon-class="eye" />
        </span>
      </el-form-item> -->

      <el-form-item prop="veryCode" label="验证码">
        <el-input
          v-model="loginForm.veryCode"
          name="veryCode"
        />
      </el-form-item>

      <el-button :loading="loading" type="warning" style="width:100%;margin-bottom:30px;" @click.native.prevent="handleRegister">{{ $t('login.logIn') }}</el-button>
    </el-form>


  </div>
</template>

<script src="./register.js"></script>
<style rel="stylesheet/scss" lang="scss" src="./register.scss"></style>
