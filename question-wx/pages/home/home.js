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
    //清空数据,响应式赋值，页面才会跟着变化
    this.setData({
      tableList:[],
      total:0,
    })
    //调用列表
    this.getHomeList()
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
  }
})