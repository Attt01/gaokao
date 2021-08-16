import { updatePwd } from '@/api/login'
import { getInfo } from '@/api/login'

export default {
  data() {
    return {
      activeName: 'profile',
      user: {
        username: '',
        vipIsOrNot: '',
        nickname: '',
        score: '',
        provinceRank: '',
        subject: '',
        phone: '',
        email: ''
      }
    }
  },
  mounted: function() {
    this.init()
  },
  methods: {
    init() {
      getInfo().then(res => {
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
    }

  }
}
