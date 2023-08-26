package com.cxs.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxs.entity.Teapark;
import com.cxs.service.TeaparkService;
import com.cxs.mapper.TeaparkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TeaparkServiceImpl extends ServiceImpl<TeaparkMapper, Teapark>
    implements TeaparkService{

    @Autowired
    TeaparkMapper teaparkMapper;

    @Override
    public boolean insertPark(Teapark teapark) {
        return teaparkMapper.insertPark(teapark);
    }

    @Override
    public boolean updateParkByPid(Teapark teapark) {
        return teaparkMapper.updateParkByPid(teapark);
    }

    @Override
    public String findPnameByPid(int pid) {
        return teaparkMapper.findPnameByPid(pid);
    }

    @Override
    public Integer getTotal() {
        return teaparkMapper.getTotal();
    }

    @Override
    public boolean deleteParkById(int pid) {
        return teaparkMapper.deleteParkById(pid);
    }

    @Override
    public IPage<Teapark> findByAllPage(IPage<Teapark> page, Wrapper wrapper) {
        return teaparkMapper.findByAllPage(page, wrapper);
    }
}




