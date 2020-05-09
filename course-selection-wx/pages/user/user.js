// pages/user/user.js
import HTTP from '../../utils/http.js'
import util from "../../utils/util.js"
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    //登陆相关
    loginLoading: false,
    param: {
      studentId: '',
      passworde: '',
      avatarUrl: '',
      code: ''
    },
    //主页相关
    user: null,
    recentCourses: [],
    listCourseParams: {
      limit: 5
    }
  },
  enterItem(e) {
    const id = e.currentTarget.dataset.item_id
    HTTP.Get('applet/item/enterItem/' + id, null).then(data => {
      if (data.code == 0) {
        wx.navigateTo({
          url: '/pages/itemDetail/itemDetail?id=' + id,
        })
      } else {
        wx.showModal({
          title: data.info,
          content: '你不在该选课的年级或学院',
          showCancel: false
        })
      }
    })
  },
  enterCourse(e) {
    const id = e.currentTarget.dataset.id
    wx.navigateTo({
      url: '/pages/courseDetail/courseDetail?id=' + id,
    })
  },
  getRecentCourse() {
    HTTP.Get('/applet/user_course/listAllByUser', this.data.listCourseParams).then(data => {
      this.setData({
        recentCourses: data.data,
      })
    })
  },
  gotoSetting(){
    wx.navigateTo({
      url: '/pages/setting/setting',
    })
  },
  gotoMySchedule() {
    wx.navigateTo({
      url: '/pages/schedule/schedule',
    })
  },
  gotoMyComment() {
    wx.navigateTo({
      url: '/pages/myComment/myComment',
    })
  },
  gotoMyCourse() {
    wx.navigateTo({
      url: '/pages/myCourse/myCourse',
    })
  },
  //登录相关
  //授权成功后回调函数
  getUserInfoResult(e) {
    //授权失败
    if (e.detail.userInfo == undefined) {
      wx.showModal({
        title: '授权失败',
        content: '请同意授权以登录系统',
        showCancel: false
      })
      //授权成功
    } else {
      console.log(e.detail.userInfo)
      app.globalData.userInfo = e.detail.userInfo
      //重新登录会重新上传头像路径到服务器
      this.setData({
        ['param.avatarUrl']: e.detail.userInfo.avatarUrl
      })
      this.login()
    }
  },
  accountChange(e) {
    this.data.param.studentId = e.detail
  },
  passwordChange(e) {
    this.data.param.password = e.detail
  },
  login() {
    this.setData({
      loginLoading: true
    })
    wx.login({
      success: res => {
        this.setData({
          ['param.code']: res.code,
          ['param.avatarUrl']: app.globalData.userInfo.avatarUrl
        })
        HTTP.Post('applet/user/login', this.data.param)
          .then(data => {
            this.setData({
              loginLoading: false
            })
            if (data.code == 0) {
              wx.setStorageSync('token', data.data.token)
              this.onLoad()
              wx.showToast({
                title: '登录成功！',
              })
            } else {
              wx.showModal({
                title: '登录失败!',
                content: '请检查账号密码',
                showCancel: false
              })
            }
          })
          .catch(data => {
            this.setData({
              loginLoading: false
            })
          })
      }
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function() {
    util.updateUser().then(user => {
      wx.stopPullDownRefresh()
      this.setData({
        user: user
      })
    })
    this.getRecentCourse()
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function(e) {
      console.log(e)
      this.setData({
        user: app.globalData.user
      })
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
    if(!this.data.user){
      return
    }
    this.onLoad()
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