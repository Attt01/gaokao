import {isvalidUsername} from '@/utils/validate'
// import {getInfo, login} from "../../api/login";
import {STATUS_CODE} from "../../api/statusCode";
// import {setToken, setUserInfo} from "../../utils/auth";
// import {buildRouter} from "../../permission";
import { register } from "@/api/register.js";

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
      loginForm: {
        phone: '',
        password: '',
        veryCode: ''
      },
      loginRules: {
        username: [{required: true, trigger: 'blur', validator: validateUsername}],
        password: [{required: true, trigger: 'blur', validator: validatePassword}]
      },
      loading: false,
      pwdType: 'password'
    }
  },
  methods: {
    showPwd() {
      if (this.pwdType === 'password') {
        this.pwdType = ''
      } else {
        this.pwdType = 'password'
      }
    },
    handleRegister() {
      register(this.loginForm.phone, this.loginForm.password, this.loginForm.veryCode).then((res) => {
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
  }
}
