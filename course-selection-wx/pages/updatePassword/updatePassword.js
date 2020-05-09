// pages/updatePassword/updatePassword.js
import HTTP from '../../utils/http.js'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    loading: false,
    param: {
      oldPassword: '',
      firstNewPassword: '',
      secondNewPassword: '',
    },
  },
  oldPasswordChange({
    detail
  }) {
    this.setData({
      ['param.oldPassword']: detail
    })
  },
  firstNewPasswordChange({
    detail
  }) {
    this.setData({
      ['param.firstNewPassword']: detail
    })
  },
  secondNewPasswordChange({
    detail
  }) {
    this.setData({
      ['param.secondNewPassword']: detail
    })
  },
  updatePassword() {
    if (this.data.param.oldPassword == '' || this.data.param.firstNewPassword == '' || this.data.param.secondNewPassword == '') {
      wx.showModal({
        title: '参数错误',
        content: '密码不能为空',
        showCancel:false
      })
      return
    }
    this.setData({
      loading: true
    })
    HTTP.Post('applet/user/updatePassword', this.data.param)
      .then(data => {
        this.setData({
          loading: false
        })
        if (data.code == 0) {
          wx.showToast({
            title: '密码已修改',
          })
          setTimeout(res=>{
            wx.navigateBack({
            })
          },1000)
        } else {
          this.setData({
            loginLoading: false
          })
          wx.showModal({
            title: '修改密码失败!',
            content: data.info,
            showCancel: false
          })
        }
      })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  }
})