package com.xdxct.web.sys_paper.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xdxct.web.sys_paper.entity.SysPaper;

import java.util.List;

public interface SysPaperService extends IService<SysPaper> {
    //保存试题
    void savePaper(List<SysPaper> paperList);
    //试题回显
    List<SysPaper> getList(String questionId);
}
