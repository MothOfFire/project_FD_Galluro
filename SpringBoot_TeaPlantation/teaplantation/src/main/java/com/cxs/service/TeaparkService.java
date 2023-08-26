package com.cxs.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxs.entity.Teapark;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;


public interface TeaparkService extends IService<Teapark> {

    boolean insertPark(Teapark teapark);

    boolean updateParkByPid(Teapark teapark);

    String findPnameByPid(int pid);

    Integer getTotal();

    boolean deleteParkById(int pid);

    IPage<Teapark> findByAllPage(IPage<Teapark> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
