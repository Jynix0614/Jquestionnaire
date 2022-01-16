// pages/home/home.js
import {getHomeListApi} from '../../api/home'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    total:0,//总条数
    tableList:[],//列表数据
    parms: {
      currentPage: 1,
      pageSize: 20,
    },
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log('执行onLoad')
    this.getHomeList()
    //数据加载完后，停止下拉刷新
    wx.stopPullDownRefresh({
      success: (res) => {},
    })
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
    console.log('执行onShow')
    // //清空数据,响应式赋值，页面才会跟着变化
    // this.setData({
    //   tableList:[],
    //   total:0,
    // })
    // //调用列表
    // this.getHomeList()
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
    console.log('下拉刷新')
    //从第一页开始加载，把列表里面的数据清空
    this.data.parms.currentPage = 1
    this.setData({
      tableList:[]
    })
    //重新加载
    this.onLoad()
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    console.log('上拉触底')
    //判断是否还有下一页，若有，继续加载数据；若无，停止加载并提示信息
    let that = this
    // 计算总页数
    let totalPage = Math.ceil(that.data.total / that.data.parms.pageSize)
    if(that.data.parms.currentPage >= totalPage){
      //没有下一页了
      wx.showToast({
        title: '没有更多数据了',
      })
    }else{
      that.setData({
        currentPage:that.data.parms.currentPage++
      })
      that.onLoad()
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },

  /**
   * 获取列表
   */
  async getHomeList(){
    let that = this
    let res = await getHomeListApi(that.data.parms)
    if(res && res.code == 200){
      console.log("getHomeListApi",res)
      //普通获取（不推荐）数据非响应式获取，data变化，但页面不会变化
      // that.data.tableList = res.data.records
      // that.data.total = res.data.total
      // console.log(that.data)

      //响应式获取
      that.setData({
        total:res.data.total,
        tableList: that.data.tableList.concat(res.data.records)//原数据合并
      })
      console.log(that.data)
    }
  },

  gotoDetail:function(e){
    console.log(e)
    wx.navigateTo({
      url: '../detail/detail?questionId=' + e.currentTarget.dataset.questionid,
    })
  }
})