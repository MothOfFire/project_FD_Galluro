package com.cxs.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxs.entity.Parkade;
import com.baomidou.mybatisplus.extension.service.IService;


public interface ParkadeService extends IService<Parkade> {

    boolean updateCarByCid(Parkade parkade);

    boolean deleteCarById(int cid);

    IPage<Parkade> findByAllPage(IPage<Parkade> page, Wrapper wrapper);

    boolean insertCar(Parkade parkade);
}
