package com.xdxct.web.sys_paper_choice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xdxct.web.sys_paper_choice.entity.SysPaperChoice;
import com.xdxct.web.sys_paper_choice.mapper.SysPaperChoiceMapper;
import com.xdxct.web.sys_paper_choice.service.SysPaperChoiceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPaperChoiceServiceImpl extends ServiceImpl<SysPaperChoiceMapper, SysPaperChoice> implements SysPaperChoiceService {
    @Override
    public List<SysPaperChoice> getListPaperChoice(Long questionId) {
        return this.baseMapper.getListPaperChoice(questionId);
    }
}
