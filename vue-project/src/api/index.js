import axios from 'axios'
import {Message, Loading} from 'element-ui'

// let loading;
// Add a request interceptor
axios.interceptors.request.use(function (config) {
  // loading = Loading.service({target: '.el-table'});
  return Promise.resolve(config);
}, function (error) {
  // loading.close();
  Message.error('请求超时!');
  return Promise.reject(error);
});

// Add a response interceptor
axios.interceptors.response.use(function (response) {
  // loading.close();
  if (response.status && response.status == 200 && response.data.code === 0 && response.data.msg !== null) {
    Message.success(response.data.msg);
  }
  return response;
}, function (error) {
  // loading.close();
  if (error.response.status == 504 || error.response.status == 404) {
    Message.error('服务器被连接错误');
  } else if (error.response.status == 401) {
    Message.error('请先登录');
  } else if (error.response.status == 403) {
    Message.error('权限不足,请联系管理员!');
  } else {
    Message.error(error.response.data.message);
  }
  return Promise.reject(error);
});

let base = '';

export const getRequest = (url, config) => {
  return new Promise((resolve, reject) => {
    axios.get(`${base}${url}`, config)
      .then(response => {
        resolve(response);
      })
      .catch(error => {
        reject(error);
      });
  });
};

export const postRequest = (url, data, config) => {
  return new Promise((resolve, reject) => {
    axios.post(`${base}${url}`, data, config)
      .then(response => {
        resolve(response);
      })
      .catch(error => {
        reject(error);
      });
  });
};

export const putRequest = (url, data, config) => {
  return new Promise((resolve, reject) => {
    axios.put(`${base}${url}`, data, config)
      .then(response => {
        resolve(response);
      })
      .catch(error => {
        reject(error);
      });
  });
};

export const deleteRequest = (url, config) => {
  return new Promise((resolve, reject) => {
    axios.delete(`${base}${url}`, config)
      .then(response => {
        resolve(response);
      })
      .catch(error => {
        reject(error);
      });
  });
};
