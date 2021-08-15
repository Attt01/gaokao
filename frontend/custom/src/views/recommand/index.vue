<template>
<div>
  <el-row type="flex" justify="space-around">
    <el-col :span="0.5"></el-col>
    <el-col :span="12">
      <el-card
        :body-style="{ padding: '0px', backgroundColor: '#F5F7FA' }"
        shadow="hover"
      >
        <el-row type="flex" justify="space-around" align="center">
          <el-col :span="21">
            <div class="form-container">

              <el-form ref="favorite" label-width="80px">

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
                  <!-- <el-button type="primary" @click="area.isShowDialog=true;area.tmpSelected=area.selected;">点击选择</el-button>
                  <el-dialog
                    title="选择意向地区"
                    :visible.sync="area.isShowDialog"
                  >
                    <el-form :rules="areaRules" :model="area" ref="areaSelect">
                      <el-form-item prop="tmpSelected">
                        <el-checkbox-group v-model="area.tmpSelected">
                          <el-checkbox v-for="availableArea in area.areas" :key="availableArea" :label="availableArea"></el-checkbox>
                        </el-checkbox-group>
                      </el-form-item>
                      <el-form-item>
                        <el-row type="flex" justify="end">
                          <el-col :span="3">
                            <el-button type="primary" @click="onConfirmArea">确 定</el-button>
                          </el-col>
                        </el-row>
                      </el-form-item>
                    </el-form>
                  </el-dialog> -->
                  <el-cascader
                    :options="area.options"
                    :props="area.props"
                    clearable
                    collapse-tags
                  >
                  </el-cascader>
                </el-form-item>

                <el-form-item label="填报方案">
                  <!--按钮待替换-->
                  <el-button type="primary" @click="plan.isShowDialog=true; plan.tmpnum=plan.num.slice(0);">点击选择</el-button>
                  <el-dialog
                    title="选择填报方案"
                    :visible.sync="plan.isShowDialog"
                    width="50%"
                  >
                    <el-form>
                      <el-form-item label="冲击">
                        <el-input v-model="plan.tmpnum[0]" type="number" placeholder="请输入冲击数"></el-input>
                      </el-form-item>
                      <el-form-item label="稳妥">
                        <el-input v-model="plan.tmpnum[1]" type="number" placeholder="请输入稳妥数"></el-input>
                      </el-form-item>
                      <el-form-item label="保底">
                        <el-input v-model="plan.tmpnum[2]" type="number" placeholder="请输入保底数"></el-input>
                      </el-form-item>
                    </el-form>
                    <span slot="footer" class="dialog-footer">
                      <el-button type="primary" @click="onConfirmPlan">确 定</el-button>
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

                <el-row type="flex" justify="space-around">
                  <el-col :span="8">
                    <el-button type="primary" @click="onSubmit">立即生成推荐表</el-button>
                  </el-col>
                </el-row>
                
              </el-form>
            </div>
          </el-col>
        </el-row>
      </el-card>
    </el-col>
    <el-col :span="10">
      <div class="right-container">
        <!--轮播图/宣传图留空-->
        <el-image
          src="https://imagezy.eol.cn/_nuxt/img/banner-s1.1b8e78b.png"
        >
        </el-image>
      </div>
    </el-col>
    <el-col :span="0.5"></el-col>
  </el-row>
</div>
</template>

<script src="./recommand.js"></script>

<style lang="scss" scoped>
.form-container {
  padding: 5% 0;
  width: 100%;
  /*background-color: aqua;*/
}
</style>
