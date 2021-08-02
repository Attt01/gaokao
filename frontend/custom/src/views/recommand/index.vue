<template>
<div>
  <el-row type="flex" alien="middle" justify="space-around">
    <el-col :span="0.5"></el-col>
    <el-col :span="12">
      <div class="tmp">
        <el-row type="flex" justify="space-around" align="center">
          <el-col :span="21">
            <div class="tmp2">

              <el-form ref="form" label-width="80px">

                <el-form-item label="成绩信息">
                  <el-input
                    v-model="userInfoStr"
                    disabled
                  >
                  </el-input>
                </el-form-item>

                <el-form-item label="专业意向">
                    <el-cascader
                      :options="major.options"
                      :props="major.props"
                      collapse-tags
                      clearable
                    >
                    </el-cascader>
                </el-form-item>

                <el-form-item label="意向地区">
                  <!--按钮待替换-->
                  <el-button type="primary" @click="area.isShowDialog=true">点击选择</el-button>
                  <el-dialog
                    title="选择意向地区"
                    :visible.sync="area.isShowDialog"
                  >
                    <el-checkbox-group v-model="area.selected">
                      <el-checkbox v-for="availableArea in area.areas" :key="availableArea" :label="availableArea"></el-checkbox>
                    </el-checkbox-group>
                    <span slot="footer" class="dialog-footer">
                      <el-button type="primary" @click="area.isShowDialog=false">确 定</el-button>
                    </span>
                  </el-dialog>
                </el-form-item>

                <el-form-item label="填报方案">
                  <!--按钮待替换-->
                  <el-button type="primary" @click="plan.isShowDialog=true">点击选择</el-button>
                  <el-dialog
                    title="选择填报方案"
                    :visible.sync="plan.isShowDialog"
                    width="50%"
                  >
                    <el-form>
                      <el-form-item label="冲击">
                        <el-input v-model="plan.num[0]" placeholder="请输入内容"></el-input>
                      </el-form-item>
                      <el-form-item label="稳妥">
                        <el-input v-model="plan.num[1]" placeholder="请输入内容"></el-input>
                      </el-form-item>
                      <el-form-item label="保底">
                        <el-input v-model="plan.num[2]" placeholder="请输入内容"></el-input>
                      </el-form-item>
                    </el-form>
                    <span slot="footer" class="dialog-footer">
                      <el-button type="primary" @click="plan.isShowDialog=false">确 定</el-button>
                    </span>
                  </el-dialog>
                </el-form-item>
                
                <el-form-item label="填报批次">
                  <!--展示更多信息可能需要更换组件-->
                  <el-select v-model="batch.selected" placeholder="请选择">
                    <el-option
                      v-for="batchItem in batch.options"
                      :key="batchItem.value"
                      :label="batchItem.label"
                      :value="batchItem.value">
                    </el-option>
                  </el-select>
                </el-form-item>

                <el-form-item label="系统推荐">
                  是否允许系统推荐志愿：
                  <el-switch v-model="isAutoRecommand"></el-switch>
                  <span v-if="isAutoRecommand">&nbsp;&nbsp;允许</span>
                  <span v-if="!isAutoRecommand">&nbsp;不允许</span>
                </el-form-item>

                <el-form-item>
                  <el-button type="primary" @click="onSubmit">立即生成推荐表</el-button>
                  <el-button>取消</el-button>
                </el-form-item>
              </el-form>
            </div>
          </el-col>
        </el-row>
      </div>
    </el-col>
    <el-col :span="10">
      <div class="tmp">
        <!--轮播图/宣传图留空-->
        <el-image
          src="https://z3.ax1x.com/2021/03/30/cigICq.jpg"
          :fit="cover"
        >
        </el-image>
      </div>
    </el-col>
    <el-col :span="0.5"></el-col>
  </el-row>
</div>
</template>

<script src="./recommand.js"></script>

<style scoped>
/* .recommand-main-container {
  display: flex;
  justify-content: center;
} */
.tmp {
  background-color:peru;
  width: 100%;
}
.tmp2 {
  border:5px solid red;
  width: 100%;
}
</style>
