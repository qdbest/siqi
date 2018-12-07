<template>
  <div>
    <el-form :inline="true">
      <el-form-item>
        <el-input placeholder="请输入扫描商品条形码" suffix-icon="el-icon-ali-tiaoxingma" v-model="commodityCode"
                  @keypress.enter.native="search" ref="input"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-check" round @click="search">确认</el-button>
      </el-form-item>
      <el-checkbox v-model="showPaidPrice">显示进价</el-checkbox>
      <el-form-item label="应收">
        <el-input class="income" v-model="income" :disabled="true">
        </el-input>
      </el-form-item>
      <el-form-item label="实收">
        <el-input class="income" placeholder="实收" v-model="realIncome"></el-input>
      </el-form-item>
      <el-form-item label="找零">
        <el-input class="income" v-model="giveChange" :disabled="true">
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="success" round @click="receive"><i class="el-icon-ali-fl-renminbi"></i>结账</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="cartCommodities"
      stripe
      style="width: 100%">
      <el-table-column
        prop="id"
        label="序号">
      </el-table-column>
      <el-table-column
        prop="stockCommodity.commodity.name"
        label="商品">
      </el-table-column>
      <el-table-column
        prop="stockCommodity.commodity.specification"
        label="规格">
      </el-table-column>
      <el-table-column
        prop="stockCommodity.commodity.price"
        label="零售价">
      </el-table-column>
      <el-table-column v-if="showPaidPrice"
                       prop="stockCommodity.paidPrice"
                       label="进价">
      </el-table-column>
      <el-table-column
        label="数量">
        <template slot-scope="scope">
          <el-input-number v-model="scope.row.quantity" :min="1" :step="1"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column
        label="总价">
        <template slot-scope="scope">
          {{scope.row.stockCommodity.commodity.price*scope.row.quantity}}
        </template>
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
  import {getRequest, postRequest, putRequest, deleteRequest} from '../../api'
  import {Message} from 'element-ui'

  export default {
    name: "CartCommodity",

    data() {
      return {
        commodityCode: '',
        showPaidPrice: false,
        realIncome: 0,
        cartCommodities: [],
        oldCartCommodities: []
      }
    },
    watch: {
      cartCommodities: {
        handler(newValue) {
          for (let i = 0; i < newValue.length; i++) {
            if (this.oldCartCommodities[i].quantity !== newValue[i].quantity) {
              // 减库存
              putRequest(`api/cart`, newValue[i])
                .then(response => {
                  // 因引用问题，不能直接通过“=”进行赋值
                  this.oldCartCommodities = JSON.parse(JSON.stringify(newValue));
                })
                .catch(error => {
                  // 发生异常，比如库存不足，改回原值
                  this.cartCommodities = JSON.parse(JSON.stringify(this.oldCartCommodities));
                })
            }
          }
          localStorage.setItem("cartCommodities", JSON.stringify(this.cartCommodities));
        },
        deep: true
      }
    },
    computed: {
      income() {
        return this.cartCommodities.reduce((prev, next) => {
          return prev + next.stockCommodity.commodity.price * next.quantity;
        }, 0).toFixed(2);
      },
      giveChange() {
        return (this.realIncome - this.income).toFixed(2);
      }
    },
    created() {
      this.cartCommodities = this.oldCartCommodities = JSON.parse(localStorage.getItem("cartCommodities")) || [];
    },
    mounted() {
      this.$refs['input'].focus();
    },
    methods: {
      // 查库存
      search() {
        // 减库存
        getRequest(`api/cart/${this.commodityCode}`)
          .then(response => {
            this.cartCommodities.push(response.data.data);
            this.oldCartCommodities = JSON.parse(JSON.stringify(this.cartCommodities));
          });
        this.commodityCode = '';
      },
      handleDelete(cartCommodity) {
        deleteRequest(`api/cart`, cartCommodity)
          .then(response => {
            this.cartCommodities.splice(this.cartCommodities.indexOf(cartCommodity), 1);
          })
          .catch(error => {

          });
      },
      receive() {
        if (this.realIncome - this.income >= 0) {
          postRequest(`api/cart/receive`, {
            saleOrder: {
              income: this.income,
              realIncome: this.realIncome,
              giveChange: this.giveChange
            },
            cartCommodities: this.cartCommodities
          })
            .then(response => {
              this.cartCommodities = [];
              this.oldCartCommodities = [];
              this.realIncome = 0;
              this.$refs['input'].focus();
            });
        } else {
          Message.error("实收不能小于应收！请确保人工输入实收！");
        }
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
