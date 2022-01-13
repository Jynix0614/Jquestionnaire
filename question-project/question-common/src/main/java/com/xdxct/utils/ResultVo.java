package com.xdxct.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 姜瑜欣
 * @description: 封装返回值
 * @date 2022/1/13 14:22
 */
@Data
@AllArgsConstructor
public class ResultVo<T> {
    private String msg;
    private int code;
    private T data;
}
