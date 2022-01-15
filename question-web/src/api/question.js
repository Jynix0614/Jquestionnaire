import http from '@/utils/http'
//获取列表
export async function getListApi(parm){
    return await http.get('/api/question/list',parm)
}

//新增问卷
export async function addApi(parm){
    return await http.post('/api/question',parm)
}

//编辑问卷
export async function editApi(parm){
    return await http.put('/api/question',parm)
}

//编辑问卷
export async function deleteApi(parm){
    return await http.delete('/api/question',parm)
}