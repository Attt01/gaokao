import {getBatch, getMajorType, getRegion, getSchoolType} from "../../api/screen";

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
      form: {
        userInfoStr: undefined,
        major: [],
        school: [],
        batch: undefined,
        area: undefined,
      },
      //多选参数
      multiProps: {multiple: true},
      levelOptions: [],
      majorOptions: [],
      locationOptions: [],
      classifyOptions: [],
      plan: {
        num: []
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
      this.selectBatch();
      this.selectRegion();
      this.selectSchoolType();
      this.selectMajorType();
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
    selectBatch() {
      getBatch().then(response => {
        if (response) {
          this.levelOptions = response.data;
        }
      })
    },
    selectRegion() {
      getRegion().then(response => {
        if (response) {
          this.locationOptions = response.data;
        }
      })
    },
    selectSchoolType() {
      getSchoolType().then(response => {
        if (response) {
          this.classifyOptions = response.data;
        }
      })
    },
    selectMajorType() {
      getMajorType().then(response => {
        if (response) {
          this.majorOptions = response.data;
        }
      })
    },
    changeClassify(value) {
      console.log(value)
    },
    changeLocation(value) {
      console.log(value)
    },
  },
  computed: {
    userInfoStr() {
      const subjects = ['', '物理', '化学', '生物', '历史', '地理', '政治']
      if (this.userInfo.score) {
        let subs = this.userInfo.subject;
        return '高考分数: ' + this.userInfo.score
          + ' / 排名: ' + this.userInfo.province_rank
          + ' / 选科: ' + subjects[subs[0]] + ' ' + subjects[subs[1]] + ' ' + subjects[subs[2]];
      }
    }
  },
  mounted() {
    this.initData();
  },
}
