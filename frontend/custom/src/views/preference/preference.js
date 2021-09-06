import {
  getCurrentVolunteer,
  createVolunteerForm,
  changeCurrentForm,
  updateVolunteerName,
  upVolunteer,
  downVolunteer,
  swapVolunteer,
  deleteVolunteer
} from "@/api/volunteer";
import { STATUS_CODE } from "@/api/statusCode";
export default {
  data() {
    return {
      isExist: false,
      dialogVisible: false,
      //新建和修改表名称共用一个对话框，mode为true时表示新建
      mode: true,
      formId: 0,
      userInfo: {
        id: 0,
        score: 0,
        subject: [],
        provinceRank: 0
      },
      toSwapIndex: undefined,
      currentFormName: '暂无志愿表，点击添加',
      tableData: [
      // {
      //   num: '志愿1',
      //   school: 'school',
      //   profession: 'profession',
      //   isExist: false,
      //   id: 0
      // }
      ],
      createData: {
        generatedType: true,
        name: '',
        score: 0,
        subject: []
      },
      createRules: {
        name: [
          { required: true, message: '请输入志愿表名称', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    initData() {
      this.userInfo = JSON.parse(localStorage.getItem("userGaoKaoInfo"));
      this.createData.score = this.userInfo.score;
      this.createData.subject = this.userInfo.subject;
      //初始化表格数据全为空
      this.tableData.splice(0);
      for (let index = 1; index <= 96; ++index) {
        this.tableData.push({
            num: '志愿' + index,
            school: '',
            profession: '',
            isExist: false,
            id: 0,
            swapVisible: false
          });
      }
      //console.log('crdt', this.createData);
      getCurrentVolunteer().then(res => {
        //console.log(res);
        if (res.code === STATUS_CODE.SUCCESS) {
          this.isExist = true;
          this.userInfo.id = res.data.userId;
          this.formId = res.data.id;
          this.currentFormName = res.data.name;
          this.userInfo.score = res.data.score;
          this.userInfo.subject = res.data.subject;
          res.data.volunteerList.forEach(volunteer => {
            console.log(volunteer);
            this.tableData[volunteer.volunteerPosition - 1].school = volunteer.name;
            this.tableData[volunteer.volunteerPosition - 1].profession = volunteer.professionalName;
            this.tableData[volunteer.volunteerPosition - 1].isExist = true;
            this.tableData[volunteer.volunteerPosition - 1].id = volunteer.id;
          });
        }
      }).catch(res => {
        if (res.code === STATUS_CODE.FAIL) {
          this.isExist = false;
        }
      });
    },
    createForm() {
      this.$refs['createVolForm'].validate(isvalid => {
        if (isvalid) {
          createVolunteerForm(this.createData).then(res => {
            if (res.code === STATUS_CODE.SUCCESS) {
              this.$message({
                message: '创建成功',
                type: 'success'
              });
              changeCurrentForm({
                newFormId: res.data,
                preFormId: this.formId,
                userId: this.userInfo.id
              }).then(res => {
                if (res.code === STATUS_CODE.SUCCESS) {
                  this.dialogVisible = false;
                  this.formId = res.data;
                  this.initData();
                }
              });
            }
          });
        }
      })
    },
    updateName() {
      this.$refs['createVolForm'].validate(isvalid => {
        if (isvalid) {
          updateVolunteerName(this.formId, this.createData.name).then(res => {
            if (res.code === STATUS_CODE.SUCCESS) {
              this.$message({
                message: '修改成功',
                type: 'success'
              });
              this.currentFormName = this.createData.name;
              this.dialogVisible = false;
            }
          });
        }
      });
    },
    toScreen() {
      this.$router.push('/screen');
    },
    upVol(index) {
      if (index == 0) {
        this.$message({
          message: '不能继续上移了(>_<)',
          type: 'error'
        });
        return;
      }
      upVolunteer({
        formId: this.formId,
        section: true,
        volunteerId: this.tableData[index].id,
        volunteerPosition: index + 1
      }).then(res => {
        if (res.code === STATUS_CODE.SUCCESS) {
          this.$message({
            message: '移动成功',
            type: 'success'
          });
          this.initData();
        }
      });
    },
    downVol(index) {
      if (index == 95) {
        this.$message({
          message: '不能继续下移了(>_<)',
          type: 'error'
        });
        return;
      }
      downVolunteer({
        formId: this.formId,
        section: true,
        volunteerId: this.tableData[index].id,
        volunteerPosition: index + 1
      }).then(res => {
        if (res.code === STATUS_CODE.SUCCESS) {
          this.$message({
            message: '移动成功',
            type: 'success'
          });
          this.initData();
        }
      });
    },
    swapVol(index) {
      let tempIndex = this.toSwapIndex - 1;
      if (tempIndex < 0 || 95 < tempIndex) {
        this.$message({
          message: '填写志愿位置应该在1~96之间(>_<)',
          type: 'error'
        });
        return;
      }
      swapVolunteer({
        firstVolunteerId: this.tableData[index].id,
        firstVolunteerPosition: index + 1,
        formId: this.formId,
        secondVolunteerId: this.tableData[tempIndex].id,
        secondVolunteerPosition: tempIndex + 1,
        section: true
      }).then(res => {
        if (res.code === STATUS_CODE.SUCCESS) {
          this.$message({
            message: '移动成功',
            type: 'success'
          });
          this.initData();
        }
      });
    },
    deleteVol(index) {
      deleteVolunteer({
        formId: this.formId,
        section: true,
        volunteerPosition: index + 1
      }).then(res => {
        if (res.code === STATUS_CODE.SUCCESS) {
          this.$message({
            message: '删除成功',
            type: 'success'
          });
          this.initData();
        }
      });
    }
  },
  mounted() {
    this.initData();
  },
  computed: {
    userInfoStr() {
      let subs = this.userInfo.subject;
      return '高考分数: ' + this.userInfo.score
        + ' / 排名: ' + this.userInfo.provinceRank
        + ' / 选科: ' + subs[0] + ' ' + subs[1] + ' ' + subs[2];
    }
  }
}
