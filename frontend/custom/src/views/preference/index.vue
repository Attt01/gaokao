<template>
  <div class="body">
    <el-container>
  <el-header>
   <h1>760分 新建志愿表单</h1>
   <h2>山东/选课/分数/名次</h2>
  <el-button ><i class="el-icon-circle-plus-outline"></i> 新建志愿表</el-button>
  <el-button type="primary" ><i class="el-icon-document"></i> 复制志愿表</el-button>
  <el-button type="success" ><i class="el-icon-upload2"></i> 导出志愿表</el-button>
  <el-button type="info" ><i class="el-icon-edit"></i> 修改表名称</el-button>
  </el-header>
  <el-main>
    <br> <br> <br> <br> <br> <br> <br>
    <el-table :data="tableData" height="100%" border style="width: 100%">
      <el-table-column prop="num" label="志愿序号" width="180" align="center" >
      </el-table-column>
      <el-table-column prop="school" label="所选院校" width="180" align="center">
      </el-table-column>
      <el-table-column prop="profession" label="所选专业" align="center"> </el-table-column>
      <el-table-column prop="add" label="操作" align="center"> </el-table-column>
    </el-table>
    </el-main>
    </el-container>
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
