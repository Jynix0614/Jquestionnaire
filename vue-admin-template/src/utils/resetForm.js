/**
 * 清空表单
 * @param {*} formName 表单ref属性
 * @param {*} obj 表单绑定数据域
 */
export default function resetForm(formName,obj){
    //清空表单,判断是否存在
    if(this.$refs[formName]){
        this.$refs[formName].resetFields();
    }
    //清空表单数据
    Object.keys(obj).forEach(key=>{
        obj[key]='';
    })

}