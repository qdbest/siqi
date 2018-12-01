<template>
  <div>
    <el-form :inline="true" :model="commodity" :rules="commodityRule" ref="commodityForm">
      <el-form-item label="条码" prop="code">
        <el-input placeholder="请输入商品条码" v-model="commodity.code" ref="input" @keypress.enter.native="search">
          <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
        </el-input>
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input placeholder="名称" v-model="commodity.name"></el-input>
      </el-form-item>
      <el-form-item label="规格" prop="specification">
        <el-input placeholder="规格" style="width: 80px" v-model="commodity.specification"></el-input>
      </el-form-item>
      <el-form-item label="单位" prop="unit">
        <el-input placeholder="单位" style="width: 80px;" v-model="commodity.unit"></el-input>
      </el-form-item>
      <el-form-item label="零售价" prop="price">
        <el-input-number :precision="2" :step="0.1" :min="0" style="width: 140px"
                         v-model="commodity.price"></el-input-number>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-plus" round @click="add">添加</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="commodities"
      stripe
      style="width: 100%">
      <el-table-column
        prop="id"
        label="序号"
        width="60">
      </el-table-column>
      <el-table-column
        prop="code"
        label="条码"
        width="160">
      </el-table-column>
      <el-table-column
        prop="name"
        label="名称"
        width="200">
      </el-table-column>
      <el-table-column
        prop="specification"
        label="规格"
        width="200">
      </el-table-column>
      <el-table-column
        prop="unit"
        label="单位"
        width="60">
      </el-table-column>
      <el-table-column
        prop="price"
        label="零售价">
      </el-table-column>
    </el-table>
    <el-pagination
      background
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :page-sizes="[5, 10, 15, 20]"
      :page-size="pageSize"
      :current-page.sync="currentPage"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
  </div>
</template>

<script>
  import {createNamespacedHelpers} from 'vuex'
  import {getRequest, postRequest} from '../../api'
  import {Message} from 'element-ui'

  const {mapState, mapGetters, mapMutations, mapActions} = createNamespacedHelpers('commodity');

  export default {
    name: "Commodity",
    data() {
      return {
        commodity: {
          price: 0
        },
        commodityRule: {
          code: [
            {required: true, message: '条码不能为空', trigger: 'blur'},
          ],
          name: [
            {required: true, message: '名称不能为空', trigger: 'blur'},
          ],
          specification: [
            {required: true, message: '规格不能为空', trigger: 'blur'}
          ],
          unit: [
            {required: true, message: '单位不能为空', trigger: 'blur'}
          ],
          price: [
            {required: true, message: '零售价不能为空', trigger: 'blur'},
          ]
        },
        pageSize: 10,
        currentPage: 1,
        isExisted: false,
      }
    },
    computed: {
      ...mapState({
        commodities: state => state.commodities,
        total: state => state.total
      })
    },
    created() {
      this.listCommodities({pageSize: this.pageSize, currentPage: this.currentPage});
    },
    mounted() {
      this.$refs['input'].focus();
    },
    methods: {
      ...mapMutations([
        'incrementTotal'
      ]),
      ...mapActions([
          'listCommodities'
        ]
      ),
      search() {
        getRequest(`api/commodity/find`, {code: this.commodity.code})
          .then(response => {
            this.isExisted = response.data.data != null;
            if (this.isExisted) {
              Message.error('该条码已经存在');
              this.commodity.code = '';
            }
          });
      },
      add() {
        this.$refs['commodityForm'].validate(valid => {
          if (!valid) {
            Message.error('表单验证失败');
          } else if (this.isExisted) {
            Message.error('该条码已经存在');
          } else {
            postRequest('api/commodity/add', this.commodity)
              .then(response => {
                Message.success('添加商品成功');
                this.$refs['commodityForm'].resetFields();
                this.$refs['input'].focus();
                this.listCommodities({pageSize: this.pageSize, currentPage: this.currentPage});
              });
          }
        });
      },
      handleSizeChange(val) {
        this.pageSize = val;
        this.listCommodities({pageSize: this.pageSize, currentPage: this.currentPage});
      },
      handleCurrentChange(val) {
        this.currentPage = val;
        this.listCommodities({pageSize: this.pageSize, currentPage: this.currentPage})
      }
    },
    components: {}
  }
</script>

<style scoped>
  .el-pagination {
    margin: 10px 0px;
  }
</style>
