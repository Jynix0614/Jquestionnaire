package com.xdxct.wxapi.sys_paper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xdxct.wxapi.sys_paper.mapper.SysPaperMapper;
import com.xdxct.wxapi.sys_paper.entity.SysPaper;
import com.xdxct.wxapi.sys_paper.service.SysPaperService;
import com.xdxct.wxapi.sys_paper_choice.entity.SysPaperChoice;
import com.xdxct.wxapi.sys_paper_choice.service.SysPaperChoiceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
public class SysPaperServiceImpl extends ServiceImpl<SysPaperMapper, SysPaper> implements SysPaperService {
//    @Resource
//    private SysPaperMapper sysPaperMapper;

    @Autowired
    private SysPaperChoiceService sysPaperChoiceService;

    @Override
    public List<SysPaper> getMyPaperList(Long questionId, String openid) {
        return this.baseMapper.getMyPaperList(questionId,openid);
    }
}
