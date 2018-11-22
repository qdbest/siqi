<template>
  <div>
    <el-form :inline="true">
      <el-form-item>
        <el-input placeholder="请输入扫描商品条形码" suffix-icon="el-icon-ali-tiaoxingma"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-check" round>确认</el-button>
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
  </div>
</template>

<script>
  export default {
    name: "Sell",
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
