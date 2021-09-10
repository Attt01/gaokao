import {getBatch, getMajorType, getRegion, getSchoolType} from "../../api/screen";

export default {
  data() {
    const validatePlan = (rule, value, callback) => {
      let sum = 0;
      this.plan.forEach(num => {
        sum += parseInt(num);
      });
      console.log(sum);
      if (sum != 96) {
        callback(new Error('三个志愿总和应该是96'));
      } else {
        callback();
      }
    };
    return {
      userInfo: {
        score: 0,
        subject: [],
        provinceRank: 0
      },
      form: {
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
      plan: [],
      isAutoRecommand: true,
      validateRules: {
        plan: [{ validator: validatePlan, trigger: 'blur' }]
      }
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
      this.$refs['form'].validate((isValid) => {
        if (isValid) {
          alert('submit!');
        } else {
          return false;
        }
      })
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
        let subs = this.userInfo.subject
        return '高考分数: ' + this.userInfo.score
          + ' / 排名: ' + this.userInfo.provinceRank
          + ' / 选科: ' + subjects[subs[0]] + ' ' + subjects[subs[1]] + ' ' + subjects[subs[2]]
      }
    }
  },
  mounted() {
    this.initData();
  },
}
