<template>
  <div>
    <el-form :inline="true" :model="purchaseOrder" :rules="purchaseOrderRule" ref="purchaseOrderForm">
      <fieldset>
        <legend>进货单信息</legend>
        <el-form-item label="单号" prop="code">
          <el-input placeholder="单号" v-model="purchaseOrderCode"></el-input>
        </el-form-item>
        <el-form-item label="供货商" prop="seller">
          <el-input placeholder="供货商" v-model="purchaseOrder.seller"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="success" icon="el-icon-ali-tijiao" round @click="putIn">入库</el-button>
        </el-form-item>
      </fieldset>
    </el-form>
    <el-form :inline="true" :model="purchaseCommodity" :rules="purchaseCommodityRule" ref="purchaseCommodityForm">
      <fieldset>
        <legend>商品信息</legend>
        <el-form-item label="条码" prop="commodity.code">
          <el-input placeholder="请输入商品条码" v-model="purchaseCommodity.commodity.code" ref="input"
                    @keypress.enter.native="search">
            <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="名称" prop="commodity.name">
          <el-input placeholder="名称" v-model="purchaseCommodity.commodity.name"
                    :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="规格" prop="commodity.specification">
          <el-input placeholder="规格" style="width: 80px"
                    v-model="purchaseCommodity.commodity.specification"
                    :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="单位" prop="commodity.unit">
          <el-input placeholder="单位" style="width: 80px;" v-model="purchaseCommodity.commodity.unit"
                    :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="零售价" prop="commodity.price">
          <el-input placeholder="零售价" style="width: 80px" v-model="purchaseCommodity.commodity.price"
                    :disabled="true"></el-input>
        </el-form-item>
        <br>
        <el-form-item label="进价" prop="paidPrice">
          <el-input-number :precision="2" :step="0.1" :min="0" style="width: 140px"
                           v-model="purchaseCommodity.paidPrice"></el-input-number>
        </el-form-item>
        <el-form-item label="数量" prop="quantity">
          <el-input-number :precision="0" :step="1" :min="1" style="width: 140px"
                           v-model="purchaseCommodity.quantity"></el-input-number>
        </el-form-item>
        <el-form-item label="总价" prop="totalPrice">
          <el-input-number :precision="2" :step="0.1" :min="0" style="width: 140px"
                           v-model="totalPrice"></el-input-number>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-plus" round @click="add">添加</el-button>
        </el-form-item>
      </fieldset>
    </el-form>
    <el-table
      :data="purchaseCommodities"
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
      <el-table-column>
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="danger"
            icon="el-icon-close"
            @click="handleDelete(scope.row)">
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
  import {format} from 'date-fns'
  import {getRequest, postRequest} from '../../api'
  import {Message} from 'element-ui'

  export default {
    name: "PurchaseCommodity",
    data() {
      return {
        currentTime: new Date(),
        purchaseOrder: {},
        purchaseCommodity: {
          commodity: {},
          paidPrice: 0,
          quantity: 1
        },
        purchaseCommodities: [],
        purchaseOrderRule: {
          code: [
            {required: true, message: '单号不能为空', trigger: 'blur'}
          ],
          seller: [
            {required: true, message: '供货商不能为空', trigger: 'blur'}
          ]
        },
        purchaseCommodityRule: {
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
      purchaseOrderCode: {
        get() {
          return this.purchaseOrder.code = format(this.currentTime, "YYYYMMDDHHmmssSSS");
        },
        set(val) {
          this.purchaseOrder.code = val;
        }
      },
      totalPrice: {
        get() {
          return this.purchaseCommodity.totalPrice = this.purchaseCommodity.paidPrice * this.purchaseCommodity.quantity;
        },
        set(val) {
          this.purchaseCommodity.totalPrice = val;
          this.purchaseCommodity.paidPrice = this.purchaseCommodity.totalPrice / this.purchaseCommodity.quantity;
        }
      },
    },
    watch: {
      purchaseCommodities(newValue) {
        localStorage.setItem("purchaseCommodities", JSON.stringify(newValue));
      }
    },
    created() {
      this.purchaseCommodities = JSON.parse(localStorage.getItem("purchaseCommodities")) || [];
    },
    mounted() {
      this.$refs['input'].focus();
    },
    methods: {
      // 向后台查询商品是否存在
      search() {
        getRequest(`api/commodity/find`, {code: this.purchaseCommodity.commodity.code})
          .then(response => {
            this.isExisted = response.data.data != null;
            if (!this.isExisted) {
              Message.error('该商品不存在，请先添加该商品');
              this.purchaseCommodity.commodity = {};
            } else {
              this.purchaseCommodity.commodity = response.data.data;
            }
          });
      },
      // 向列表添加商品
      add() {
        this.$refs['purchaseCommodityForm'].validate(valid => {
          if (!valid) {
            Message.error('表单验证失败');
          } else {
            // 商品信息加入列表，因对象是引用，必须使用深拷贝，否则，对象里边的值会被后边的语句重置
            this.purchaseCommodities.push(JSON.parse(JSON.stringify(this.purchaseCommodity)));
            // 需要重置的选项必须加上prop="name"的指向
            this.$refs['purchaseCommodityForm'].resetFields();
            this.$refs['input'].focus();
          }
        });
      },
      handleDelete(purchaseCommodity) {
        this.purchaseCommodities = this.purchaseCommodities.filter(item => item !== purchaseCommodity)
      },
      // 入库
      putIn() {
        this.$refs['purchaseCommodityForm', 'purchaseOrderForm'].validate(valid => {
          if (!valid) {
            Message.error('表单验证失败');
          } else if (this.purchaseCommodities.length <= 0) {
            Message.error('没有进货明细，请先添加进货明细');
          } else {
            postRequest('api/stock/putIn', {
              purchaseOrder: this.purchaseOrder,
              purchaseCommodities: this.purchaseCommodities
            })
              .then(response => {
                this.$refs['purchaseOrderForm'].resetFields();
                this.$refs['purchaseCommodityForm'].resetFields();
                this.$refs['input'].focus();
                this.currentTime = new Date();
                this.purchaseCommodities = [];
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
