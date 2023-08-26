package com.cxs.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxs.entity.Teatree;
import com.cxs.service.TeatreeService;
import com.cxs.mapper.TeatreeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TeatreeServiceImpl extends ServiceImpl<TeatreeMapper, Teatree>
    implements TeatreeService{
    @Autowired
    TeatreeMapper teatreeMapper;

    @Override
    public Teatree findTeatreeByKid(String kid) {
        return teatreeMapper.findTeatreeByKid(kid);
    }

    @Override
    public boolean insertTeaTree(Teatree teatree) {
        return teatreeMapper.insertTeaTree(teatree);
    }

    @Override
    public boolean updateTeaTreeByTid(Teatree teatree) {
        return teatreeMapper.updateTeaTreeByTid(teatree);
    }

    @Override
    public boolean deleteTeaTreeById(Integer tid) {
        return teatreeMapper.deleteTeaTreeById(tid);
    }

    @Override
    public IPage<Teatree> findByAllPage(IPage<Teatree> page, Wrapper wrapper) {
        return teatreeMapper.findByAllPage(page,wrapper);
    }
}




