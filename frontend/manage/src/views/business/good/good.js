import {
  getVolunteers, addVolunteer, updateVolunteer, deleteVolunteer
} from "../../../api/screen";

export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      //志愿列表
      volunteerList: [],
      //请求参数
      listQuery: {
        page: 1,
        size: 10,
        total: 0,
        searchText: ""
      },
      // 弹出层标题
      title: "",
      // 是否显示添加弹出层
      dialogFormVisible: false,
      //是否显示修改弹出层
      dialogFormVisibleEdit: false,
      //禁用参数
      edit: true,
      responseList: [],
      //表单参数
      form: {
        formId: undefined,
        volunteerId: undefined,
        volunteerPosition: undefined,
        section: undefined,
        name: undefined,
        professionalName: "",
        universityCode: "",
        province: "",
        city: "",
        address: "",
        undergraduateSchoolIsOrNot: false,
        juniorSchoolIsOrNot: false,
        is985: false,
        is211: false,
        PublicIsOrNot: false,
        PrivateIsOrNot: false,
        category: "",
        picLink: "",
        employmentRate: 0,
        abroadRate: 0,
        furtherRate: 0,
        volunteerSection: 0,
        lowestScore: 0,
        lowestPosition: 0,
        subjectRestrictionType: 0,
        subjectRestrictionDetail: "",
        score: 0,
        position: 0,
        enrollment: 0,
        time: 0,
        fee: 0,
        doubleFirstClassSubjectNumber: 0,
        countrySpecificSubjectNumber: 0,
        masterPoint: 0,
        doctorPoint: 0,
      },
      // 表单校验
      rules: {
        volunteerPosition: {required: true, message: "志愿序号不能为空", trigger: "blur"},
        section: {required: true, message: "志愿批次不能为空", trigger: "blur"},
      }
    }
  },

  created() {
    this.fetchData();
  },

  methods: {
    //请求列表数据
    fetchData() {
      this.loading = true;
      if (!this.listQuery.searchText) {
        this.listQuery.searchText = ""
      }
      //默认加载全部数据
      getVolunteers(this.listQuery).then(response => {
        this.responseList = [];
        this.responseList = response.data.content;
        this.volunteerList = [];
        this.responseList.forEach(item => {
          this.volunteerList.push({
            "volunteerId": item.id,
            "name": item.name,
            "province": item.province,
            "lowestScore": item.lowestScore,
            "lowestPosition": item.lowestPosition,
            "professionalName": item.professionalName,
            "category": item.category,
            "enrollment": item.enrollment,
            "time": item.time,
            "fee": item.fee,
            "universityCode": item.universityCode,
            "privateIsOrNot": item.privateIsOrNot,
            "publicIsOrNot": item.publicIsOrNot,
            "majorCode": item.majorCode,
            "subjectRestrictionType": item.subjectRestrictionType
          })
        })
        this.listQuery.total = response.data.totalPages;
        this.listQuery.searchText = ""
      }) .catch(error => {
        console.error('An error occurred while getting volunteers:', error);
      });
      this.loading = false;
    },
    handleSearch() {
      this.fetchData()
    },
    handleAdd() {
      this.title = "增加志愿";
      this.edit = true;
      this.dialogFormVisible = true;
      this.clearForm();
    },
    handleEdit(row) {
      this.title = "修改志愿";
      this.edit = true;
      this.dialogFormVisibleEdit = true;
      this.form.name = row.name;
      this.form.professionalName = row.professionalName;
      this.form.lowestPosition = row.lowestPosition;
      this.form.lowestScore = row.lowestScore;
      this.form.enrollment = row.enrollment;
      this.form.volunteerId = row.volunteerId;
    },
    handleDelete(preferData) {
      // confirm事件在删除前确认一下
      this.$confirm('此操作将永久删除该志愿, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteVolunteer(preferData.volunteerId).then((res) => {
          if (res.code === 200) {
            this.$message({
              message: '删除志愿成功！',
              type: 'success',
              duration: 700
            })
            // 在成功删除后，触发表格数据的刷新
            this.refreshTable();
          } else {
            this.$message({
              message: '删除志愿表失败！',
              type: 'error',
              duration: 700
            })
          }
        })
      })
    },
    refreshTable() {
      // 这里是更新表格数据的逻辑，具体根据你的项目实现来调用对应的方法
      // 例如，如果你的表格数据是从接口获取的，可以重新调用获取数据的接口方法
      this.fetchData(); // 假设 getData() 是获取表格数据的方法
    },
    reset() {
      this.form = {
        universityName: undefined,
        majorName: undefined,
        volunteerPosition: undefined
      };
      this.formReset("form");
    },
    clearForm() {
      this.form = {
        formId: undefined,
        volunteerId: undefined,
        volunteerPosition: undefined,
        section: undefined,
        name: undefined,
        professionalName: "",
        universityCode: "",
        province: "",
        city: "",
        address: "",
        undergraduateSchoolIsOrNot: false,
        juniorSchoolIsOrNot: false,
        is985: false,
        is211: false,
        PublicIsOrNot: false,
        PrivateIsOrNot: false,
        category: "",
        picLink: "",
        employmentRate: 0,
        abroadRate: 0,
        furtherRate: 0,
        volunteerSection: 0,
        lowestScore: 0,
        lowestPosition: 0,
        subjectRestrictionType: 0,
        subjectRestrictionDetail: "",
        score: 0,
        position: 0,
        enrollment: 0,
        time: 0,
        fee: 0,
        doubleFirstClassSubjectNumber: 0,
        countrySpecificSubjectNumber: 0,
        masterPoint: 0,
        doctorPoint: 0,
      };
    },
    cancel() {
      this.dialogFormVisible = false;
      this.reset();
    },
    submitAdd: function () {
      console.log(this.form)
      addVolunteer(this.form).then(response => {
        if (response.code === 200) {
          this.$message({
            type: 'success',
            message: '增加志愿成功'
          })
        }
        if (response) {
          this.clearForm()
        }
        this.dialogFormVisible = false;
        this.fetchData();
      });
    },
    submitEdit: function () {
        console.log(this.form)
        updateVolunteer(this.form).then(response => {
          if (response.code === 200) {
            this.$message({
              type: 'success',
              message: '修改志愿成功'
            })
          }
          if (response) {
            this.clearForm()
          }
          this.fetchData();
          this.dialogFormVisibleEdit = false;
        });
    },
    // 分页方法
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
      this.fetchData(page)
    },
    changeSize(limit) {
      this.listQuery.limit = limit
      this.fetchData()
    },
  }

}
