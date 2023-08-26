package com.cxs.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cxs.entity.Equipment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

@Mapper
public interface EquipmentMapper extends BaseMapper<Equipment> {

    //根据eid查询设备
    @Select("select * from equipment where eid=#{eid}")
    Equipment SelectEquipmentByEid(@Param("eid") Integer eid);

    @Select("select count(*) from equipment")
    Integer getTotal();

    @Select("select ename from equipment where eid=#{eid}")
    String findEnameByEid(int eid);

    @Select("select badnum from equipment where eid=#{eid}")
    Integer findBadNumbereByEid(int eid);

    //添加
    @Insert("insert into equipment values(null,#{ename},#{total},#{badnum},#{aid})")
    boolean insertEquipment(Equipment equipment);

    //修改
    @Update("update equipment " +
            "set total=#{total}," +
            "badnum=#{badnum}," +
            "aid=#{aid},ename=#{ename} where eid=#{eid} ")
    boolean updateEquipmentByEid(Equipment equipment);

    //删除
    @Delete("delete from equipment where eid=#{eid}")
    boolean deleteEquipmentByEid(@Param("eid") Integer eid);

    //分页查询
    @Select("select * from equipment ${ew.customSqlSegment}")
    IPage<Equipment> findByAllPage(IPage<Equipment> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}




