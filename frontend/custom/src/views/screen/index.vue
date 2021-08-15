<template>
  <div class="app-container">
    <!--搜索栏部分-->
    <el-form :model="listQuery" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="填报批次" prop="level">
        <el-select
          v-model="listQuery.level"
          placeholder="请选择批次"
          style="width: 240px">
          <el-option
            v-for="item in levelOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="就读地区" prop="location">
        <el-cascader
          v-model="listQuery.location"
          placeholder="不限"
          style="width: 240px"
          clearable
          filterable
          :options="locationOptions"
          @change="changeLocation">
        </el-cascader>
      </el-form-item>
      <el-form-item label="大学类型" prop="classification">
        <el-cascader
          v-model="listQuery.classification"
          style="width: 240px"
          :options="classifyOptions"
          clearable
          filterable
          :props="{ expandTrigger: 'hover' }"
          @change="changeClassify"></el-cascader>
      </el-form-item>
      <el-form-item label="搜索大学" prop="universityName">
        <el-input
          v-model="listQuery.universityName"
          placeholder="请输入大学名称"
          style="width: 240px"
          clearable></el-input>
      </el-form-item>
      <el-form-item label="搜索专业" prop="majoName">
        <el-input
          v-model="listQuery.majorName"
          placeholder="请输入专业名称"
          style="width: 240px"
          clearable></el-input>
      </el-form-item>
      <el-form-item>
        <div class="niceButton">
          <el-button type="primary" icon="el-icon-search" @click.native="handleQuery">搜索</el-button>
        </div>
      </el-form-item>
      <el-divider content-position="right"/>
    </el-form>

    <!--列表部分-->
    <el-table :data="volunteerList" v-loading="loading"  border highlight-current-row>
      <el-table-column label="录取概率" prop="percent" align="center">
        <template slot-scope="scope">
          {{ scope.row.percent }}
        </template>
      </el-table-column>
      <el-table-column label="招生院校" prop="universityName" align="center">
        <template slot-scope="scope">
          {{ scope.row.universityName }}
        </template>
      </el-table-column>
      <el-table-column label="2021招生计划" align="center">
        <el-table-column label="招生代码" align="center" prop="majorCode">
          <template slot-scope="scope">
            {{ scope.row.majorCode }}
          </template>
        </el-table-column>
        <el-table-column label="招生人数" align="center" prop="recruitNumber">
          <template slot-scope="scope">
            {{ scope.row.recruitNumber }}
          </template>
        </el-table-column>
        <el-table-column label="减扩人数" align="center" prop="changedNumber">
          <template slot-scope="scope">
            {{ scope.row.changedNumber }}
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column label="历史录取" align="center" class-name="small-padding fixed-width">
        //后续改为当前年份的前三年
        <el-table-column label="2020" align="center" prop="lastYear">
          <template slot-scope="scope">
            {{ scope.row.lastYear }}
          </template>
        </el-table-column>
        <el-table-column label="2019" align="center" prop="theYearBeforeLast">
          <template slot-scope="scope">
            {{ scope.row.theYearBeforeLast }}
          </template>
        </el-table-column>
        <el-table-column label="2018" align="center" prop="threeYearsAgo">
          <template slot-scope="scope">
            {{ scope.row.threeYearsAgo }}
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleFill(scope.row)"
          >填报
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <br>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-pagination layout="prev, pager, next" @current-change="fetchPage" :page-size="listQuery.limit"
                     :page-count="listQuery.total" style="text-align:center;margin:10px">
      </el-pagination>
    </el-col>

  </div>
</template>

<script src="./screen.js"></script>


<style>
.app-container {

}
.niceButton{
  position: relative;
  left: 20px;
  border-radius: 4px;
  bottom: 0;
}
.niceButton:hover{
  background-color: #1795bb;
}
.niceButton:active{
  background-color: #1795bb;
  box-shadow: 0 5px #666;
  transform:translateY(4px);
}
</style>
