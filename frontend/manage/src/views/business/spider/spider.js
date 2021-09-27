import date from "../../../utils/date";
import {getSpiderList, startSpider} from "../../../api/business/spider";

export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      //列表
      spiderList: [],
      //总条数
      total: 0,
      //请求分类列表的参数
      listQuery: {
        page: 1,
        limit: 5,
        total: 0,
      },
      form: {},
    }
  },

  created() {
    this.fetchData();
  },

  methods: {
    //请求列表数据
    fetchData() {
      this.loading = true;
      getSpiderList(this.listQuery).then(response => {
        this.spiderList = response.data.content;
        this.loading = false;
        this.total = response.data.totalElements;
        this.listQuery.total = response.data.totalPages;
      })
    },
    /** 更新按钮操作 */
    handleFresh() {
      this.listQuery.page = 1;
      this.fetchData();
    },
    /** 爬取按钮操作 */
    handleSpider() {
      startSpider().then(response => {
        if (response.code === 200) {
          this.$message({
            type: 'success',
            message: '爬取信息成功'
          })
          this.fetchData();
        } else {
          this.$message({
            type: 'error',
            message: '爬取信息失败'
          })
        }
      })
    },
    // 时间格式化
    formatDate1: function (row) {
      return date.formatDate.format(
        new Date(row.startTime),
        'yyyy-MM-dd hh:mm'
      )
    },
    formatDate2: function (row) {
      if (row.endTime === 0) {
        return date.formatDate.format(
          new Date(row.startTime),
          'yyyy-MM-dd hh:mm'
        )
      } else if (row.endTime === -1) {
        return "尚未完成"
      } else {
        return date.formatDate.format(
          new Date(row.endTime),
          'yyyy-MM-dd hh:mm'
        )
      }
    },
    // 分页需要的4个方法
    fetchNext() {
      this.listQuery.page = this.listQuery.page + 1
      this.fetchData()
    },
    fetchPrev() {
      this.listQuery.page = this.listQuery.page - 1
      this.fetchData()
    },
    fetchPage(page) {
      this.listQuery.page = page
      this.fetchData()
    },
    changeSize(limit) {
      this.listQuery.limit = limit
      this.fetchData()
    },
  }
}
