const app = getApp()
var config = require('../config.js')
const HOST = config.host;

function Get(url, params) {
  let promise = new Promise(function(resolve, reject) {
    wx.request({
      url: HOST + url,
      data: params,
      method: 'GET',
      header: {
        'Content-Type': 'application/json',
        'token': wx.getStorageSync('token')
      },
      success: res => {
        console.log(url + " 响应：", res.data)
        if (res.data.code == 101 || res.data.code == 102) {
          noLogin()
        } else {
          resolve(res.data)
        }
      },
      fail: res => {
        reject(res.data)
      }
    })
  });
  return promise
}

function Post(url, data) {
  let promise = new Promise(function(resolve, reject) {
    wx.request({
      url: HOST + url,
      data: data,
      method: 'POST',
      header: {
        'Content-Type': 'application/json',
        'token': wx.getStorageSync('token')
      },
      success: res => {
        console.log(url + " 响应：", res.data)
        if (res.data.code == 101 || res.data.code == 102) {
          noLogin()
        } else {
          resolve(res.data)
        }
      },
      fail: res => {
        wx.showToast({
          title: '网络异常！请稍后再试！',
          icon: 'none'
        })
        reject(res.data)
      }
    })
  });
  return promise
}

function noLogin() {
  const app = getApp()
  wx.removeStorageSync('user')
  wx.removeStorageSync('token')
  app.globalData.user = null
  wx.switchTab({
    url: '/pages/user/user',
  })
}

module.exports = {
  Get,
  Post
}