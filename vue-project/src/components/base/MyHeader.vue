<template>
  <div>
    <el-header>
      <span>思起超市收银系统</span>
      <span></span>
      <nav>
        <router-link to="/cartCommodity">
          <i class="el-icon-ali-daishouhuo"></i>
          <span>售货</span>
        </router-link>
        <router-link to="/stock">
          <i class="el-icon-ali-jinhuo"></i>
          <span>进货 </span>
        </router-link>
      </nav>
    </el-header>
  </div>
</template>

<script>
  export default {
    name: "MyHeader",
    data() {
      return {
      }
    },
    computed: {
    },
    created() {
    },
    methods: {},
    components: {}
  }
</script>

<style scoped lang="less">
  .el-header {
    background-image: url("../../assets/logo.png");
    background-size: 40px;
    background-repeat: no-repeat;
    background-color: #34495E;
    color: #333;
    background-position: 10px;
    display: flex;
    > :first-child {
      flex: 5;
      height: 40px;
      margin: 10px 80px;
      line-height: 40px;
      color: white;
      font-size: 28px;
      font-weight: bold;
    }
    > :nth-child(2) {
      flex: 4;
    }
    > :nth-child(3) {
      flex: 2;
      margin-right: 40px;
      display: flex;
      a {
        flex: 1;
        color: white;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
      }
      a.router-link-active {
        color: lightgreen;
      }
    }
  }
</style>
