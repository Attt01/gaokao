<template>
  <div class="user-content">
    <el-button type="primary">使用选中志愿表</el-button>
    <el-button type="primary" v-if="!editName" @click="editVolunteerName">修改志愿表名称</el-button>
    <el-button type="primary" v-else  @click="editVolunteerName">提交志愿表名称</el-button>
    <el-button type="primary">删除志愿表</el-button>
    <el-table
      :data="tableData"
      border
      @selection-change="handleSelectionChange"
      style="width: 100%; margin-top: 20px;">
      <el-table-column
        label="选择"
        type="selection"
        width="80">
      </el-table-column>
      <el-table-column
        prop="name"
        label="志愿表名称"
      >
        <template slot-scope="scope">
          <el-input v-model="scope.row.name" v-if="editName"></el-input>
          <span v-else> {{scope.row.name}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="type"
        label="创建方式"
        width="200">
      </el-table-column>
      <el-table-column
        prop="createtime"
        label="创建时间"
        width="250">
      </el-table-column>
      <el-table-column
        prop="iscurrent"
        label="是否为当前"
        width="150">
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="200">
        <template slot-scope="scope">
          <el-button @click="setCurrentPreference(scope.row)" type="text" size="small">设为活动志愿表</el-button>
          <el-button @click="deletePreference(scope.row)" type="text" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script src="./myPreferenceList.js"></script>

<style rel="stylesheet/scss" lang="scss" scoped>
@import "src/styles/common.scss";

</style>
