package com.cxs.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cxs.entity.Teapark;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.Map;

@Mapper
public interface TeaparkMapper extends BaseMapper<Teapark> {

    @Insert("insert into teapark values(null,#{pname},#{area},#{function},#{aid})")
    boolean insertPark(Teapark teapark);

    @Update("update teapark " +
            "set pname=#{pname}," +
            "area=#{area},aid=#{aid}," +
            "function=#{function} where pid=#{pid} ")
    boolean updateParkByPid(Teapark teapark);

    @Select("select pname from teapark where pid=#{pid}")
    String findPnameByPid(int pid);

    @Delete("delete from teapark where pid=#{pid}")
    boolean deleteParkById(int pid);

    @Select("select * from teapark ${ew.customSqlSegment}")
    IPage<Teapark> findByAllPage(IPage<Teapark> page,@Param(Constants.WRAPPER) Wrapper wrapper);

    @Select("select count(*) from teapark")
    Integer getTotal();
}




