//引入
const http = require('../utils/http')

//获取列表
export async function getHomeListApi(parm){
  return await http.GET("/wxapi/home/getList",parm)
}
