<template>
  <el-container>
    <el-aside width="200px">
      <el-menu :default-active="$route.path" :default-openeds="['stock']" router>
        <el-submenu index="stock">
          <template slot="title"><i class="el-icon-ali-shangpinguanli"></i>商品管理</template>
          <el-menu-item index="/stock/category"><i class="el-icon-ali-tubiaozhizuomoban"></i>商品种类</el-menu-item>
          <el-menu-item index="/stock/jinhuo"><i class="el-icon-ali-jinhuo"></i>进货</el-menu-item>
          <el-menu-item index="/stock/shangjia"><i class="el-icon-ali-shangjia"></i>上架</el-menu-item>
        </el-submenu>
      </el-menu>
    </el-aside>
    <el-main>
      <keep-alive>
        <router-view></router-view>
      </keep-alive>
    </el-main>
  </el-container>
</template>

<script>
  export default {
    name: "Stock",
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
  .el-aside {
    background-color: #D3DCE6;
  }

  a {
    color: black;
  }

  a.router-link-active {
    color: #409EFF;
  }
</style>
