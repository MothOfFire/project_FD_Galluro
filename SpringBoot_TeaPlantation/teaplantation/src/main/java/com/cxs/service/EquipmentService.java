package com.cxs.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cxs.entity.Equipment;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;


public interface EquipmentService extends IService<Equipment> {
    Equipment SelectEquipmentByEid(@Param("eid") Integer eid);
    String findEnameByEid(int eid);
    Integer findBadNumbereByEid(int eid);
    Integer getTotal();
    boolean insertEquipment(Equipment equipment);
    boolean updateEquipmentByEid(Equipment equipment);
    boolean deleteEquipmentByEid(@Param("eid") Integer eid);
    IPage<Equipment> findByAllPage(IPage<Equipment> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
