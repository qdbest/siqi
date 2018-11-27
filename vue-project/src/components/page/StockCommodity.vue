<template>
  <div>
    <el-form :inline="true" :model="purchaseOrder" :rules="purchaseOrderRule" ref="purchaseOrderForm">
      <fieldset>
        <legend>进货单信息</legend>
        <el-form-item label="单号" prop="code">
          <el-input placeholder="单号" v-model="purchaseOrder.code"></el-input>
        </el-form-item>
        <el-form-item label="供货商" prop="seller">
          <el-input placeholder="供货商" v-model="purchaseOrder.seller"></el-input>
        </el-form-item>
      </fieldset>
    </el-form>
    <el-form :inline="true" :model="stockCommodity" :rules="stockCommodityRule" ref="stockCommodityForm">
      <fieldset>
        <legend>商品信息</legend>
        <el-form-item label="条码" prop="commodity.code">
          <el-input placeholder="请输入商品条码" v-model="stockCommodity.commodity.code" ref="input"
                    @keypress.enter.native="search">
            <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="名称" prop="commodity.name">
          <el-input placeholder="名称" v-model="stockCommodity.commodity.name"
                    :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="规格" prop="commodity.specification">
          <el-input placeholder="规格" style="width: 80px"
                    v-model="stockCommodity.commodity.specification"
                    :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="单位" prop="commodity.unit">
          <el-input placeholder="单位" style="width: 80px;" v-model="stockCommodity.commodity.unit"
                    :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="零售价" prop="commodity.price">
          <el-input placeholder="零售价" style="width: 80px" v-model="stockCommodity.commodity.price"
                    :disabled="true"></el-input>
        </el-form-item>
        <br>
        <el-form-item label="进价" prop="paidPrice">
          <el-input-number :precision="2" :step="0.1" :min="0" style="width: 140px"
                           v-model="stockCommodity.paidPrice"></el-input-number>
        </el-form-item>
        <el-form-item label="数量" prop="quantity">
          <el-input-number :precision="0" :step="1" :min="1" style="width: 140px"
                           v-model="stockCommodity.quantity"></el-input-number>
        </el-form-item>
        <el-form-item label="总价" prop="totalPrice">
          <el-input-number :precision="2" :step="0.1" :min="0" style="width: 140px"
                           v-model="totalPrice"></el-input-number>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-plus" round @click="add">添加</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="success" icon="el-icon-ali-tijiao" round @click="submit">提交</el-button>
        </el-form-item>
      </fieldset>
    </el-form>
    <el-table
      :data="stockCommodities"
      stripe
      style="width: 100%">
      <el-table-column
        prop="commodity.id"
        label="序号"
        width="60">
      </el-table-column>
      <el-table-column
        prop="commodity.code"
        label="条码"
        width="160">
      </el-table-column>
      <el-table-column
        prop="commodity.name"
        label="名称"
        width="200">
      </el-table-column>
      <el-table-column
        prop="commodity.specification"
        label="规格"
        width="200">
      </el-table-column>
      <el-table-column
        prop="commodity.unit"
        label="单位"
        width="60">
      </el-table-column>
      <el-table-column
        prop="commodity.price"
        label="零售价">
      </el-table-column>
      <el-table-column
        prop="paidPrice"
        label="进价">
      </el-table-column>
      <el-table-column
        prop="quantity"
        label="数量">
      </el-table-column>
      <el-table-column
        prop="totalPrice"
        label="总价">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
  import {getRequest, postRequest} from '../../api'
  import {Message} from 'element-ui'

  export default {
    name: "StockCommodity",
    data() {
      return {
        purchaseOrder: {},
        stockCommodity: {
          commodity: {},
          paidPrice: 0,
          quantity: 1
        },
        stockCommodities: [],
        purchaseOrderRule:{
          code:[
            {required: true, message: '单号不能为空', trigger: 'blur'}
          ],
          seller:[
            {required: true, message: '供货商不能为空', trigger: 'blur'}
          ]
        },
        stockCommodityRule: {
          'commodity.code': [
            {required: true, message: '条码不能为空', trigger: 'change'},
          ],
          'commodity.name': [
            {required: true, message: '名称不能为空', trigger: 'change'},
          ],
          'commodity.specification': [
            {required: true, message: '规格不能为空', trigger: 'change'}
          ],
          'commodity.unit': [
            {required: true, message: '单位不能为空', trigger: 'change'}
          ],
          'commodity.price': [
            {required: true, message: '零售价不能为空', trigger: 'change'},
          ]
        },
      }
    },
    computed: {
      totalPrice: {
        get() {
          return this.stockCommodity.totalPrice = this.stockCommodity.paidPrice * this.stockCommodity.quantity;
        },
        set(val) {
          this.stockCommodity.totalPrice = val;
          this.stockCommodity.paidPrice = this.stockCommodity.totalPrice / this.stockCommodity.quantity;
        }
      },
    },
    mounted() {
      this.$refs['input'].focus();
    },
    methods: {
      search() {
        getRequest(`api/commodity/find`, {code: this.stockCommodity.commodity.code})
          .then(response => {
            this.isExisted = response.data.data != null;
            if (!this.isExisted) {
              Message.error('该商品不存在，请先添加该商品');
              this.stockCommodity.commodity={};
            } else {
              this.stockCommodity.commodity = response.data.data;
            }
          });
      },
      add() {
        this.$refs['stockCommodityForm'].validate(valid => {
          if (!valid) {
            Message.error('表单验证失败');
          } else {
            // 商品信息加入列表，因对象是引用，必须使用深拷贝，否则，对象里边的值会被后边的语句重置
            this.stockCommodities.push(JSON.parse(JSON.stringify(this.stockCommodity)));
            // 需要重置的选项必须加上prop="name"的指向
            this.$refs['stockCommodityForm'].resetFields();
            this.$refs['input'].focus();
          }
        });
      },
      submit() {
        this.$refs['stockCommodityForm','purchaseOrderForm'].validate(valid => {
          if (!valid) {
            Message.error('表单验证失败');
          } else if (this.stockCommodities.length <= 0) {
            Message.error('没有进货明细，请先添加进货明细');
          } else {
            postRequest('api/stockCommodity/addAll', {
              purchaseOrder: this.purchaseOrder,
              stockCommodities: this.stockCommodities
            })
              .then(response => {
                Message.success('提交成功');
                this.$refs['purchaseOrderForm'].resetFields();
                this.$refs['stockCommodityForm'].resetFields();
                this.$refs['input'].focus();
                this.stockCommodities = [];
              });
          }
        });
      }
    }
  }
</script>

<style scoped lang="less">
  fieldset {
    border: 1px solid lightseagreen;
    margin-top: 10px;
    padding: 5px;
  }

  legend {
    color: lightseagreen;
  }
</style>
