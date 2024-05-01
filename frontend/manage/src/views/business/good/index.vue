<template>
  <div class="app-container">
    <el-input
      v-model="listQuery.searchText"
      placeholder="学校名称"
      clearable
      style="width: 200px; margin-bottom: 20px;"
    ></el-input>
    <el-button type="primary" @click="handleSearch">搜索</el-button>

    <el-button type="success" @click="handleAdd" style="float: right;">添加志愿</el-button>

    <el-table :data="volunteerList" v-loading="loading" border highlight-current-row stripe height="800"
              :header-cell-style="{'text-align':'center'}">
        <el-table-column label="招生院校" prop="name" align="left">
          <template slot-scope="scope">
            <div class="important-text">
              {{ scope.row.name }}
            </div>
            <div class="sign-text">
              <el-row>
                <el-col :span="8">
                  <div class="grid-content bg-purple"></div>
                  {{ scope.row.province }}
                </el-col>
                <el-col :span="8">
                  <div class="grid-content bg-purple-light">{{ scope.row.category }}</div>
                </el-col>
              </el-row>
            </div>
            <div class="sign-text-second">
              <el-row>
                <el-col :span="24">
                  <div class="grid-content bg-purple"></div>
                  院校代码{{ scope.row.universityCode }}
                </el-col>
              </el-row>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="招生专业" align="left" prop="professionalName" fit>
          <template slot-scope="scope">
            <div class="important-text">
              {{ scope.row.professionalName }}
            </div>
            <div class="sign-text">
              <el-row>
                <el-col :span="12">
                  <div class="grid-content bg-purple"></div>
                  学费{{ scope.row.fee }}
                </el-col>
                <el-col :span="12">
                  <div class="grid-content bg-purple-light">学制{{ scope.row.time }}</div>
                </el-col>
              </el-row>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="最低分" align="center" prop="lowestScore">
          <template slot-scope="scope">
            {{ scope.row.lowestScore }}
          </template>
        </el-table-column>
        <el-table-column label="最低位次" align="center" prop="lowestPosition">
          <template slot-scope="scope">
            {{ scope.row.lowestPosition }}
          </template>
        </el-table-column>
        <el-table-column label="录取人数" align="center" prop="enrollment">
          <template slot-scope="scope">
            {{ scope.row.enrollment }}
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button type="primary" icon="el-icon-edit" @click="handleEdit(scope.row)" style="font-size: 11px;"></el-button>
            <el-button type="danger" icon="el-icon-delete" @click="handleDelete(scope.row)" style="font-size:11px;"></el-button>
          </template>
        </el-table-column>
    </el-table>
    <br>


    <template>
      <el-dialog
        :visible.sync="dialogFormVisibleEdit"
        title="编辑信息"
        :close-on-click-modal="false"
        width="50%">
        <el-form ref="formData" :model="form" label-width="100px">
          <el-form-item label="院校名称">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="专业名称">
            <el-input v-model="form.professionalName"></el-input>
          </el-form-item>
          <el-form-item label="最低分">
            <el-input v-model.number="form.lowestScore"></el-input>
          </el-form-item>
          <el-form-item label="最低位次">
            <el-input v-model.number="form.lowestPosition"></el-input>
          </el-form-item>
          <el-form-item label="录取人数">
            <el-input v-model.number="form.enrollment"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisibleEdit = false">取消</el-button>
      <el-button type="primary" @click.native="submitEdit">保存</el-button>
    </span>
      </el-dialog>
    </template>

    <template>
      <el-dialog
        :visible.sync="dialogFormVisible"
        title="编辑信息"
        :close-on-click-modal="false"
        width="50%">
        <el-form ref="formData" :model="form" label-width="100px">
          <el-form-item label="院校名称">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="专业名称">
            <el-input v-model="form.professionalName"></el-input>
          </el-form-item>
          <el-form-item label="最低分">
            <el-input v-model.number="form.lowestScore"></el-input>
          </el-form-item>
          <el-form-item label="最低位次">
            <el-input v-model.number="form.lowestPosition"></el-input>
          </el-form-item>
          <el-form-item label="录取人数">
            <el-input v-model.number="form.enrollment"></el-input>
          </el-form-item>
          <el-form-item label="所属大学代码">
            <el-input v-model="form.universityCode"></el-input>
          </el-form-item>
          <el-form-item label="所属省份">
            <el-input v-model="form.province"></el-input>
          </el-form-item>
          <el-form-item label="所在城市">
            <el-input v-model="form.city"></el-input>
          </el-form-item>
          <el-form-item label="地址">
            <el-input v-model="form.address"></el-input>
          </el-form-item>
          <el-form-item label="本科学校">
            <el-checkbox v-model="form.undergraduateSchoolIsOrNot">是</el-checkbox>
          </el-form-item>
          <el-form-item label="是否为985">
            <el-checkbox v-model="form.is985">是</el-checkbox>
          </el-form-item>
          <el-form-item label="是否是211">
            <el-checkbox v-model="form.is211">是</el-checkbox>
          </el-form-item>
          <el-form-item label="是否是公立院校">
            <el-checkbox v-model="form.PublicIsOrNot">是</el-checkbox>
          </el-form-item>
          <el-form-item label="是否是私立院校">
            <el-checkbox v-model="form.PrivateIsOrNot">是</el-checkbox>
          </el-form-item>
          <el-form-item label="分类">
            <el-input v-model="form.category"></el-input>
          </el-form-item>
          <el-form-item label="图片链接">
            <el-input v-model="form.picLink"></el-input>
          </el-form-item>
          <el-form-item label="就业率">
            <el-input v-model.number="form.employmentRate"></el-input>
          </el-form-item>
          <el-form-item label="出国率">
            <el-input v-model.number="form.abroadRate"></el-input>
          </el-form-item>
          <el-form-item label="深造率">
            <el-input v-model.number="form.furtherRate"></el-input>
          </el-form-item>
          <el-form-item label="志愿者部门">
            <el-input v-model.number="form.volunteerSection"></el-input>
          </el-form-item>
          <el-form-item label="学科限制类型">
            <el-input v-model.number="form.subjectRestrictionType"></el-input>
          </el-form-item>
          <el-form-item label="学科限制详情">
            <el-input v-model="form.subjectRestrictionDetail"></el-input>
          </el-form-item>
          <el-form-item label="考试分数">
            <el-input v-model.number="form.score"></el-input>
          </el-form-item>
          <el-form-item label="考试位次">
            <el-input v-model.number="form.position"></el-input>
          </el-form-item>
          <el-form-item label="时间">
            <el-input v-model.number="form.time"></el-input>
          </el-form-item>
          <el-form-item label="费用">
            <el-input v-model.number="form.fee"></el-input>
          </el-form-item>
          <el-form-item label="双一流学科数量">
            <el-input v-model.number="form.doubleFirstClassSubjectNumber"></el-input>
          </el-form-item>
          <el-form-item label="国家特色学科数量">
            <el-input v-model.number="form.countrySpecificSubjectNumber"></el-input>
          </el-form-item>
          <el-form-item label="硕士点数">
            <el-input v-model.number="form.masterPoint"></el-input>
          </el-form-item>
          <el-form-item label="博士点数">
            <el-input v-model.number="form.doctorPoint"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取消</el-button>
      <el-button type="primary" @click.native="submitAdd">保存</el-button>
    </span>
      </el-dialog>
    </template>


    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-pagination layout="prev, pager, next" @current-change="fetchPage" :page-size="listQuery.limit"
                     :page-count="listQuery.total" style="text-align:center;margin:10px">
      </el-pagination>
    </el-col>
  </div>
</template>

<script src="./good.js"></script>

<style>

.niceButton {
  position: relative;
  left: 20px;
  border-radius: 4px;
  bottom: 0;
}

.niceButton:hover {
  background-color: #1795bb;
}

.niceButton:active {
  background-color: #1795bb;
  box-shadow: 0 5px #666;
  transform: translateY(4px);
}

.important-text {
  font-size: 14px;
  font-weight: bold;
}

.sign-text {
  color: #8c939d;
  font-size: smaller;
  font-family: 幼圆;
  font-weight: bold;
}

.sign-text-second {
  color: #8c939d;
  font-size: smaller;
  font-family: 幼圆;
  font-weight: bold;
}


</style>

