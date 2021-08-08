export default {
  data() {
    return {
      tableData: [{
        id: 1,
        name: '志愿表1',
        type: '智能推荐',
        createtime: '2021-08-05 17:14',
        iscurrent: '是'
      }],
      activeIndex: 0
    }
  },
  methods: {
    initData() {
      
    },
    //这几个方法绝对写麻烦了QAQ
    setCurrentPreference(preferData) {
      let index = this.tableData.indexOf(preferData);
      this.tableData[this.activeIndex].iscurrent = '否';
      this.activeIndex = index;
      this.tableData[index].iscurrent = '是';
    },
    deletePreference(preferData) {
      let index = this.tableData.indexOf(preferData);
      if (preferData.iscurrent == '是') {
        this.activeIndex = 0;
      }
      //200if
      this.tableData.splice(index, 1);
      if (this.activeIndex == 0 && this.tableData.length) {
        this.tableData[0].iscurrent = '是';
      }
    }
  },
  mounted() {
    this.initData()
  },
}