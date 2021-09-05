<template>
  <div class="body">
    <el-row>
      <h1> {{ currentFormName }} </h1>
      <h2> {{ userInfoStr }} </h2>
      <el-button @click="dialogVisible = true;"> <i class="el-icon-circle-plus-outline"/> 新建志愿表</el-button>
      <el-button type="primary" v-if="isExist"> <i class="el-icon-document"/> 复制志愿表</el-button>
      <el-button type="success" v-if="isExist"> <i class="el-icon-upload2"/> 导出志愿表</el-button>
      <el-button type="info" v-if="isExist"> <i class="el-icon-edit"/> 修改表名称</el-button>
    </el-row>
    <br/>
    <el-row v-if="isExist">
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="num" label="志愿序号" width="180" align="center" ></el-table-column>
        <el-table-column prop="school" label="所选院校" width="180" align="center"></el-table-column>
        <el-table-column prop="profession" label="所选专业" align="center"></el-table-column>
        <el-table-column>
          <template slot-scope="scope">
            <div v-if="tableData[scope.$index].isExist">
              <el-button> 编辑 </el-button>
            </div>
            <div v-else>
              <center> <el-button round @click="toScreen"> 添加 </el-button> </center>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </el-row>
    <el-dialog
      title="新建志愿表"
      :visible.sync="dialogVisible"
      width="50%"
    >
      <el-form ref="createVolForm" :model="createData" :rules="createRules">
        <el-form-item label="志愿表名称" prop="name">
          <el-input v-model="createData.name"></el-input>
        </el-form-item>
        <el-form-item label="成绩信息">
          <el-input v-model="userInfoStr"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="createForm">立即创建</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script src="./preference.js">

</script>

<style lang="scss" scoped>

.body {
  width: 100%;
  height: 100%;
  font-family: "Lucida Console", "Courier New", monospace;
  font-size:12px;
  font-weight:bold;
}
</style>
