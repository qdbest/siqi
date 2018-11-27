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
### Vuex体会
getter类似computed属性，如果不对数据进行加工，可以通过mapState直接使用
mutations与actions的区别：mutation不能异步

### axios使用loading组件
使用axios拦截器
```
let loading;
// Add a request interceptor
axios.interceptors.request.use(function (config) {
  loading = Loading.service({target:'.el-table'});
  return config;
}, function (error) {
  loading.close();
  return Promise.reject(error);
});

// Add a response interceptor
axios.interceptors.response.use(function (response) {
  loading.close();
  return response;
}, function (error) {
  loading.close();
  return Promise.reject(error);
});
```
axios请求参数传递
```
//调用时，组装成对象作为参数传递
this.listCommodities({pageSize:this.pageSize,currentPage:this.currentPage})
//定义方法时，对参数进行解构{}
listCommodities({state, commit}, params) {
    axios.get('api/commodity', {params})
      .then(response => {
        commit('commoditiesChange', response.data.data.content);
        commit('totalChange',response.data.data.totalElements);
      })
      .catch(error => {
        Message.error(`获取数据失败！错误原因:${error.toString()}`);
      });
  }
```

### element ui 键盘事件需要加native
```
<el-input placeholder="请输入商品条码" v-model="commodity.code" ref="input" @keypress.enter.native="search">
          <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
        </el-input>
```
## 以下两个坑踩了一下午才爬出来
### 对象引用踩的坑
```
// 商品信息加入列表，因对象是引用，必须使用深拷贝，否则，对象里边的值会被后边的语句重置
  this.stockCommodities.push(JSON.parse(JSON.stringify(this.stockCommodity)));
```
### this.$refs['stockCommodityForm'].resetFields()不起作用的原因
- 从其他组件复制过来，因取消验证，将prop全部删除，导致踩了好多坑，参考以下文章
[vue element-ui表单重置失败（解决）](https://blog.csdn.net/menglinjie/article/details/81127712)

### 表单验证挖坑之旅
- 如果data有嵌套
    - 表单控件prop属性用prop="commodity.name"
    ```
    <el-form-item label="名称" prop="commodity.name">
              <el-input placeholder="名称" v-model="stockCommodity.commodity.name"
                        :disabled="true"></el-input>
            </el-form-item>
    ```

    - 验证变量需要加引号
    ```
    'commodity.code': [
                {required: true, message: '条码不能为空', trigger: 'change'},
              ],
    ```
    参考如下文章，官网只是简单示例
    [vue+element表单验证(多重object嵌套)](https://blog.csdn.net/yytoo2/article/details/82626219)
    

