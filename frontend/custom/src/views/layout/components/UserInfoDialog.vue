<template>
  <div>
    <el-dialog
      title="输入高考信息"
      :visible.sync="dialogVisible"
      width="30%"
      :show-close="false"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >
      <span>
        <el-form :rules="rules" :model="userInfo" ref="userInfo" label-width="80px">
          <el-form-item label="高考分数" prop="score">
            <el-input
              v-model.number="userInfo.score"
              type="text"
              maxlength="3"
              placeholder="请输入高考分数"
            >
            </el-input>
          </el-form-item>
          <el-form-item label="高考排名" prop="rank">
            <el-input
              v-model.number="userInfo.rank"
              type="text"
              maxlength="7"
              placeholder="请输入高考排名"
            >
            </el-input>
          </el-form-item>
          <el-form-item label="选考科目" prop="subjects">
            <el-checkbox-group
              v-model="userInfo.subjects"
              :max="3"
            >
              <el-checkbox label="物理" name="type"></el-checkbox>
              <el-checkbox label="化学" name="type"></el-checkbox>
              <el-checkbox label="生物" name="type"></el-checkbox>
              <el-checkbox label="政治" name="type"></el-checkbox>
              <el-checkbox label="历史" name="type"></el-checkbox>
              <el-checkbox label="地理" name="type"></el-checkbox>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item>
            <div style="float: right;">
              <el-button type="primary" @click="submitForm(userInfo)">确 定</el-button>
            </div>
          </el-form-item>
        </el-form>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      dialogVisible: true,
      userInfo: {
        score: null,
        subjects: [],
        rank: null
      },
      rules: {
        score: [
          { required: true, message: '请输入高考分数', trigger: 'blur' },
          { type: 'number', min: 100, max: 750, message: '必须是100~750的整数', trigger: 'blur'}
        ],
        rank: [
          { type: 'number', message: '必须是整数', trigger: 'blur'}
        ],
        subjects: [
          { required: true, message: '请选择科目' },
          { type: 'array', len: 3, message: '选择三项', trigger: 'blur'}
        ]
      }
    };
  },
  methods: {
    submitForm(userInfo) {
      this.$refs.userInfo.validate((isValid) => {
        if (isValid) {
          //console.log('submit!');
          //console.log(userInfo);
          localStorage.setItem('userGaoKaoInfo', JSON.stringify(userInfo));
          //console.log(localStorage.getItem('userGaoKaoInfo'));
          this.dialogVisible = false;
        } else {
          //console.log('error submit!!');
          return false;
        }
      });
    },
  },
  mounted() {
    if (localStorage.getItem('userGaoKaoInfo')) {
      this.dialogVisible = false;
    }
  }
};
</script>

<style lang="sass" scoped>

</style>
