export default {
  data() {
    return {
      userInfo: {},
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
        }]
      },
      area: {
        isShowDialog: false,
        areas: ['qwq', 'owo', 'aaa'],
        selected: [],
        tmpSelected: []
      },
      areaRules: {
        tmpSelected: [
          { required: true, message: '请至少选择一项', trigger: 'blur' }
        ]
      },
      plan: {
        isShowDialog: false,
        num: [],
        tmpnum: []
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
      isAutoRecommand: true
    }
  },
  methods: {
    initData() {
      //if (getToken())
      this.userInfo = JSON.parse(localStorage.getItem('userGaoKaoInfo'));
      //console.log(this.userInfo);
    },
    onSubmit() {
      alert('submit!');
    },
    onConfirmArea() {
      // area.isShowDialog=false
      // alert("qwq");
      this.$refs.areaSelect.validate((isValid) => {
        //alert("qqq");
        if (isValid) {
          //alert("Qwq");
          this.area.selected = this.area.tmpSelected;
          this.area.isShowDialog = false;
        } else {
          return false;
        }
      });
    },
    onConfirmPlan() {
      let tmpNum = this.plan.tmpnum.slice(0), sum = 0;
      //console.log(tmpNum);
      for (let index = 0; index < 3; index++) {
        if (tmpNum[index] == undefined || tmpNum[index] == "") {
          tmpNum[index] = 0;
        }
        sum += parseInt(tmpNum[index]);
      }
      if (sum != 96)
      {
        this.$message('三种志愿总和应该是96');
      } else {
        this.plan.num = tmpNum.slice(0);
        this.plan.isShowDialog = false;
      }
    }
  },
  computed: {
    userInfoStr() {
      if (this.userInfo.score) {
        let subs = this.userInfo.subjects;
        return '高考分数: ' + this.userInfo.score
          + ' / 排名: ' + this.userInfo.rank
          + ' / 选科: ' + subs[0][0] + ' ' + subs[1][0] + ' ' + subs[2][0];
      }
    }
  },
  mounted() {
    this.initData();
  },
}
