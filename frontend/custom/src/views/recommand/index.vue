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

                <el-form :model="form" :rules="validateRules" ref="form" label-width="80px">

                  <el-form-item label="成绩信息">
                    <el-input
                      v-model="userInfoStr"
                    >
                    </el-input>
                  </el-form-item>

                  <!-- <el-form-item label="专业意向" prop="major">
                    <el-cascader
                      v-model="form.major"
                      :options="majorOptions"
                      :props="multiProps"
                      collapse-tags
                      clearable
                      filterable
                      @change="changeClassify"></el-cascader>
                  </el-form-item> -->

                  <el-form-item label="大学类型" prop="school">
                    <el-cascader
                      v-model="form.school"
                      :options="classifyOptions"
                      :props="multiProps"
                      clearable
                      filterable
                      collapse-tags
                    ></el-cascader>
                  </el-form-item>

                  <el-form-item label="意向地区" prop="region">
                    <el-cascader
                      v-model="form.region"
                      clearable
                      filterable
                      :options="locationOptions"
                      :props="multiProps"
                      collapse-tags
                    >
                    </el-cascader>
                  </el-form-item>

                  <el-form-item label="填报方案" prop="plan">
                    <el-form-item label="冲击">
                      <el-input v-model="plan[0]" type="number" placeholder="请输入冲击数"></el-input>
                    </el-form-item>
                    <el-form-item label="稳妥">
                      <el-input v-model="plan[1]" type="number" placeholder="请输入稳妥数"></el-input>
                    </el-form-item>
                    <el-form-item label="保底">
                      <el-input v-model="plan[2]" type="number" placeholder="请输入保底数"></el-input>
                    </el-form-item>
                  </el-form-item>

                  <el-form-item label="填报批次" prop="batch">
                    <!--展示更多信息可能需要更换组件-->
                    <el-select v-model="form.batch" placeholder="请选择">
                      <el-option
                        v-for="item in levelOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                      />
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
