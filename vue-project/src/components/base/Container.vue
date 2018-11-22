<template>
  <el-container style="height: 100%; border: 1px solid #eee">
    <my-header></my-header>
    <el-main>
      <keep-alive>
        <router-view></router-view>
      </keep-alive>
    </el-main>
    <el-footer style="text-align: center;">Footer</el-footer>
  </el-container>
</template>

<script>
  import MyHeader from './MyHeader'

  export default {
    name: "Container",
    data() {
      return {}
    },
    computed: {},
    created() {
    },
    methods: {},
    components: {
      MyHeader
    }
  }
</script>

<style scoped lang="less">
  .el-header, .el-footer {
    background-color: #34495E;
    color: #fff;
    line-height: 60px;
  }

  .el-main {
    height: 100%;
  }

  .el-header {
    a {
      color: white;
    }

    a.router-link-active {
      color: lightgreen;
    }

  }
</style>
