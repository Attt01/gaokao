import { updatePwd } from '@/api/login'
import { getInfo, updateUserInfo } from '@/api/login'

export default {
  data() {
    return {
      activeName: 'profile',
      user: {
        id: 0,
        username: '',
        vipIsOrNot: '',
        nickname: '',
        score: '',
        provinceRank: '',
        subject: '',
        phone: '',
        email: '',
        password: ''
      },
      edit: false
    }
  },
  mounted: function() {
    this.init()
  },
  methods: {
    init() {
      getInfo().then(res => {
        this.user.id = res.data.id
        this.user.username = res.data.username
        this.user.vipIsOrNot = res.data.vipIsOrNot
        this.user.nickname = res.data.nickname
        this.user.score = res.data.score
        this.user.provinceRank = res.data.provinceRank
        this.user.subject = res.data.subject
        this.user.phone = res.data.phone
        this.user.email = res.data.email
      })
      console.log(this.user)
    },
    handleClick(tab, event) {
      this.$router.push({ path: '/account/' + tab.name })
    },
    onClickEdit() {
      if (!this.edit) {
        // 进入修改页面
        this.edit = !this.edit
      } else {
        // 保存修改结果
        if (this.user.username === '') {
          this.$message({
            message: '用户名不能为空',
            type: 'error'
          })
          this.$refs.username.focus()
          return
        } else if (this.user.phone === '') {
          this.$message({
            message: '手机号码不能为空',
            type: 'error'
          })
          this.$refs.phone.focus()
          return
        }
        this.edit = !this.edit
        const params = {
          id: this.user.id,
          nickname: this.user.nickname,
          phone: this.user.phone,
          username: this.user.username
        }
        updateUserInfo(params).then(res => {
          if (res.code === 200) {
            this.$message({
              message: '修个个人信息成功！',
              type: 'succes',
              duration: 700
            })
          } else {
            this.$message({
              message: '修改失败，请稍后再试',
              type: 'error'
            })
          }
        })
      }
    }
  }
}
