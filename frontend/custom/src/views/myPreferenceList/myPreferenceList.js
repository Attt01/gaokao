import { getVolunteer, deleteVolunteer,changeCurrentForm } from '../../api/volunteer'
import { getInfo } from '../../api/login'
export default {
  data() {
    return {
      tableData: [{
        id: 0,
        name: '',
        type: '',
        createtime: '',
        iscurrent: ''
      }],
      userId: 0,
      checkList: [],
      activeIndex: 0,
    }
  },
  methods: {
    initData() {
      getInfo().then(res => {
        this.userId = res.data.id
        const data = []
        getVolunteer(this.userId).then(volunteer => {
          volunteer.data.forEach((item, index) => {
            data[index] = {
              id: item.id,
              name: item.name,
              type: item.generatedType ? '自动生成' : '手动生成',
              createtime: new Date(item.generatedTime).toLocaleDateString(),
              iscurrent: item.current ? '是' : '否'
            }
            if (data[index].iscurrent === '是') {
              this.activeIndex = data[index].id
            }
          })
          this.tableData = data
        })
      })
    },
    // 这几个方法绝对写麻烦了QAQ
    setCurrentPreference(preferData) {
      const params = {
        newFormId: preferData.id,
        preFormId: this.activeIndex,
        userId: this.userId
      }
      changeCurrentForm(params).then(() => {
        const newIndex = this.tableData.indexOf(preferData)
        const preIndex = this.activeIndex
        this.tableData[preIndex].iscurrent = '否'
        this.activeIndex = newIndex
        this.tableData[newIndex].iscurrent = '是'
      })
    },
    deletePreference(preferData) {
      const index = this.tableData.indexOf(preferData)
      if (preferData.iscurrent === '是') {
        this.activeIndex = 0
      }
      // 200if
      this.tableData.splice(index, 1)
      if (this.activeIndex === 0 && this.tableData.length) {
        this.tableData[0].iscurrent = '是'
      }
      console.log(preferData)
      const params = {
        formId: preferData.id,
        userId: this.userId
      }
      // deleteVolunteer(params)
    }
  },
  mounted() {
    this.initData()
  }
}
