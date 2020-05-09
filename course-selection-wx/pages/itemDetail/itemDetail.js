// pages/itemDetail/itemDetail.js
import HTTP from "../../utils/http.js"
import util from "../../utils/util.js"
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    activeNames: [],
    selectedCourses: [],
    selectCourseName: '',
    userScore: 0,
    show: false,
    item: {},
    courses: [],
    coursesTotal: 0,
    coursesPageCount: 0,
    itemId: 0,
    coursesAllshow: false,
    courseListParam: {
      pageSize: 10,
      currentPage: 1,
      itemId: '',
    },
    selectParam: {
      courseId: 0,
      score: 0
    }
  },
  onChange(event) {
    this.setData({
      activeNames: event.detail
    });
  },
  enterCourse(e) {
    const id = e.currentTarget.dataset.id
    wx.navigateTo({
      url: '/pages/courseDetail/courseDetail?id=' + id,
    })
  },
  //退选
  cancelSelect(e){
    var name = e.currentTarget.dataset.name
    var score = e.currentTarget.dataset.score
    var that = this
    wx.showModal({
      title: '确定退选 '+name+'？',
      content: '退选成功将返还该选课积分：'+score,
      success:function(res){
        if(res.confirm){
          var param = {
            'courseId': e.currentTarget.dataset.id
          }
          HTTP.Post('applet/user_course/cancelSelect', param).then(data => {
            if (data.code == 0) {
              that.getSelectedCourses()
              that.getCourses()
              that.updateScore()
              wx.showToast({
                title: '已成功退选',
              })
            } else {
              wx.showToast({
                title: data.info,
                icon: 'none'
              })
            }
          })
        }
      }
    })
  },
  //获取已选择课程
  getSelectedCourses() {
    var param = {
      'itemId': this.data.itemId
    }
    HTTP.Get('applet/user_course/listAllByItemAndUser', param).then(data => {
      this.setData({
        selectedCourses: data.data
      })
    })
  },
  //更新用户积分
  updateScore() {
    util.updateUser().then(data => {
      this.setData({
        userScore: data.score
      })
    })
  },
  //监控积分选择
  scoreChange(value) {
    this.setData({
      ['selectParam.score']: value.detail
    })
  },
  //打开选课窗口
  openSelect(e) {
    this.setData({
      show: true,
      ['selectParam.courseId']: e.currentTarget.dataset.id,
      selectCourseName: e.currentTarget.dataset.name
    })
  },
  //关闭选课窗口
  closeSelect() {
    this.setData({
      show: false
    });
  },
  //选课
  select() {
    HTTP.Post('applet/user_course/select', this.data.selectParam).then(data => {
      this.closeSelect()
      if (data.code == 602) {
        wx.showModal({
          title: '选课时间错误',
          content: data.info,
          showCancel: false
        })
      } else if (data.code == 600) {
        wx.showModal({
          title: data.info,
          content: '冲突课程：' + data.data.courseName + '(' + data.data.courseDetail + ')',
          showCancel: false
        })
      } else if (data.code == 601) {
        wx.showModal({
          title: data.info,
          content: '选课数量上限：' + data.data,
          showCancel: false
        })
      } else if (data.code == 603) {
        wx.showModal({
          title: data.info,
          content: '你在'+data.data.year+data.data.quarter+'已修过'+data.data.courseName,
          showCancel: false
        })
      }else if (data.code == 700) {
        wx.showModal({
          title: '选课失败',
          content: data.info,
          showCancel: false
        })
      } else if(data.code==0) {
        this.setData({
          show: false
        })
        wx.showToast({
          title: '已选择',
        })
        this.getSelectedCourses()
        this.updateScore()
        this.getCourses()
      }else{
        wx.showToast({
          title: '未知错误',
        })
      }
    })
  },
  //获取项目详情
  getItem() {
    HTTP.Get('applet/item/' + this.data.itemId, null).then(data => {
      this.setData({
        item: data.data
      })
      wx.setNavigationBarTitle({
        title: data.data.name
      })
    })
  },
  //获取项目课程
  getCourses() {
    wx.showLoading({
      title: '加载中',
    })
    HTTP.Post('applet/course/listByItemId', this.data.courseListParam).then(data => {
      wx.hideLoading()
      this.setData({
        courses: data.data,
        coursesTotal: data.total,
        coursePageCount: data.pageCount
      })
      if (this.data.coursesTotal <= this.data.courses.length) {
        this.setData({
          coursesAllshow: true
        })
      }
    })
  },
  //上拉触底触发获取更多课程
  getCoursesByReachBottom() {
    if (this.data.coursesAllshow) {
      return
    }
    this.setData({
      ['courseListParam.pageSize']: this.data.courseListParam.pageSize + 10
    })
    this.getCourses()
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.setData({
      itemId: options.id,
      ['courseListParam.itemId']: options.id
    })
    this.getItem()
    this.getCourses()
    this.getSelectedCourses()
    this.updateScore()
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
    this.getCoursesByReachBottom()
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  }
})