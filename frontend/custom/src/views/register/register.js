import {isvalidUsername} from '@/utils/validate'
// import {getInfo, login} from "../../api/login";
import {STATUS_CODE} from "../../api/statusCode";
// import {setToken, setUserInfo} from "../../utils/auth";
// import {buildRouter} from "../../permission";
import { register } from "@/api/register.js";
import { UserInfoDialog } from './components';
import store from '@/store';

export default {
  name: 'login',
  // components: {LangSelect},
  data() {
    const validateUsername = (rule, value, callback) => {
      if (!isvalidUsername(value)) {
        callback(new Error('Please enter the correct user name'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 5) {
        callback(new Error('The password can not be less than 5 digits'))
      } else {
        callback()
      }
    }
    return {
      regForm: {
        phone: '',
        password: '',
        veryCode: '',
        nickname: '',
        score: 0,
        subject: '',
        province_rank: 0
      },
      loginRules: {
        username: [{required: true, trigger: 'blur', validator: validateUsername}],
        password: [{required: true, trigger: 'blur', validator: validatePassword}]
      },
      loading: false,
      pwdType: 'password'
    }
  },
  components: {
    UserInfoDialog
  },
  methods: {
    initData() {
      let userGaokaoInfo = JSON.parse(localStorage.getItem('userGaoKaoInfo'));
      //console.log(JSON.parse(localStorage.getItem('userGaoKaoInfo')));
      this.regForm.score = userGaokaoInfo.score;
      this.regForm.province_rank = userGaokaoInfo.province_rank;
      this.regForm.subject = userGaokaoInfo.subject;
    },
    showPwd() {
      if (this.pwdType === 'password') {
        this.pwdType = ''
      } else {
        this.pwdType = 'password'
      }
    },
    showInfoDialog() {
      store.commit('SHOW_DIALOG', true);
    },
    handleRegister() {
      register(this.regForm).then((res) => {
        console.log(res);
        if (res.code === STATUS_CODE.SUCCESS) {
          this.$message({
            type: 'success',
            message: '注册成功'
          });
          this.$router.push('/login');
        }
      })
    }
  },
  computed: {
    userInfoStr() {
      if (this.regForm.score) {
        let subs = this.regForm.subject;
        return '高考分数: ' + this.regForm.score
          + ' / 排名: ' + this.regForm.province_rank
          + ' / 选科: ' + subs[0][0] + ' ' + subs[1][0] + ' ' + subs[2][0];
      }
    }
  },
  mounted() {
    this.initData();
  },
}
