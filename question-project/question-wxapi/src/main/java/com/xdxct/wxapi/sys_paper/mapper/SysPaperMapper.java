package com.xdxct.wxapi.sys_paper.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xdxct.wxapi.sys_paper.entity.SysPaper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysPaperMapper extends BaseMapper<SysPaper> {

    //查询已经作答的试卷内容，数据回显用
    List<SysPaper> getMyPaperList(@Param("questionId") Long questionId, @Param("openid") String openid);
}
