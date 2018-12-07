<template>
  <div id="app">
    <container></container>
  </div>
</template>

<script>
  import Container from './components/base/Container'
  export default {
    name: 'App',
    components:{
      Container
    }
  }
</script>

<style>
  /*元素内间距外间距清零*/
  *{
    padding:0;
    margin:0;
  }
  html,body,#app,.el-container{
    height:100%;
  }
  a{
    text-decoration: none;
  }
  .income > input {
    width: 160px;
    color: red !important;
    font-size: 16px;
    font-weight: bold;
  }
</style>
