// pages/detail/detail.js
import {
  getDetailsApi
} from '../../api/home.js'

const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    questionId: '',
    questionDesc: '',
    questionTitle: '',
    questionImg: '',
    status: ''
  },



  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log('详情页')
    console.log(options)
    let parm = {
      questionId: options.questionId,
      openId: app.globalData.openid
    }
    this.getDetails(parm)
  },

  async getDetails(parm) {
    let that = this
    let res = await getDetailsApi(parm);
    if (res && res.code == 200) {
      that.setData({
        questionId: res.data.questionId,
        questionDesc: res.data.questionDesc,
        questionTitle: res.data.questionTitle,
        questionImg: res.data.questionImg,
        status: res.data.status
      })
    }

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

  },

  gotoAnswer: function (e) {
    console.log(e)
    wx.navigateTo({
      url: '../answer/answer?questionId=' + e.currentTarget.dataset.questionid,
    })
  },

  hasAnswer(e) {
    wx.showToast({
      title: '您已答卷',
      duration: 2000
    })
  }

})