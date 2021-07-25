import { getOverview } from '@/api/dashboard/dashboard'

export default {
  data() {
    return {
    }
  },
  methods: {
    initData() {
      getOverview().then(response => {
        const vo = response.data
        if (vo) {
          this.todayOrderNum = vo.todayOrderNum
          this.allOrderNum = vo.allOrderNum
          this.todaySaleVolume = vo.todaySaleVolume
          this.allSaleVolume = vo.allSaleVolume
          this.todayFavoriteServiceNum = vo.todayFavoriteServiceNum
          this.allFavoriteServiceNum = vo.allFavoriteServiceNum
          this.todayFavoriteShopNum = vo.todayFavoriteShopNum
          this.allFavoriteShopNum = vo.allFavoriteShopNum
        }
      })
    }
  },
  mounted() {
    console.log('mounted')
    this.initData()
  }
}
