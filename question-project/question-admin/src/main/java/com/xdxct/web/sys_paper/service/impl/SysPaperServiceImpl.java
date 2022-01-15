package com.xdxct.web.sys_paper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xdxct.web.sys_paper.entity.SysPaper;
import com.xdxct.web.sys_paper.mapper.SysPaperMapper;
import com.xdxct.web.sys_paper.service.SysPaperService;
import com.xdxct.web.sys_paper_choice.entity.SysPaperChoice;
import com.xdxct.web.sys_paper_choice.mapper.SysPaperChoiceMapper;
import com.xdxct.web.sys_paper_choice.service.SysPaperChoiceService;
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
    @Transactional
    public void savePaper(List<SysPaper> paperList) {
        //先删除，再保存
        for (int i=0;i<paperList.size();i++){
            //判断是否是单选、多选；需要删除对应的选项数据
            if(paperList.get(i).getPaperType().equals("1") || paperList.get(i).getPaperType().equals("2")){
                //删除选项，构造删除的条件
                QueryWrapper<SysPaperChoice> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(SysPaperChoice::getPaperId,paperList.get(i).getPaperId());
                //删除选项
                sysPaperChoiceService.remove(queryWrapper);
            }
            //删除试题
//            this.baseMapper.deleteById(paperList.get(i).getPaperId());
            QueryWrapper<SysPaper> query = new QueryWrapper<>();
            query.lambda().eq(SysPaper::getQuestionId,paperList.get(i).getQuestionId());
            this.baseMapper.delete(query);
//            sysPaperMapper.deleteById(paperList.get(i).getPaperId());
        }
        //保存
        for (int k =0;k<paperList.size();k++){
            //先保存试题
            SysPaper sysPaper = new SysPaper();
            BeanUtils.copyProperties(paperList.get(k),sysPaper);
            //试题序号
            sysPaper.setPaperOrder(k+1L);
            //保存试题
            this.baseMapper.insert(sysPaper);
            //判断是否是单选、多选;需要保存选项
            if(paperList.get(k).getPaperType().equals("1") || paperList.get(k).getPaperType().equals("2")){
                //批量保存，保存之前，需要设置试题的id
                paperList.get(k).getPaperChoice().forEach(item ->{
                    //设置试题的id
                    item.setPaperId(sysPaper.getPaperId());
                });
                //保存选项
                sysPaperChoiceService.saveBatch(paperList.get(k).getPaperChoice());
            }
        }
    }

    @Override
    public List<SysPaper> getList(String questionId) {
        //构造查询条件
        QueryWrapper<SysPaper> query = new QueryWrapper<>();
        query.lambda().eq(SysPaper::getQuestionId,questionId);
        //试题列表
        List<SysPaper> paperList = this.baseMapper.selectList(query);
        //如果是单选、多选，需要查询对于的选项
       if(paperList.size()>0){
           for (int i =0;i<paperList.size();i++){
                if(paperList.get(i).getPaperType().equals("1") || paperList.get(i).getPaperType().equals("2")){
                    //构造查询选项的条件
                    QueryWrapper<SysPaperChoice> queryWrapper = new QueryWrapper<>();
                    queryWrapper.lambda().eq(SysPaperChoice::getPaperId,paperList.get(i).getPaperId());
                    //查询选项
                    List<SysPaperChoice> list = sysPaperChoiceService.list(queryWrapper);
                    //把查询到的选项设置到试题
                    paperList.get(i).setPaperChoice(list);
                }
           }
       }
        return paperList;
    }
}
