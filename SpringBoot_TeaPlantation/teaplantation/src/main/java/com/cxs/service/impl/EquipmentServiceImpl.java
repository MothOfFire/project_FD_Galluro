package com.cxs.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxs.entity.Equipment;
import com.cxs.service.EquipmentService;
import com.cxs.mapper.EquipmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipmentServiceImpl extends ServiceImpl<EquipmentMapper, Equipment>
    implements EquipmentService{

    @Autowired
    EquipmentMapper equipmentMapper;

    @Override
    public Equipment SelectEquipmentByEid(Integer eid) {
        return equipmentMapper.SelectEquipmentByEid(eid);
    }

    @Override
    public String findEnameByEid(int eid) {
        return equipmentMapper.findEnameByEid(eid);
    }

    @Override
    public Integer findBadNumbereByEid(int eid) {
        return equipmentMapper.findBadNumbereByEid(eid);
    }

    @Override
    public Integer getTotal() {
        return equipmentMapper.getTotal();
    }

    @Override
    public boolean insertEquipment(Equipment equipment) {
        return equipmentMapper.insertEquipment(equipment);
    }

    @Override
    public boolean updateEquipmentByEid(Equipment equipment) {
        return equipmentMapper.updateEquipmentByEid(equipment);
    }

    @Override
    public boolean deleteEquipmentByEid(Integer eid) {
        return equipmentMapper.deleteEquipmentByEid(eid);
    }

    @Override
    public IPage<Equipment> findByAllPage(IPage<Equipment> page, Wrapper wrapper) {
        return equipmentMapper.findByAllPage(page,wrapper);
    }
}




