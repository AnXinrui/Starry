import axios, {AxiosInstance} from 'axios'

const httpInstance:AxiosInstance = axios.create({
    // baseURL: 'http://localhost:8080',
    // baseURL: 'http://192.168.159.101:8080
    baseURL: 'http://82.157.80.123:8080',
    // timeout: 1000,
    // headers: {'X-Custom-Header': 'foobar'}
});

httpInstance.defaults.withCredentials = true; // 支持跨域携带凭据 session

/**
 * 拦截器
 */
// 添加请求拦截器
httpInstance.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    // console.log('我要发请求了');
    return config;
}, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
});


// 添加响应拦截器
httpInstance.interceptors.response.use(function (response) {
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    // console.log('我收到响应了');
    return response.data;
}, function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    return Promise.reject(error);
});

export default httpInstance;