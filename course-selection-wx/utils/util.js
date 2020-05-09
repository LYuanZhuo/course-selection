
import HTTP from './http.js'
//更新用户信息
function updateUser() {
  let promise = new Promise(function (resolve, reject) {
  HTTP.Get('applet/user',null).then(data=>{
    const app = getApp()
    app.globalData.user = data.data
    wx.setStorageSync('user', data.data)
    resolve(data.data)
  })
  })
  return promise
}
module.exports = {
  updateUser
}
