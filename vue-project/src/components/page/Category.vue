<template>
  <div>
    <el-form :inline="true">

      <el-form-item label="条码">
        <el-input placeholder="请输入商品条码">
          <el-button slot="append" icon="el-icon-search"></el-button>
        </el-input>
      </el-form-item>
      <el-form-item label="名称">
        <el-input placeholder="名称"></el-input>
      </el-form-item>
      <el-form-item label="规格">
        <el-input placeholder="规格"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-plus" round>添加</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="tabledata"
      stripe
      style="width: 100%">
      <el-table-column
        prop="date"
        label="日期"
        width="180">
      </el-table-column>
      <el-table-column
        prop="name"
        label="姓名"
        width="180">
      </el-table-column>
      <el-table-column
        prop="address"
        label="地址">
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
  export default {
    name: "Category",
    data() {
      return {}
    },
    computed: {},
    created() {
    },
    methods: {},
    components: {}
  }
</script>

<style scoped>

</style>
