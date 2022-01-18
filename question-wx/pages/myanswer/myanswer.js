// pages/myanswer/myanswer.js
import { getMyPaperListShowApi} from "../../api/home.js"
const app = getApp()
// pages/answer/answer.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    questionId:'',
    paperList:[],
    // 表单提交的数据
    answer:{
      openid:'',
      questionId:'',
      paperList:[]
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log('答卷页')
    console.log(options)
    this.setData({
      questionId:options.questionId
    })
    let parm ={
      questionId:options.questionId,
      openid:app.globalData.openid
      // openid: wx.getStorageSync('openid')
    }
    this.getPaperList(parm)
  },

  async getPaperList(parm){
    let that = this
    let res = await getMyPaperListShowApi(parm)
    if(res && res.code==200){
      that.setData({
        paperList:res.data.listPaper
      })
      console.log(that.data.paperList)
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
  /**
   * 用户交互事件
   */
  selectChange(e){
    let that = this
    let obj ={
      questionId:'',
      paperId:'',
      paperType:'',
      paperValue:''
    }
    obj.questionId = that.data.questionId
    obj.paperId = e.currentTarget.dataset.paperid
    obj.paperType = e.currentTarget.dataset.papertype
    //获取选中的值
    //多选的时候需要设置为字符串
    if(e.currentTarget.dataset.papertype == '2'){
      obj.paperValue = e.detail.value.join(",")//数组转为字符串
    }else{
      obj.paperValue = e.detail.value
    }
    // 判断答卷是否存在
    let list = that.data.answer.paperList
    if(list.length > 0){
      //需要判断是否存在
      for(let i = 0;i<list.length;i++){
        if(list[i].paperId === obj.paperId && list[i].paperType === obj.paperType ){
          //删除原来的
          list.splice(i,1)
        }
      }
      //删除之后，新的存入
      list.push(obj)
    }else{
      list.push(obj)
    }
    console.log(list)
  },

  async submitBtn(e){
    this.data.answer.openid = app.globalData.openid
    this.data.answer.questionId = this.data.questionId
    console.log(this.data.answer)
    let res = await saveCommitApi(this.data.answer)
    if(res && res.code  == 200){
      console.log(res)
      wx.navigateBack({
        delta:1
      })
    }
  }
})