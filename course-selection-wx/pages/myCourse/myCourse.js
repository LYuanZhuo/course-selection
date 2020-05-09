// pages/myCourse/myCourse.js
import HTTP from '../../utils/http.js'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    selectingCourses: null,
    selectSuccessCourses: null,
    selectFailCourses: null,
    allCourses: null,
    params: {
      status: null,
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
  getCourses(e) {
    let status = e.detail.name
    this.getCoursesByStatus(status)
  },
  getCoursesByStatus(status) {
    this.setData({
      ['params.status']: status
    })
    HTTP.Get('/applet/user_course/listAllByUser', this.data.params).then(data => {
      if (status == 0) {
        this.setData({
          selectingCourses: data.data,
        })
      } else if (status == 1) {
        this.setData({
          selectSuccessCourses: data.data,
        })
      } else if (status == 2) {
        this.setData({
          selectFailCourses: data.data,
        })
      } else {
        this.setData({
          allCourses: data.data,
        })
      }
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.getCoursesByStatus(0)
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