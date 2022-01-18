package com.xdxct.wxapi.sys_paper.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xdxct.wxapi.sys_paper.entity.SysPaper;


import java.util.List;

public interface SysPaperService extends IService<SysPaper> {
    List<SysPaper> getMyPaperList(Long questionId,String openid);
}
