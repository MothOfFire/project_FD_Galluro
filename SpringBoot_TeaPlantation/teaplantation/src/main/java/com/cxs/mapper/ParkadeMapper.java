package com.cxs.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cxs.entity.Parkade;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ParkadeMapper extends BaseMapper<Parkade> {

    //查看空车位的数量
    @Select("select count(*) from parkade where iscar='false'")
    long getEmptySpace();

    @Select("select count(*) from parkade")
    long getTotal();

    //修改
    @Update("update parkade set vid=#{vid}," +
            "iscar=#{iscar},carnumber=#{carnumber}," +
            "ptime=#{ptime},aid=#{aid} where cid=#{cid} ")
    boolean updateCarByCid(Parkade parkade);

    //删除只有拆停车场时才使用
    @Delete("delete from parkade where cid=#{cid}")
    boolean deleteCarById(int cid);

    //分页查询
    @Select("select * from parkade ${ew.customSqlSegment}")
    IPage<Parkade> findByAllPage(IPage<Parkade> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    //添加
    @Insert("insert into parkade values(null,#{iscar},#{aid},#{vid},#{ptime},#{carnumber})")
    boolean insertCar(Parkade parkade);
}




