package com.cxs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxs.entity.Equipment;
import com.cxs.service.EquipmentService;
import com.cxs.mapper.EquipmentMapper;
import org.springframework.stereotype.Service;

/**
* @author cxs.sakura
* @description 针对表【equipment】的数据库操作Service实现
* @createDate 2023-03-09 15:12:58
*/
@Service
public class EquipmentServiceImpl extends ServiceImpl<EquipmentMapper, Equipment>
    implements EquipmentService{

}




