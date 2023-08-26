package com.cxs.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxs.entity.Equipmentitem;
import com.cxs.service.EquipmentitemService;
import com.cxs.mapper.EquipmentitemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipmentitemServiceImpl extends ServiceImpl<EquipmentitemMapper, Equipmentitem>
    implements EquipmentitemService{
    @Autowired
    EquipmentitemMapper equipmentitemMapper;

    @Override
    public Equipmentitem SelectEquipmentItemByIno(Integer ino) {
        return equipmentitemMapper.SelectEquipmentItemByIno(ino);
    }

    @Override
    public Integer findNUmberByIEid(Integer eid) {
        return equipmentitemMapper.findNUmberByIEid(eid);
    }

    @Override
    public boolean insertEquipmentItem(Equipmentitem equipmentitem) {
        return equipmentitemMapper.insertEquipmentItem(equipmentitem);
    }

    @Override
    public boolean updateEquipmentItemByIno(Equipmentitem equipmentitem) {
        return equipmentitemMapper.updateEquipmentItemByIno(equipmentitem);
    }

    @Override
    public boolean deleteEquipmentItemByIno(Integer ino) {
        return equipmentitemMapper.deleteEquipmentItemByIno(ino);
    }

    @Override
    public IPage<Equipmentitem> findByAllPage(IPage<Equipmentitem> page, Wrapper wrapper) {
        return equipmentitemMapper.findByAllPage(page,wrapper);
    }
}




