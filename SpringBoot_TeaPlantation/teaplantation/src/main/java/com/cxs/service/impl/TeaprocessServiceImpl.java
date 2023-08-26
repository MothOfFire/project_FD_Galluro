package com.cxs.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxs.entity.Teaprocess;
import com.cxs.service.TeaprocessService;
import com.cxs.mapper.TeaprocessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author cxs.sakura
* @description 针对表【teaprocess】的数据库操作Service实现
* @createDate 2023-04-09 11:17:32
*/
@Service
public class TeaprocessServiceImpl extends ServiceImpl<TeaprocessMapper, Teaprocess>
    implements TeaprocessService{

    @Autowired
    TeaprocessMapper teaprocessMapper;

    @Override
    public List<Teaprocess> selectProcessByMakeKind(String makekind) {
        return teaprocessMapper.selectProcessByMakeKind(makekind);
    }

    @Override
    public Teaprocess findProcessById(Integer workid) {
        return teaprocessMapper.findProcessById(workid);
    }

    @Override
    public long getProcessTotalByMakeKind(String makekind) {
        return teaprocessMapper.getProcessTotalByMakeKind(makekind);
    }

    @Override
    public boolean insertTeaProcess(Teaprocess teaprocess) {
        return teaprocessMapper.insertTeaProcess(teaprocess);
    }

    @Override
    public boolean updateTeaProcessById(Teaprocess teaprocess) {
        return teaprocessMapper.updateTeaProcessById(teaprocess);
    }

    @Override
    public boolean deleteTeaProcessById(Integer workid) {
        return teaprocessMapper.deleteTeaProcessById(workid);
    }

    @Override
    public IPage<Teaprocess> findByAllPage(IPage<Teaprocess> page, Wrapper wrapper) {
        return teaprocessMapper.findByAllPage(page,wrapper);
    }
}




