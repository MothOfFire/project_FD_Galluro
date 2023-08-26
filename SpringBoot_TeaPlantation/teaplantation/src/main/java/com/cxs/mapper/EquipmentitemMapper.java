package com.cxs.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cxs.entity.Equipmentitem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxs.entity.Teatree;
import org.apache.ibatis.annotations.*;

@Mapper
public interface EquipmentitemMapper extends BaseMapper<Equipmentitem> {
    //根据eid查询设备项目
    @Select("select * from equipmentitem where ino=#{ino}")
    Equipmentitem SelectEquipmentItemByIno(@Param("ino") Integer ino);

    //根据eid查询的总个数
    @Select("select count(*) from equipmentitem where eid=#{eid}")
    Integer findNUmberByIEid(@Param("eid") Integer eid);

    //添加
    @Insert("insert into equipmentitem values(null,#{eid},#{isbad},#{pid},#{ename})")
    boolean insertEquipmentItem(Equipmentitem equipmentitem);

    //修改
    @Update("update equipmentitem " +
            "set eid=#{eid}," +
            "isbad=#{isbad}," +
            "pid=#{pid},ename=#{ename} where ino=#{ino} ")
    boolean updateEquipmentItemByIno(Equipmentitem equipmentitem);

    //删除
    @Delete("delete from equipmentitem where ino=#{ino}")
    boolean deleteEquipmentItemByIno(@Param("ino") Integer ino);

    //分页查询
    @Select("select * from equipmentitem ${ew.customSqlSegment}")
    IPage<Equipmentitem> findByAllPage(IPage<Equipmentitem> page, @Param(Constants.WRAPPER) Wrapper wrapper);

}




