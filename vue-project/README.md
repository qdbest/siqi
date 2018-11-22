## Element Ui 使用第三方图标
  - [【Vue】 element ui 引入第三方图标](https://blog.csdn.net/b376924098/article/details/78286880)
  - [vue element-ui使用第三方图标库](https://www.cnblogs.com/zhengao/p/8480192.html)
  - [Vue Element-UI使用icon图标(第三方)--在线版](https://www.jianshu.com/p/8379597e3f97)
  - 个人体会
  开发时使用在线方式，产品则使用本地方式
  在index.html引入样式
  ```
  <!-- 在线方式 -->
      <link rel="stylesheet" href="//at.alicdn.com/t/font_924480_fsy3dl8wb9q.css">
      <!-- 本地方式 -->
      <!--<link rel="stylesheet" href="src/assets/icon/iconfont.css">-->
  ```
  iconfont.css
  ```
  .iconfont {
    font-family:"iconfont" !important;
    font-size:14px;
    font-style:normal;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
  }

  [class^="el-icon-ali"], [class*="el-icon-ali"]
  {
    font-family:"iconfont" !important;
    font-size:14px;
    font-style:normal;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
  }
  ```
