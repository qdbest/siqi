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

### watch，对复杂结构，如对象、数组，数组中又套对象的深度监控
```
//因为引用的问题，Vue无法保留数组中对象的副本，newValue和oldValue值相同，因此，此处采取在data中设置全局变量，每次改变，进行深度拷贝的方法保留数组的副本
watch: {
  saleCommodities: {
    handler(newValue) {
      for (let i = 0; i < newValue.length; i++) {
        if (this.oldSaleCommodities[i].quantity !== newValue[i].quantity) {
          // 减库存
          postRequest(`api/stockCommodity/reduce`, newValue[i])
            .then(response => {
              // 因引用问题，不能直接通过“=”进行赋值
              this.oldSaleCommodities = JSON.parse(JSON.stringify(newValue));
            })
            .catch(error => {
              // 发生异常，比如库存不足，改回原值
              this.saleCommodities = JSON.parse(JSON.stringify(this.oldSaleCommodities));
            })
        }
      }
    },
    deep: true
  }
},
```
[Vue watcher oldValue和newValue始终一样，watcher监听一个对象的具体属性](https://www.w2le.com/p/108)
    
## 上线运行
### vue修改配置
- 修改config/index.js，在build中修改assetsPublicPath: '/siqi/',代码如下：
```
'use strict'
// Template version: 1.3.1
// see http://vuejs-templates.github.io/webpack for documentation.

const path = require('path')

module.exports = {
  dev: {

    // Paths
    assetsSubDirectory: 'static',
    assetsPublicPath: '/',
    proxyTable: {
      '/api': {
        target: 'http://localhost:8899',//后端接口地址
        changeOrigin: true,//是否允许跨越
        pathRewrite: {
          '^/api': '/',//重写,
        }
      }
    },

    // Various Dev Server settings
    host: 'localhost', // can be overwritten by process.env.HOST
    port: 8080, // can be overwritten by process.env.PORT, if port is in use, a free one will be determined
    autoOpenBrowser: false,
    errorOverlay: true,
    notifyOnErrors: true,
    poll: false, // https://webpack.js.org/configuration/dev-server/#devserver-watchoptions-


    /**
     * Source Maps
     */

    // https://webpack.js.org/configuration/devtool/#development
    devtool: 'cheap-module-eval-source-map',

    // If you have problems debugging vue-files in devtools,
    // set this to false - it *may* help
    // https://vue-loader.vuejs.org/en/options.html#cachebusting
    cacheBusting: true,

    cssSourceMap: true
  },

  build: {
    // Template for index.html
    index: path.resolve(__dirname, '../dist/index.html'),

    // Paths
    assetsRoot: path.resolve(__dirname, '../dist'),
    assetsSubDirectory: 'static',
    assetsPublicPath: '/siqi/',

    /**
     * Source Maps
     */

    productionSourceMap: true,
    // https://webpack.js.org/configuration/devtool/#production
    devtool: '#source-map',

    // Gzip off by default as many popular static hosts such as
    // Surge or Netlify already gzip all static assets for you.
    // Before setting to `true`, make sure to:
    // npm install --save-dev compression-webpack-plugin
    productionGzip: false,
    productionGzipExtensions: ['js', 'css'],

    // Run the build command with an extra argument to
    // View the bundle analyzer report after build finishes:
    // `npm run build --report`
    // Set to `true` or `false` to always turn it on or off
    bundleAnalyzerReport: process.env.npm_config_report,
  }
}
```
- 修改router/index.js，增加mode:'history',base:'siqi',路由导航的时候会自动在前边加上siqi，就会与nginx中的设置对应起来
- 修改api/index.js，let base = '/siqiApi';将调用中的api删除，与nginx中的设置对应起来
### 配置nginx
```

#user  nobody;
worker_processes  1;

#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

#pid        logs/nginx.pid;


events {
    worker_connections  1024;
}


http {
    include       mime.types;
    default_type  application/octet-stream;

    #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
    #                  '$status $body_bytes_sent "$http_referer" '
    #                  '"$http_user_agent" "$http_x_forwarded_for"';

    #access_log  logs/access.log  main;

    sendfile        on;
    #tcp_nopush     on;

    #keepalive_timeout  0;
    keepalive_timeout  65;

    #gzip  on;

    server {
        listen       80;
        server_name  yucn.nat300.top;
        root					D:/VueWebRoot;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;

        location /foq {
            try_files $uri $uri/ /foq/index.html;
        }
        location /foqApi {
        		proxy_pass http://localhost:8888/;
				}

        location /siqi {
            try_files $uri $uri/ /siqi/index.html;
        }
        location /siqiApi {
        		proxy_pass http://localhost:8899/;
				}

        #error_page  404              /404.html;

        # redirect server error pages to the static page /50x.html
        #
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }

        # proxy the PHP scripts to Apache listening on 127.0.0.1:80
        #
        #location ~ \.php$ {
        #    proxy_pass   http://127.0.0.1;
        #}

        # pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000
        #
        #location ~ \.php$ {
        #    root           html;
        #    fastcgi_pass   127.0.0.1:9000;
        #    fastcgi_index  index.php;
        #    fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;
        #    include        fastcgi_params;
        #}

        # deny access to .htaccess files, if Apache's document root
        # concurs with nginx's one
        #
        #location ~ /\.ht {
        #    deny  all;
        #}
    }


    # another virtual host using mix of IP-, name-, and port-based configuration
    #
    #server {
    #    listen       8000;
    #    listen       somename:8080;
    #    server_name  somename  alias  another.alias;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}


    # HTTPS server
    #
    #server {
    #    listen       443 ssl;
    #    server_name  localhost;

    #    ssl_certificate      cert.pem;
    #    ssl_certificate_key  cert.key;

    #    ssl_session_cache    shared:SSL:1m;
    #    ssl_session_timeout  5m;

    #    ssl_ciphers  HIGH:!aNULL:!MD5;
    #    ssl_prefer_server_ciphers  on;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}

}

```



