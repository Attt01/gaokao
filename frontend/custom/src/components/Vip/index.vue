<template>
  <el-dialog
    title="加入VIP，享受更多优质服务"
    :visible.sync="dialogVisible"
    width="400px"
    center
    :before-close="handleClose">
<!--    <img class="vip-code" :src="require('@/assets/img/QR_code_fake.jpg')"/>-->
    <img class="vip-code" :src="'/xhr/v1/users/orders/saveOrder?userId=' + userId"/>
    <div class="vip-word">微信支付</div>
    <h2 class="vip-price">￥99.00</h2>
    <div class="vip-privilege">
      <div class="vip-title">VIP特权</div>
      <div class="icon-container">
        <i class="el-icon-unlock vip-icon"></i>
        <i class="el-icon-document vip-icon"></i>
        <i class="el-icon-edit vip-icon"></i>
        <i class="el-icon-data-line vip-icon"></i>
      </div>
      <div class="text-container">
        <div class="text">解锁更多院校</div>
        <div class="text">一键智能填报</div>
        <div class="text">测算录取概率</div>
        <div class="text">生成志愿表</div>
      </div>
    </div>
    <span slot="footer" class="dialog-footer">
    <el-button @click="hideDialog">取 消</el-button>
    <el-button type="primary" @click="hideDialog">确 定</el-button>
  </span>
  </el-dialog>
</template>

<script>
// 1.生成订单号和二维码
// 2.扫描二维码支付，回调支付结果
// 3.若未支付则订单状态为待支付，可在我的订单页面查看并支付，也可以取消订单
import { saveOrder, success, cancelOrder } from '@/api/vip'
import { getInfo } from '@/api/login'
export default {
  name: 'vip',
  props: {
    visible: Boolean
  },
  data() {
    return {
      dialogVisible: false,
      orderId: 0,
      code: '',
      userId: 0
    }
  },
  methods: {
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
        })
        .catch(_ => {})
    },
    hideDialog() {
      this.dialogVisible = false
    },
    init() {
      getInfo().then(res => {
        this.userId = res.data.id
      })
    }
    // 支付回调
    // onSubmitCallback(result) {
    //   // 发起微信支付
    //   if (result.payType == PayTypeEnum.WECHAT.value) {
    //     wxPayment(result)
    //       .then(() => {
    //         this.$success('支付成功')
    //         success(this.orderId)
    //       })
    //       .catch(err => {
    //         this.$error('订单未支付')
    //       })
    //       .finally(() => {
    //         this.disabled = false
    //       })
    //   }
    // }

  },
  watch: {
    visible(val, oldVal) {
      this.dialogVisible = val
      this.init()
    }
  }
}
</script>

<style scoped>
.vip-code{
  margin-left: 100px;
  width: 150px;
  height: 150px;
}
.vip-word{
  text-align: center;
  font-size: 15px;
}
.icon-container{
  display: flex;
}
.vip-title{
  height: 50px;
  font-size: 15px;
  margin-left: 20px;
  color: #409EFF;
}
.vip-icon{
  flex:1;
  font-size: 30px;
  text-align: center;
  color: #409EFF;
}
.text-container{
  display: flex;
}
.text{
  flex:1;
  text-align: center;
  font-size: 10px;
  color: #409EFF;
}
.vip-privilege{
  border: solid #73b5fa thin;
  margin-top: 30px;
  margin-bottom: 20px;
}
.vip-price{
  text-align: center;
}
</style>
