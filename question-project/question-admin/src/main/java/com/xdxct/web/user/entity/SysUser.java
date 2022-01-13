package com.xdxct.web.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 姜瑜欣
 * @description: 用户实体
 * @date 2022/1/1314:36
 */
@Data
@TableName("sys_user") //对应表名
public class SysUser implements Serializable {
    //主键,自增
    @TableId(type = IdType.AUTO)
    private Long userId;
    //账户
    private String username;
    //密码
    private String password;
    //电话
    private String phone;
    //邮箱
    private String email;
}
