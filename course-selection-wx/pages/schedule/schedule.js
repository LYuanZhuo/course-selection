// pages/schedule/schedule.js
import HTTP from '../../utils/http.js'
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    schedule: [],
    years: [],
    quarters: [{
      text: '秋季',
      value: '秋季'
    }, {
      text: '春季',
      value: '春季'
    }],
    days: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
    classIndexs: ['1,2节', '3,4节', '5,6节', '7,8节', '9,10节', '11,12节'],
    params: {
      year: 0,
      quarter: '',
      week: 0
    }
  },
  yearChange({detail}){
    this.setData({
      ['params.year']:detail
    })
    this.getSchedule()
  },
  quarterChange({ detail }){
    this.setData({
      ['params.quarter']: detail
    })
    this.getSchedule()
  },
  setYear() {
    let grade = app.globalData.user.grade
    let nowYear = (new Date()).getFullYear()
    let years = []
    for (let i = grade, j = 1; i <= nowYear && j <= 5; i++, j++) {
      years.push({
        text: i,
        value: i
      })
    }
    this.setData({
      years: years
    })
  },
  getSchedule() {
    HTTP.Get('/applet/user_course/getClassSchedule', this.data.params).then(data => {
      if (data.code == 0) {
        this.setData({
          schedule: data.data
        })
      } else {
        wx.showToast({
          title: data.info,
        })
      }
    })
  },
  setNow() {
    let now = new Date()
    let year = now.getFullYear()
    let month = now.getMonth() + 1
    let quarter = '秋季'
    if (month >= 3 && month <= 8) {
      quarter = '春季'
    }
    this.setData({
      ['params.year']: year,
      ['params.quarter']: quarter
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.setNow()
    this.setYear()
    this.getSchedule()
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