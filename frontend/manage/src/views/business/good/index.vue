<template>
  <div class="app-container">
    <!--搜索栏部分-->
    <el-form :model="listQuery" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="商品名称" prop="name">
        <el-input
          v-model="listQuery.name"
          placeholder="请输入商品名称"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="listQuery.status"
          placeholder="请选择状态"
          size="small"
          style="width: 240px">
          <el-option
            v-for="item in statusOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click.native="handleQuery" v-perm="'goods:view'">
          搜索
        </el-button>
      </el-form-item>
    </el-form>

    <!--按钮部分-->
    <el-row :gutter="20" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd" v-perm="'goods:add'">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" icon="el-icon-folder-add" size="mini" :disabled="multiple"
                   @click.native="handleOnSell" v-perm="'goods:changeStatus'">批量上架
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" icon="el-icon-folder-remove" size="mini" :disabled="multiple"
                   @click.native="handleOffSell" v-perm="'goods:changeStatus'">批量下架
        </el-button>
      </el-col>
      <!-- <el-col :span="1.5">
         <el-button type="warning" icon="el-icon-crop" size="mini" :disabled="multiple"
                    @click.native="handleOutOff">商品暂停服务
         </el-button>
       </el-col>-->
      <el-col :span="1.5">
        <el-button type="danger" icon="el-icon-lock" size="mini" :disabled="multiple"
                   @click.native="handleClose" v-perm="'goods:audit'">封禁商品
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" icon="el-icon-unlock" size="mini" :disabled="multiple"
                   @click.native="handleUnClose" v-perm="'goods:audit'">解封商品
        </el-button>
      </el-col>
      <div class="top-right-btn">
        <el-row>
          <el-tooltip class="item" effect="dark" content="刷新" placement="top">
            <el-button size="mini" circle icon="el-icon-refresh" @click="handleQuery"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="显隐列" placement="top" v-if="columns">
            <el-button size="mini" circle icon="el-icon-menu" @click="showColumn()"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" :content="showSearch ? '隐藏搜索' : '显示搜索'" placement="top">
            <el-button size="mini" circle icon="el-icon-search" @click="showSearch=!showSearch"/>
          </el-tooltip>
        </el-row>
        <el-dialog :title="toolbarTitle" :visible.sync="open" append-to-body>
          <el-transfer
            :titles="['显示', '隐藏']"
            v-model="visibleValue"
            :data="columns"
            @change="dataChange"
          ></el-transfer>
        </el-dialog>
      </div>
    </el-row>
    <br>

    <!--列表部分-->
    <el-table :data="goodList" v-loading="loading" @selection-change="handleSelectionChange" border
              highlight-current-row>
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="商品名称" align="center" prop="name" key="name" v-if="columns[0].visible">
        <template slot-scope="scope">
          {{ scope.row.name }}
        </template>
      </el-table-column>
      <el-table-column label="商品图片" align="center" prop="thumbnail" key="thumbnail" v-if="columns[1].visible">
        <template slot-scope="scope">
          <img :src="scope.row.thumbnail" width="70" height="70"/>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" key="status" show-overflow-tooltip
                       v-if="columns[2].visible">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.status=== 1">在售中</el-tag>
          <el-tag type="info" v-if="scope.row.status=== 0">审核中</el-tag>
          <div v-if="scope.row.status=== -1">
            <el-tag type="warning">已驳回</el-tag>
            <span>{{ scope.row.auditOpinion }}</span>
          </div>
          <el-tag v-if="scope.row.status=== -2">已下架</el-tag>
          <span v-if="scope.row.status=== -3">暂停服务</span>
          <el-tag type="danger" v-if="scope.row.status=== -4">封禁</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="审核意见" align="center" prop="auditOpinion" key="auditOpinion" v-if="columns[12].visible"/>
      <el-table-column label="所属商铺" align="center" prop="shopName" key="shopName" v-if="columns[3].visible"/>
      <el-table-column label="商品分类" align="center" prop="categoryName" key="categoryName" v-if="columns[4].visible"/>
      <el-table-column label="商品标签" align="center" prop="tagNames" key="tagNames" v-if="columns[5].visible"/>
      <el-table-column label="简要描述" align="center" prop="summary" show-overflow-tooltip key="summary"
                       v-if="columns[6].visible">
        <template slot-scope="scope">
          {{ scope.row.summary }}
        </template>
      </el-table-column>
      <el-table-column label="商品原价" align="center" prop="original_price" key="original_price" v-if="columns[7].visible">
        <template slot-scope="scope">
          {{ scope.row.originalPrice }}
        </template>
      </el-table-column>
      <el-table-column label="商品现价" align="center" prop="sale_price" key="sale_price" v-if="columns[8].visible">
        <template slot-scope="scope">
          {{ scope.row.salePrice }}
        </template>
      </el-table-column>
      <el-table-column label="优先级" align="center" prop="priority" key="priority" v-if="columns[9].visible" sortable
                       :sort-orders="['ascending','descending']">
        <template slot-scope="scope">
          {{ scope.row.priority }}
        </template>
      </el-table-column>
      <el-table-column label="商品评分" align="center" prop="averageScore" key="averageScore" sortable
                       :sort-orders="['ascending','descending']">
      </el-table-column>
      <el-table-column label="商品月销量" align="center" prop="monthSale" key="monthSale" sortable
                       :sort-orders="['ascending','descending']">
      </el-table-column>
      <el-table-column label="到店服务" align="center" prop="is_2shop" key="is_2shop" v-if="columns[10].visible">
        <template slot-scope="scope">
          <span v-if="scope.row.toShopIsOrNot=== false">不支持</span>
          <span v-if="scope.row.toShopIsOrNot=== true">支持</span>
        </template>
      </el-table-column>
      <el-table-column label="上门服务" align="center" prop="status" key="status" v-if="columns[11].visible">
        <template slot-scope="scope">
          <span v-if="scope.row.door2doorIsOrNot=== false">不支持</span>
          <span v-if="scope.row.door2doorIsOrNot=== true">支持</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit"
                     @click="handleUpdate(scope.row)" v-perm="'goods:update'">修改
          </el-button>
          <div v-if="scope.row.status===0">
            <el-button size="mini" type="text" icon="el-icon-view"
                       @click="handleAudit(scope.row)" v-perm="'goods:audit'">审核
            </el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <br>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-pagination layout="prev, pager, next" @current-change="fetchPage" :page-size="listQuery.size"
                     :page-count="listQuery.total" style="text-align:center;margin:10px">
      </el-pagination>
    </el-col>

    <!-- 审核通过对话框 -->
    <el-dialog :title="passTitle" :close-on-click-modal="false" :visible.sync="passFormVisible" width="650px">
      <el-form ref="passForm" :model="passForm" label-width="100px" :rules="passFormRules">
        <el-form-item label="服务标签" prop="tags">
          <el-select v-model="passForm.tagIds" multiple collapse-tags placeholder="请选择分组（可多选）">
            <el-option
              v-for="tag in dynamicTags"
              :key="tag.id"
              :label="tag.name"
              :value="tag.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审核意见" prop="auditOpinion">
          <el-input v-model="passForm.auditOpinion" placeholder="请输入审核意见" maxlength=100 style="width: 360px"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="passCancel">取消</el-button>
        <el-button type="primary" @click.native="passSubmit">提交</el-button>
      </div>
    </el-dialog>

    <!-- 驳回信息对话框 -->
    <el-dialog :title="auditTitle" :close-on-click-modal="false" :visible.sync="notPassFormVisible" width="650px">
      <el-form ref="notPassForm" :model="notPassForm" label-width="100px" :rules="notPassFormRules">
        <el-form-item label="审核意见" prop="auditOpinion">
          <el-input v-model="notPassForm.auditOpinion" placeholder="请输入审核意见" maxlength=100 style="width: 360px"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="auditCancel">取消</el-button>
        <el-button type="primary" @click.native="auditSubmit">提交</el-button>
      </div>
    </el-dialog>

    <!-- 添加/修改/审核服务对话框 -->
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="dialogFormVisible" width="650px">
      <el-steps :active="goodsCreateStep" finish-status="success" simple style="margin-bottom: 20px">
        <el-step title="基本信息">
        </el-step>
        <el-step title="商品详情">
        </el-step>
        <el-step title="规则属性">
        </el-step>
      </el-steps>

      <div v-if="goodsCreateStep === 1">
        <el-form ref="baseForm" :model="baseForm" label-width="120px" :rules="baseFormRules">
          <el-form-item label="服务分类" prop="categoryId">
            <el-tooltip class="item" effect="dark" content="请先确保分类下存在规格属性，再添加服务" placement="top-start">
              <el-select v-model="baseForm.categoryId" filterable placeholder="请选择分类" style="width: 400px"
                         :disabled="edit">
                <el-option
                  v-for="dict in categoryOptions"
                  :key="dict.id"
                  :label="dict.name"
                  :value="dict.id"
                />
              </el-select>
            </el-tooltip>
          </el-form-item>
          <el-form-item label="服务名称" prop="name">
            <el-input v-model="baseForm.name" placeholder="请输入服务名称" maxlength=64 style="width: 400px" :disabled="edit"/>
          </el-form-item>
          <el-form-item label="两小时接单数" prop="serviceCountPerPeriod">
            <el-input v-model="baseForm.serviceCountPerPeriod" placeholder="两小时内最大接单数" type="number" :disabled="edit"
                      style="width: 400px"/>
          </el-form-item>
          <el-form-item label="首页图片" prop="thumbnail">
            <single-upload v-model="baseForm.thumbnail"></single-upload>
          </el-form-item>
          <el-form-item label="服务轮播图" prop="carousels">
            <multi-upload v-model="baseForm.carousels"></multi-upload>
          </el-form-item>
          <el-form-item label="简要描述" prop="summary">
            <el-input
              prop="summary"
              style="width: 400px"
              type="textarea"
              :disabled="edit"
              :rows="2"
              maxlength=120
              placeholder="请输入简要描述"
              v-model="baseForm.summary">
            </el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer" align="center">
          <el-button @click.native="cancel">取消</el-button>
          <el-button type="primary" @click="nextStep('baseForm')">下一步</el-button>
        </div>
      </div>
      <div v-if="goodsCreateStep === 2">
        <el-form ref="detailForm" :model="detailForm" label-width="100px" :rules="detailFormRules">
          <el-row>
            <el-col :span="12">
              <el-form-item label="上门服务">
                <el-switch v-model="detailForm.door2doorIsOrNot" :disabled="edit"></el-switch>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="到店服务">
                <el-switch v-model="detailForm.toShopIsOrNot" :disabled="edit"></el-switch>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="服务详情">
            <Tinymce v-model="detailForm.description"></Tinymce>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer" align="center">
          <el-button type="primary" @click="preStep">上一步</el-button>
          <el-button type="primary" @click="nextStep('detailForm')">下一步</el-button>
        </div>
      </div>
      <div v-if="goodsCreateStep === 3">
        <product-attr-detail :category-id="baseForm.categoryId" :sku-form="skuForm"
                             :isAuditButtonShow="isAuditButtonShow" :isSubmitButtonShow="isSubmitButtonShow"
                             :edit="edit" @preStep="preStep" @nextStep="nextStep" @audit="audit">
        </product-attr-detail>
      </div>
    </el-dialog>
  </div>
</template>

<script src="./good.js"></script>

<style>
/*解决表头与数据不对齐的问题*/
.el-table th.gutter {
  display: table-cell !important;
}

.top-right-btn {
  position: relative;
  float: right;
}
</style>

