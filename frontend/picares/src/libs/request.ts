import axios from "axios";
import { message } from "antd";

const instance = axios.create({
  baseURL: "http://localhost:8080/api",
  timeout: 10000,
  withCredentials: true,
});

// Add a request interceptor
instance.interceptors.request.use(
  function (config) {
    // Do something before request is sent
    return config;
  },
  function (error) {
    // Do something with request error
    return Promise.reject(error);
  },
);

// Add a response interceptor
instance.interceptors.response.use(
  function (response) {
    const { data } = response;
    console.log(data);
    if (data.code === 40100) {
      if (!window.location.pathname.includes("/user/login")) {
        message.warning("请先登录");
        window.location.href = `/user/login?redirect=${window.location.href}`;
      }
    }
    if (data.code !== 0) {
      throw new Error(data.message ?? "服务器错误");
    }
    // Any status code that lie within the range of 2xx cause this function to trigger
    // Do something with response data
    return data;
  },
  function (error) {
    // Any status codes that falls outside the range of 2xx cause this function to trigger
    // Do something with response error
    return Promise.reject(error);
  },
);

export default instance;
