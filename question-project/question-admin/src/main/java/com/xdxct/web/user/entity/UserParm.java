package com.xdxct.web.user.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 姜瑜欣
 * @description: 分页逻辑
 * @date 2022/1/1315:08
 */
@Data
public class UserParm implements Serializable {
    //当前页
    private Long currentPage;
    //页容量
    private Long pageSize;
}
