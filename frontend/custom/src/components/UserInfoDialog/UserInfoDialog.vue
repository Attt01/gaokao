<template>
  <div>
    <el-dialog
      title="输入高考信息"
      :visible.sync="dialogVisible"
      width="30%"
      :show-close="false"
      :lock-scroll="false"
      :modal-append-to-body='false'
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
          <el-form-item label="高考排名" prop="provinceRank">
            <el-input
              v-model.number="userInfo.provinceRank"
              type="text"
              maxlength="7"
              placeholder="请输入高考排名"
            >
              <i slot="suffix">
                <el-button type="primary" @click="getPredictedRank">
                  获取预测排名
                </el-button>
              </i>
            </el-input>
          </el-form-item>
          <el-form-item label="选考科目" prop="subject">
            <el-checkbox-group
              v-model="userInfo.subject"
              :max="3"
            >
              <el-checkbox label="1">物理</el-checkbox>
              <el-checkbox label="2">化学</el-checkbox>
              <el-checkbox label="3">生物</el-checkbox>
              <el-checkbox label="4">历史</el-checkbox>
              <el-checkbox label="5">地理</el-checkbox>
              <el-checkbox label="6">政治</el-checkbox>
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
import {getRank} from '@/api/recommand.js';
import {STATUS_CODE} from "@/api/statusCode";
import store from '@/store';

export default {
  data() {
    return {
      userInfo: {
        score: null,
        subject: [],
        provinceRank: null
      },
      rules: {
        score: [
          {required: true, message: '请输入高考分数', trigger: 'blur'},
          {type: 'number', min: 100, max: 750, message: '必须是100~750的整数', trigger: 'blur'}
        ],
        provinceRank: [
          {type: 'number', message: '必须是整数', trigger: 'blur'}
        ],
        subject: [
          {required: true, message: '请选择科目'},
          {type: 'array', len: 3, message: '选择三项', trigger: 'blur'}
        ]
      }
    };
  },
  methods: {
    initData() {
      if (this.$route.path === '/register') {
        this.userInfo = JSON.parse(localStorage.getItem('userGaoKaoInfo'));
      }
    },
    submitForm(userInfo) {
      this.$refs.userInfo.validate((isValid) => {
        if (isValid) {
          //console.log('submit!');
          //console.log(userInfo);
          localStorage.setItem('userGaoKaoInfo', JSON.stringify(userInfo));
          console.log(localStorage.getItem('userGaoKaoInfo'));
          store.commit('SHOW_DIALOG', false);
          this.$router.replace({
            path: '/refresh',
            query: {
              path: this.$route.path
            }
          });
        } else {
          //console.log('error submit!!');
          return false;
        }
      });
    },
    getPredictedRank() {
      //这里按理说不选科目也能获取排名，似乎要把表单拆成两个然后只验证前一个?
      this.$refs.userInfo.validate((isValid) => {
        if (isValid) {
          getRank(this.userInfo.score).then((res) => {
            if (res.code === STATUS_CODE.SUCCESS) {
              this.userInfo.provinceRank = res.data;
            }
          });
        }
      })
    }
  },
  computed: {
    dialogVisible() {
      return store.getters.dialogVisible;
    }
  },
  //mounted的话可能会出现一点闪动
  beforeMount() {
    if (localStorage.getItem('userGaoKaoInfo')) {
      store.commit('SHOW_DIALOG', false);
    }
    this.initData();
  }
};
</script>

<style lang="sass" scoped>

</style>
