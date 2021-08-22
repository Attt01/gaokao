import {provinceAndCityDataPlus} from "element-china-area-data";

export default {
  data() {
    // const validatePlan = (rule, value, callback) => {
    //   alert("qwq");
    //   if (this.plan.num[0] + this.plan.num[1] + this.plan.num[2] != 96) {
    //     callback(new Error('三个志愿总和应该是96'));
    //   } else {
    //     callback();
    //   }
    // }
    return {
      userInfo: {
        score: 0,
        subject: [],
        province_rank: 0
      },
      major: {
        props: { multiple: true },
        options: [{
          value: 1,
          label: '计算机类',
          children: [{
            value: 2,
            label: '计算机科学与技术'
          }, {
            value: 3,
            label: '软件工程'
          }]
        }],
        selected: []
      },
      school: {
        props: { multiple: true },
        options: [{
          value: 'daxuetese',
          label: '大学特色',
          children: [
            {
              value: 'jiubawu',
              label: '985',
            },
            {
              value: 'eryaoyao',
              label: '211',
            },
            {
              value: 'yiben',
              label: '一本',
            }
          ]
        },
        {
          value: 'banxuexingzhi',
          label: '办学性质',
          children: [
            {
              value: '1',
              label: '普通批',
            },
            {
              value: '2',
              label: '中外合办',
            },
            {
              value: '3',
              label: '校企合办',
            }
          ]
        },
        {
          value: 'daxueleixing',
          label: '大学类型',
          children: [
            {
              value: '1',
              label: '综合',
            },
            {
              value: '2',
              label: '理工',
            },
            {
              value: '3',
              label: '师范',
            }
          ]
        }],
        selected: []
      },
      area: {
        props: { multiple: true },
        options: provinceAndCityDataPlus,
        selected: []
      },
      plan: {
        num: []
      },
      batch: {
        options: [{
          value: '选项1',
          label: '普通一段'
        }, {
          value: '选项2',
          label: '普通二段'
        }],
        selected: ''
      },
      isAutoRecommand: true,
      // valiRules: {
      //   plan: [{ validator: validatePlan, trigger: 'blur' }]
      // }
    }
  },
  methods: {
    initData() {
      //if (getToken())
      this.userInfo = JSON.parse(localStorage.getItem('userGaoKaoInfo'));
      //console.log(this.userInfo);
    },
    onSubmit() {
      // this.$refs['favorite'].validate((valid) => {
      //   if (valid) {
      //     alert('submit!');
      //   } else {
      //     return false;
      //   }
      // })
    },
  },
  computed: {
    userInfoStr() {
      if (this.userInfo.score) {
        let subs = this.userInfo.subject;
        return '高考分数: ' + this.userInfo.score
          + ' / 排名: ' + this.userInfo.province_rank
          + ' / 选科: ' + subs[0][0] + ' ' + subs[1][0] + ' ' + subs[2][0];
      }
    }
  },
  mounted() {
    this.initData();
  },
}
