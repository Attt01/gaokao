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
        selected: []
      },
      plan: {
        isShowDialog: false,
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
