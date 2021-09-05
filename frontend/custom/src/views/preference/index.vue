<template>
  <div class="body">
    <el-row>
      <h1> {{ currentFormName }} </h1>
      <h2> {{ userInfoStr }} </h2>
      <el-button
        @click="mode = true; dialogVisible = true;"
      >
        <i class="el-icon-circle-plus-outline"/>
        新建志愿表
      </el-button>
      <el-button type="primary" v-if="isExist"> <i class="el-icon-document"/> 复制志愿表</el-button>
      <el-button type="success" v-if="isExist"> <i class="el-icon-upload2"/> 导出志愿表</el-button>
      <el-button
        @click="mode = false; dialogVisible = true;"
        type="info"
        v-if="isExist"
      >
        <i class="el-icon-edit"/>
        修改表名称
      </el-button>
    </el-row>
    <br/>
    <el-row v-if="isExist">
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="num" label="志愿序号" width="180" align="center" ></el-table-column>
        <el-table-column prop="school" label="所选院校" width="180" align="center"></el-table-column>
        <el-table-column prop="profession" label="所选专业" align="center"></el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <div v-if="tableData[scope.$index].isExist">
              <el-button @click="upVol(scope.$index)"> 上移 </el-button>
              <el-button @click="downVol(scope.$index)"> 下移 </el-button>
              <!-- <el-button @click="swapVol(scope.$index)"> 交换 </el-button> -->
              <el-popover
                placement="bottom"
                width="200"
                v-model="tableData[scope.$index].swapVisible"
                @after-leave="toSwapIndex = undefined;"  
              >
                <p>交换至第几志愿(1~96):</p>
                <el-input type="number" :min="0" :max="96" v-model="toSwapIndex" placeholder=""/>
                <div style="text-align: right; margin: 0">
                  <el-button size="mini" type="text" @click="tableData[scope.$index].swapVisible = false;">取消</el-button>
                  <el-button type="primary" size="mini" @click="swapVol(scope.$index)">确定</el-button>
                </div>
                <el-button slot="reference">交换</el-button>
              </el-popover>

              <el-button @click="deleteVol(scope.$index)"> 删除 </el-button>
            </div>
            <div v-else>
              <center> <el-button round @click="toScreen"> 添加 </el-button> </center>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </el-row>
    <el-dialog
      :title="mode ? '新建志愿表' : '修改当前志愿表名称'"
      :visible.sync="dialogVisible"
      width="50%"
    >
      <el-form ref="createVolForm" :model="createData" :rules="createRules">
        <el-form-item label="志愿表名称" prop="name">
          <el-input v-model="createData.name"></el-input>
        </el-form-item>
        <el-form-item v-if="mode" label="成绩信息">
          <el-input v-model="userInfoStr"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button v-if="mode" type="primary" @click="createForm">立即创建</el-button>
          <el-button v-else type="primary" @click="updateName">确认修改</el-button>
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
