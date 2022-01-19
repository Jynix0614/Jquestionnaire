// app.js
App({
  onLaunch() {
    let that = this;
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // 登录
    wx.login({
      success: res => {
        console.log('准备登录',res)
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        wx.request({
          url: that.globalData.url + '/wxapi/login/wxLogin', //请求接口地址
          data: {
            code: res.code
          },
          header: {
            'content-type': 'application/x-www-form-urlencoded' // 默认值
          },
          success (res) {
            console.log('用户开始网络请求',res.data)
            if(res.data.code == 200){
              if(res.data && res.data.data.openid){
                that.globalData.openid = res.data.data.openid
                that.globalData.session_key = res.data.data.session_key
                wx.setStorageSync('openid', res.data.data.openid)
                console.log(that.globalData.openid,' ',that.globalData.session_key)
              }
            }
          }
        })
      }
    })
  },
  //全局数据存储
  globalData: {
    userInfo: null,
    //url:'http://localhost:8099',//本机测试
    url:'http://172.18.20.145:8099',//局域网
    //url:'http://192.168.10.42:8099',//局域网
    openid:'',
    session_key:''
  }
})
