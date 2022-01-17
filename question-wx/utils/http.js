//获取应用实例
const app = getApp()

//请求数据
const requsetData = (url, method, parm) => {
  return new Promise(function (resolve, reject) {
    //获取token
    let token = app.globalData.token
    if (!token) { //若全局取不到token
      token = wx.getStorageSync('token') ? wx.getStorageSync('token') : ''
    }
    wx.request({
      url: app.globalData.url + url, //请求接口地址
      data: parm,
      method:method,
      header: {
        'content-type': 'application/json', //JSON格式传输
        'token': token
      },
      success(res) {
        console.log("success res",res)
        if (res.statusCode == 200) {
          resolve(res.data)
        } else {
          reject(res)
        }
      },
      fail: function (e) {
        console.log("e",e)
        e.errMsg = '网络请求失败！'
        reject(e)
      }
    })
  })
}

//GET请求(地址，参数)
function getData(url, parm) {
  return requsetData(url, 'GET', parm)
}

//POST请求(地址，参数)
function postData(url, parm) {
  return requsetData(url, 'POST', parm)
}

//导出
module.exports = {
  GET:getData,
  POST:postData
}

