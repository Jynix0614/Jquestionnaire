/**
 * 对象快速复制,obj1到obj2
 * @param {*} obj1 
 * @param {*} obj2 
 */
export default async function objCoppy(obj1,obj2){
    Object.keys(obj2).forEach(key=>{
        obj2[key] = obj1[key]
    })
}