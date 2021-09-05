import { getVolunteer, getCurrentVolunteer, createVolunteerForm, changeCurrentForm } from "@/api/volunteer";
import { STATUS_CODE } from "@/api/statusCode";
import { TimeSelect } from "_element-ui@2.11.0@element-ui";
export default {
  data() {
    return {
      isExist: false,
      dialogVisible: false,
      formId: 0,
      userInfo: {
        id: 0,
        score: 0,
        subject: [],
        province_rank: 0
      },
      currentFormName: '暂无志愿表，点击添加',
      tableData: [
      // {
      //   num: '志愿1',
      //   school: 'school',
      //   profession: 'profession',
      //   isExist: false
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
      for (let index = 1; index <= 96; ++index) {
        this.tableData.push({
            num: '志愿' + index,
            school: '',
            profession: '',
            isExist: false
          });
      }
      //console.log('crdt', this.createData);
      getVolunteer().then(res => {
        if (res.code === STATUS_CODE.SUCCESS) {
          if (res.data.length) {
            this.isExist = true;
            getCurrentVolunteer().then(res => {
              //console.log(res);
              if (res.code === STATUS_CODE.SUCCESS) {
                this.userInfo.id = res.data.userId;
                this.formId = res.data.id;
                this.currentFormName = res.data.name;
                this.userInfo.score = res.data.score;
                this.userInfo.subject = res.data.subject;
                // for (let i = 0; i < res.data.volunteerList.length; ++i) {
                //   this.tableData[].school = ;
                //   this.tableData[].profession = ;
                // }
              }
            });
          }
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
    toScreen() {
      this.$router.push('/screen');
    }
  },
  mounted() {
    this.initData();
  },
  computed: {
    userInfoStr() {
      let subs = this.userInfo.subject;
      return '高考分数: ' + this.userInfo.score
        + ' / 排名: ' + this.userInfo.province_rank
        + ' / 选科: ' + subs[0] + ' ' + subs[1] + ' ' + subs[2];
    }
  }
}
