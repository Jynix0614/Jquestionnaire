//引入
const http = require('../utils/http')

//获取列表
export async function getHomeListApi(parm){
  return await http.GET("/wxapi/home/getList",parm)
}

//查询问卷详情列表
export async function getDetailsApi(parm){
  return await http.GET("/wxapi/home/getDetails",parm)
}

//根据问卷id查询试题列表
export async function getPaperListApi(parm){
  return await http.GET("/wxapi/home/getPaperList",parm)
}
