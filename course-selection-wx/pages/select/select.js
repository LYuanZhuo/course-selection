// pages/select/select.js
import HTTP from "../../utils/http.js"
Page({

  /**
   * 页面的初始数据
   */
  data: {
    items:[],
    paramList:{
      name:'',
      orderBy:'start_time',
      ascOrDesc:'desc'
    }
  },
  enterItem(e){
    const id = e.currentTarget.dataset.id
    HTTP.Get('applet/item/enterItem/'+id,null).then(data=>{
      if(data.code==0){
        wx.navigateTo({
          url: '/pages/itemDetail/itemDetail?id='+id,
        })
      }else{
        wx.showModal({
          title: data.info,
          content:'你不在该选课的年级或学院',
          showCancel:false
        })
      }
    })
  },
  keywordChange(val){
    this.setData({
      ['paramList.name']:val.detail
    })
    this.getItems()
  },
  getItems(){
    wx.showLoading({
      title: '数据加载中',
    })
    HTTP.Post('applet/item/list',this.data.paramList).then(data=>{
      wx.hideLoading()
      wx.stopPullDownRefresh()
      this.setData({
        items:data.data
      })
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getItems()
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    this.getItems()
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})