package com.cxs.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cxs.entity.Equipmentitem;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

public interface EquipmentitemService extends IService<Equipmentitem> {
    Equipmentitem SelectEquipmentItemByIno(@Param("ino") Integer ino);
    Integer findNUmberByIEid(@Param("eid") Integer eid);
    boolean insertEquipmentItem(Equipmentitem equipmentitem);
    boolean updateEquipmentItemByIno(Equipmentitem equipmentitem);
    boolean deleteEquipmentItemByIno(@Param("ino") Integer ino);
    IPage<Equipmentitem> findByAllPage(IPage<Equipmentitem> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
