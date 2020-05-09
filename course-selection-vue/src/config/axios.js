import axios from 'axios'
import {Message} from "element-ui";
import router from '../router'
import global from "./global";

axios.defaults.baseURL = global.host; // 配置axios请求的地址
axios.defaults.headers.post['Content-Type'] = 'application/json; charset=utf-8';

//异步请求前在header里加入token
axios.interceptors.request.use(
  config => {
    if (config.url === global.host + 'admin/login/') {  //如果是登录操作，则不需要携带header里面的token
    } else {
      if (localStorage.getItem('token')) {
        config.headers.token = localStorage.getItem('token');
      }
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  });

//异步请求后，判断token是否过期
axios.interceptors.response.use(
  response => {
    //token错误或者过期
    if (response.data.code == '101' || response.data.code == '102') {
      Message.error(response.data.info)
      localStorage.removeItem('token')
      localStorage.removeItem('userName')
      router.replace('/login')
    }
    return response;
  },
  error => {
    Message.error("后端服务器异常  " + error)
    return Promise.reject(error)
  }
)

