// pages/comment/comment.js
import HTTP from "../../utils/http.js"
Page({

  /**
   * 页面的初始数据
   */
  data: {
    courses: [],
    coursesAllshow:false,
    paramList: {
      name: '',
      orderBy: 'id',
      ascOrDesc: 'asc',
      pageSize:20,
      currentPage:1
    }
  },
  //上拉触底触发获取更多课程
  getCoursesByReachBottom() {
    if (this.data.coursesAllshow) {
      return
    }
    this.setData({
      ['paramList.pageSize']: this.data.paramList.pageSize + 20
    })
    this.getCourses()
  },
  enterCourse(e) {
    const id = e.currentTarget.dataset.id
    wx.navigateTo({
      url: '/pages/courseDetail/courseDetail?id=' + id,
    })
  },
  keywordChange(val) {
    this.setData({
      ['paramList.name']: val.detail
    })
    this.getCourses() 
  },
  initGetCourses(){
    this.setData({
      ['paramList.pageSize']:20,
      coursesAllshow: false
    })
    this.getCourses() 
  },
  getCourses() {
    HTTP.Post('applet/basecourse/list', this.data.paramList).then(data => {
      wx.stopPullDownRefresh()
      this.setData({
        courses: data.data
      })
      if (data.total <= this.data.courses.length){
        this.setData({
          coursesAllshow:true
        })
      }
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.initGetCourses()
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
    this.initGetCourses()
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    this.getCoursesByReachBottom()
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  }
})