import {provinceAndCityDataPlus} from "element-china-area-data";

export default {
  name: "Category",
  data() {
    return {
      // 遮罩层
      // loading: true,
      loading: false,
      // 显示搜索条件
      showSearch: true,
      //志愿列表
      volunteerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      dialogFormVisible: false,
      //请求参数
      listQuery: {
        page: 1,
        limit: 5,
        total: 0,
        level: undefined,
        location: [],
        classification: [],
        universityName: undefined,
        majorName: undefined,
      },
      levelOptions: [
        {
          value: 1,
          label: '普通类一段'
        },
        {
          value: 2,
          label: '普通类二段'
        },
      ],
      locationOptions: provinceAndCityDataPlus,
      classifyOptions: [
        {
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
        }
      ],
      //表单参数
      form: {},
    }
  },

  created() {
    this.fetchData();
  },

  methods: {
    //请求列表数据
    fetchData() {
      // this.loading = true;
      getVOList(this.listQuery).then(response => {
        this.volunteerList = response.data.content;
        this.loading = false;
        this.total = response.data.totalElements;
        this.listQuery.total = response.data.totalPages;
      })
    },
    changeLocation(value) {
      console.log(value)
    },
    changeClassify(value) {
      console.log(value)
    },

    /** 搜索按钮操作 */
    handleQuery() {
      this.listQuery.page = 1;
      this.fetchData();
    },

  }
}
