import { lockMember, unlockMember , getMemberList} from '@/api/business/member'
import date from "../../../utils/date";


export default {
  data() {
    return {

      listLoading: false,
      dataList: [],
      page: {
        keyword: '',
        total: 0,
        size: 8,
        page: 1
      },


    }
  },
  methods: {

    // 锁定状态格式化
    statusFormatter(row, column) {
      let status = row.status;
      if(status === 0){
        return '正常'
      } else {
        return '锁定'
      }
    },
    // 时间格式化
    formatDate1: function (row) {
      return date.formatDate.format(new Date(row.createTime), 'yyyy-MM-dd hh:mm');
    },
    formatDate2: function (row) {
      if(row.updateTime===0)
        return date.formatDate.format(new Date(row.createTime), 'yyyy-MM-dd hh:mm');
      else
        return date.formatDate.format(new Date(row.updateTime), 'yyyy-MM-dd hh:mm');
    },

    handleCurrentChange(page) {
      this.listByPage(page);
    },
    listByPage(page) {
      if (page !== undefined) {
        this.page.page = page;
      } else {
        this.page.page = 1;
      }
      this.listLoading = true
      getMemberList(this.page).then(res => {
        if (res) {
          this.page.total = res.data.totalPages;
          this.dataList = res.data.content;
        }
        this.listLoading = false;
      })
    },
    changeSwitch(row) {
       if(row.status===1){
         console.log(1111);
        lockMember(row.id).then(response=>{
          if(response){
            this.$refs.dataList.resetFields()
          }
          row.status=0;
        });
      }else {

        unlockMember(row.id).then(response=>{
          if(response){
            this.$refs.dataList.resetFields()
           }
          row.status=1;
       });
     }

    }

  },
  mounted() {
    this.listByPage();
  }
}
