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
      //禁用参数
      edit: true,
      //多选参数
      multiProps: {multiple: true},
      //请求参数
      listQuery: {
        userId: 3,
        subject: "1",
        score: 600,
        page: 1,
        limit: 5,
        total: 10,
        other: undefined,
        universityName: undefined,
        majorName: undefined,
        type: undefined,
      },
      level: undefined,
      location: undefined,
      classification: undefined,
      majorType: undefined,
      levelOptions: [],
      locationOptions: [],
      majorOptions: [],
      classifyOptions: [],
      //表单参数
      form: {
        formId: undefined,
        volunteerPosition: 1,
        name: undefined,
        professionalName: undefined,
      },
      // 表单校验
      rules: {
        volunteerPosition: {required: true, message: "志愿序号不能为空", trigger: "blur"}
      }
    }
  },

  created() {
    this.selectBatch();
    this.selectRegion();
    this.selectSchoolType();
    this.selectMajorType();
    this.fetchData();
  },

  methods: {
    //请求列表数据
    fetchData() {
      this.handleArray();
      //默认加载全部数据
      if (!this.listQuery.type) {
        this.listQuery.type = 0;
      }
      console.log("this.listQuery.type");
      console.log(this.listQuery.type);
      console.log("！！！！！！！！！！！！");
      //获取列表
      getVOList(this.listQuery).then(response => {
        console.log("okkkkkkkkkkkkkkk");
        this.loading = false;
        /*this.total = response.data.totalElements;
        this.listQuery.total = response.data.totalPages;*/
      })
    },
    handleArray(){
      //合并数组
      let allInfo1=[];
      let info1 = this.classification;
      let i,j,z;
      const newInfo1 = [];
      if(this.classification){
        for (i = 0; i < info1.length; i++) {
          let array1 = info1[i][1];
          newInfo1.push(array1);
          allInfo1=newInfo1;
        }
      }else{
        allInfo1=[];
      }
      let info2 = this.majorType;
      const newInfo2 = [];
      if(this.majorType){
        for (j = 0; j < info2.length; j++) {
          let  array2 = info2[j][1];
          newInfo2.push(array2);
          allInfo1=newInfo2.concat(newInfo1);
        }
      }else{
        allInfo1=newInfo2;
      }
      let allInfo2=[];
      let info3 = this.location;
      const newInfo3 = [];
      if(this.location){
        for (z = 0; z < info3.length; z++) {
          let array3 = info3[z][1];
          newInfo3.push(array3);
          allInfo2=newInfo3;
        }
      }else{
        allInfo2=[];
      }
      let info4=[this.level];
      if(info4){
        allInfo2=newInfo3.concat(info4)
      }else{
        allInfo2=newInfo3;
      }
      let data=allInfo2.concat(allInfo1);
      this.listQuery.other= data.filter(Boolean);
      console.log("this.listQuery.other");
      console.log(this.listQuery.other);
    },
    reset() {
      this.form = {
        universityName: undefined,
        majorName: undefined,
        volunteerPosition: 1
      };
      this.formReset("form");
    },
    handleFill(row) {
      this.title = "填报志愿";
      this.edit = true;
      this.dialogFormVisible = true;
      //TODO:获取默认志愿表序号的方法
      this.form.formId = this.getFormId();
      this.form.universityName = row.universityName;
      this.form.majorName = row.majorName;
    },
    getFormId() {

    },
    cancel() {
      this.dialogFormVisible = false;
      this.reset();
    },
    submitForm: function () {
      this.$refs.form.validate(valid => {
        //后续调用新增志愿表的接口,新增序号为volunteerPosition的志愿记录
        submit(this.form.volunteerPosition, this.form).then(response => {
          if (response.code === 200) {
            this.$message({
              type: 'success',
              message: '已保存为第' + this.form.volunteerPosition + '志愿！'
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
    handleQuery(type) {
      this.listQuery.page = 1;
      this.listQuery.type = type;
      this.fetchData();
    },
  }

}
