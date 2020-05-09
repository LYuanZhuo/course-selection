// pages/courseDetail/courseDetail.js
import HTTP from "../../utils/http.js"
Page({

  /**
   * 页面的初始数据
   */
  data: {
    commentsTotal: 0,
    commentsAllshow: false,
    show: false,
    basecourse: {},
    basecourseId: 0,
    comments: [],
    commentListParam: {
      basecourseId: 0,
      orderBy: 'time',
      ascOrDesc: 'desc',
      pageSize: 10,
      grade:0,
    },
    addCommentParam: {
      basecourseId: 0,
      comment: '',
      anonymous: false,
      callName: '',
      score: 3,
      homework: ''
    }
  },
  addComment() {
    HTTP.Post('applet/comment', this.data.addCommentParam).then(data => {
      this.setData({
        show:false
      })
      this.firstGetComments()
      if (data.code == 0) {
        wx.showToast({
          title: '已评价',
        })
      }else{
        wx.showModal({
          title: '评价失败!',
          content: data.info,
          showCancel:false
        })
      }
    })
  },
  callNameChange({detail}){
    this.setData({
      ['addCommentParam.callName']: detail
    })
  },
  homeworkChange({detail}) {
    this.setData({
      ['addCommentParam.homework']: detail
    })
  },
  commentChange({detail}) {
    this.setData({
      ['addCommentParam.comment']: detail
    })
  },
  scoreChange({detail}){
    this.setData({
      ['addCommentParam.score']: detail
    })
  },
  anonymousChange({detail}){
    this.setData({
      ['addCommentParam.anonymous']: detail
    })
  },
  openComment(){
    this.setData({
      show:true
    })
  },
  //打开评论窗口
  openComment(e) {
    this.setData({
      show: true,
    })
  },
  //关闭评论窗口
  closeComment() {
    this.setData({
      show: false
    });
  },
  getBasecourse() {
    HTTP.Get('applet/basecourse/' + this.data.basecourseId, null).then(data => {
      this.setData({
        basecourse: data.data
      })
      wx.setNavigationBarTitle({
        title: data.data.name,
      })
    })
  },
  getCommentsByGrade(e) {
    this.setData({
      ['commentListParam.grade']: e.currentTarget.dataset.grade,
    })
    this.firstGetComments()
  },
  //上拉触底触发获取更多课程
  getCommentsByReachBottom() {
    if (this.data.commentsAllshow) {
      return
    }
    this.setData({
      ['commentListParam.pageSize']: this.data.commentListParam.pageSize + 10
    })
    this.getComments()
  },
  firstGetComments(){
    this.setData({
      pageSize: 10,
      commentsAllshow: false
    })
    this.getComments()
  },
  getComments() {
    HTTP.Post('applet/comment/listByCourse', this.data.commentListParam).then(data => {
      this.setData({
        comments: data.data,
        commentsTotal: data.total
      })
      if (this.data.commentsTotal <= this.data.comments.length) {
        this.setData({
          commentsAllshow: true
        })
      }
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.setData({
      basecourseId: options.id,
      ['commentListParam.basecourseId']: options.id,
      ['addCommentParam.basecourseId']: options.id,
    })
    this.getBasecourse()
    this.getComments()
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
    this.getCommentsByReachBottom()
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  }
})