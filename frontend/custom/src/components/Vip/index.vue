<template>
  <el-dialog
    title="加入VIP，享受更多优质服务"
    :visible.sync="dialogVisible"
    width="400px"
    center
    :before-close="handleClose">
    <img class="vip-code" :src="require('@/assets/img/avatar.gif')"/>
    <div class="vip-word">微信支付</div>
    <span slot="footer" class="dialog-footer">
    <el-button @click="hideDialog">取 消</el-button>
    <el-button type="primary" @click="hideDialog">确 定</el-button>
  </span>
  </el-dialog>
</template>

<script>
export default {
  name: 'vip',
  props: {
    visible: Boolean
  },
  data() {
    return {
      dialogVisible: false
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
    }
  },
  watch: {
    visible(val, oldVal) {
      this.dialogVisible = val
    }
  }
}
</script>

<style scoped>
.vip-code{
  margin-left: 105px;
  width: 150px;
  height: 150px;
}
.vip-word{
  text-align: center;
  font-size: 20px;
}
</style>
