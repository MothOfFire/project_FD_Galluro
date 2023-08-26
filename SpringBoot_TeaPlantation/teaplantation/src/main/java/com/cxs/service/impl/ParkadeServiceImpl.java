package com.cxs.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxs.entity.Parkade;
import com.cxs.service.ParkadeService;
import com.cxs.mapper.ParkadeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ParkadeServiceImpl extends ServiceImpl<ParkadeMapper, Parkade>
    implements ParkadeService{

    @Autowired
    ParkadeMapper parkadeMapper;

    @Override
    public boolean updateCarByCid(Parkade parkade) {
        return parkadeMapper.updateCarByCid(parkade);
    }

    @Override
    public boolean deleteCarById(int cid) {
        return parkadeMapper.deleteCarById(cid);
    }

    @Override
    public IPage<Parkade> findByAllPage(IPage<Parkade> page, Wrapper wrapper) {
        return parkadeMapper.findByAllPage(page, wrapper);
    }

    @Override
    public boolean insertCar(Parkade parkade) {
        return parkadeMapper.insertCar(parkade);
    }
}




