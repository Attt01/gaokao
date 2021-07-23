<template>
  <div class="app-container">
    <div class="overview-layout">
      <el-row :gutter="20">
        <el-col :span="24">
          <div class="out-border">
            <div class="layout-title">今日概况</div>
            <div style="padding: 40px">
              <el-row>
                <el-col :span="6" class="color-danger overview-item-value">{{ todayOrderNum }}</el-col>
                <el-col :span="6" class="color-danger overview-item-value">{{ todaySaleVolume }}</el-col>
                <el-col :span="6" class="color-danger overview-item-value">{{ todayFavoriteServiceNum }}</el-col>
                <el-col :span="6" class="color-danger overview-item-value">{{ todayFavoriteShopNum }}</el-col>
              </el-row>
              <el-row class="font-medium">
                <el-col :span="6" class="overview-item-title">订单量</el-col>
                <el-col :span="6" class="overview-item-title">销售额</el-col>
                <el-col :span="6" class="overview-item-title">收藏服务数</el-col>
                <el-col :span="6" class="overview-item-title">关注店铺数</el-col>
              </el-row>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="overview-layout">
      <el-row :gutter="20">
        <el-col :span="24">
          <div class="out-border">
            <div class="layout-title">总概况</div>
            <div style="padding: 40px">
              <el-row>
                <el-col :span="6" class="color-danger overview-item-value">{{ allOrderNum }}</el-col>
                <el-col :span="6" class="color-danger overview-item-value">{{ allSaleVolume }}</el-col>
                <el-col :span="6" class="color-danger overview-item-value">{{ allFavoriteServiceNum }}</el-col>
                <el-col :span="6" class="color-danger overview-item-value">{{ allFavoriteShopNum }}</el-col>
              </el-row>
              <el-row class="font-medium">
                <el-col :span="6" class="overview-item-title">订单量</el-col>
                <el-col :span="6" class="overview-item-title">销售额</el-col>
                <el-col :span="6" class="overview-item-title">收藏服务数</el-col>
                <el-col :span="6" class="overview-item-title">关注店铺数</el-col>
              </el-row>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script src="./recommand.js"></script>

<style scoped>
.app-container {
  margin-top: 1px;
  margin-left: 120px;
  margin-right: 120px;
}

.layout-title {
  color: #606266;
  padding: 15px 20px;
  background: #F2F6FC;
  font-weight: bold;
}

.overview-layout {
  margin-top: 20px;
}

.overview-item-value {
  font-size: 24px;
  text-align: center;
}

.overview-item-title {
  margin-top: 10px;
  text-align: center;
}

.out-border {
  border: 1px solid #DCDFE6;
}

</style>
