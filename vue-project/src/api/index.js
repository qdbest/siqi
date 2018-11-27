import axios from 'axios'
import {Message, Loading} from 'element-ui'

let loading;
// Add a request interceptor
axios.interceptors.request.use(function (config) {
  loading = Loading.service({target: '.el-table'});
  return Promise.resolve(config);
}, function (error) {
  loading.close();
  Message.error('请求超时!');
  return Promise.reject(error);
});

// Add a response interceptor
axios.interceptors.response.use(function (response) {
  loading.close();
  if (response.status && response.status == 200 && response.data.status == 'error') {
    Message.error({message: response.data.msg});
  }
  return response;
}, function (error) {
  loading.close();
  if (error.response.status == 504 || error.response.status == 404) {
    Message.error('服务器被连接错误');
  } else if (error.response.status == 403) {
    Message.error('权限不足,请联系管理员!');
  } else {
    Message.error(error.response.data.message);
  }
  return Promise.reject(error);
});

let base = '';

export const getRequest = (url, params) => {
  return new Promise((resolve, reject) => {
    axios.get(`${base}${url}`, {params})
      .then(response => {
        resolve(response);
      })
      .catch(error => {
        reject(error);
      });
  });
};

export const postRequest = (url, params) => {
  console.log({params});
  return new Promise((resolve, reject) => {
    axios.post(`${base}${url}`,params)
      .then(response => {
        resolve(response);
      })
      .catch(error => {
        reject(error);
      });
  });
};
