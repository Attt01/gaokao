<template>
  <div class="user-content">
    <el-table
      :data="tableData"
      border
      style="width: 100%">
      <el-table-column
        label="选择"
        prop="checkbox"
        width="80"
        class="checkbox">
        <template slot-scope="scope">
        <el-checkbox :value="scope.row"></el-checkbox>
        </template>
      </el-table-column>
      <el-table-column
        prop="name"
        label="志愿表名称">
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
.checkbox{
  width: 20px;
  margin-left: auto;
  margin-right: auto;
}
</style>
