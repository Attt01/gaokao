import {getBatch, getMajorType, getRegion, getSchoolType, getVOList} from "../../api/screen";

export default {
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
      //多选参数
      multiProps: {multiple: true},
      //请求参数
      listQuery: {
        page: 1,
        limit: 5,
        total: 0,
        level: undefined,
        location: undefined,
        classification: [],
        majorType: [],
        universityName: undefined,
        majorName: undefined,
        //区分冲保稳的参数
        type: undefined,
      },
      levelOptions: [],
      locationOptions: [],
      majorOptions: [],
      classifyOptions: [],
      //表单参数
      form: {
        num: 1
      },
    }
  },

  created() {
    this.fetchData();
    this.selectBatch();
    this.selectRegion();
    this.selectSchoolType();
    this.selectMajorType();
  },

  methods: {
    //请求列表数据
    fetchData() {
      // this.loading = true;
      /* getVOList(this.listQuery).then(response => {
         this.volunteerList = response.data.content;*/
      this.volunteerList = [{
        "percent": "45",
        "universityName": "A大学",
        "majorName": "计科",
        "lowestScore": "657",
        "lowestPosition": "90",
        "enrollment": "95"
      },
        {
          "percent": "53",
          "universityName": "B大学",
          "majorName": "软工",
          "lowestScore": "100",
          "lowestPosition": "568",
          "enrollment": "95"
        },
        {
          "percent": "78",
          "universityName": "D大学",
          "majorName": "大数据",
          "lowestScore": "36",
          "lowestPosition": "590",
          "enrollment": "89"
        },
        {
          "percent": "85",
          "universityName": "Q大学",
          "majorName": "物联网",
          "lowestScore": "100",
          "lowestPosition": "578",
          "enrollment": "43"
        },
        {
          "percent": "99",
          "universityName": "K大学",
          "majorName": "电信",
          "lowestScore": "12",
          "lowestPosition": "590",
          "enrollment": "68"
        },
      ];
      this.loading = false;
      /*this.total = response.data.totalElements;
      this.listQuery.total = response.data.totalPages;*/
      // })
    },
    reset() {
      this.form = {
        num: 1
      };
      this.formReset("form");
    },
    handleFill() {
      this.title = "填报志愿";
      this.dialogFormVisible = true;
    },
    cancel() {
      this.dialogFormVisible = false;
      this.reset();
    },
    submitForm: function () {
      this.$refs.form.validate(valid => {
        //后续调用新增志愿表的接口,新增序号为num的志愿记录
        submit(this.form.num, this.form).then(response => {
          if (response.code === 200) {
            this.$message({
              type: 'success',
              message: '更新信息成功'
            })
          }
          if (response) {
            this.$refs.form.resetFields()
          }
          this.dialogFormVisible = false;
          this.fetchData();
        });
      });
    },
    changeLocation(value) {
      console.log(value)
    },
    changeClassify(value) {
      console.log(value)
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
    /** 搜索按钮操作 */
    handleQuery() {
      this.listQuery.page = 1;
      this.fetchData();
    },
  }

}
