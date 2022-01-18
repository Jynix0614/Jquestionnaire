import {
  getMyQuestionListApi
} from '../../api/home'
//获取应用实例
var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    total: 0,
    //获取列表参数
    parms: {
      currentPage: 1,
      pageSize: 10,
      openid:app.globalData.openid
      //openid: wx.getStorageSync('openid')
    },
    //列表数据
    tableList: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log('我的页面')
    console.log(app.globalData.openid)
  },

  //获取问卷列表
  async getQuestionList() {
    let that = this;
    console.log(this.data.total)
    this.data.parms.openid = app.globalData.openid
    // this.data.parms.openid = wx.getStorageSync('openid')
    let res = await getMyQuestionListApi(this.data.parms);
    console.log(res)
    if (res && res.code == 200) {
      //注意，赋值不能使用this.data.的方式赋值，这样没有双向数据绑定的效果，虽然有数据，但是页面不会改变
      that.setData({
        tableList: that.data.tableList.concat(res.data.records),
        total: res.data.total
      })
      // that.data.parms.total = res.data.total;
      console.log(res.data.total)
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
    this.setData({
      tableList:[],
      total:0
    })
   //获取问卷列表
   this.getQuestionList();
   //停止下拉刷新
   wx.stopPullDownRefresh(); 
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
    console.log('我的页面下拉刷新')
    this.data.parms.currentPage = 1;
    this.data.tableList = [];
    this.onLoad();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    console.log('我的页面上拉触底')
    let that = this;
    let totalPage = Math.ceil(that.data.total / that.data.parms.pageSize);
    if (that.data.parms.currentPage >= totalPage) {
      //如果data中的当前页数大于等于总页数，则没有下一页
      wx.showToast({
        title: '没有更多数据了',
      })
    } else {
      that.setData({
        currentPage: that.data.parms.currentPage++
      })
      console.log(this.data.parms)
      that.onLoad();
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },

  //跳转到详情页
  gotoDetail:function(e) {
    console.log(e.currentTarget.dataset.item)
    wx.navigateTo({
     url:'../myanswer/myanswer?questionId='+e.currentTarget.dataset.item.questionId
    })
  }
})