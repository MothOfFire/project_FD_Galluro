package com.cxs.mapper;

import com.cxs.entity.Traffic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface TrafficMapper extends BaseMapper<Traffic> {

    @Select("select * from traffic order by date ASC")
    List<Traffic> findAllTraffic();

    @Select("select * from traffic where date=#{date}")
    Traffic findByDate(Date date);

    @Select("select count(*) from traffic")
    long getTotal();

    @Insert("insert into traffic values(#{daynumber},#{date},#{mothnumber},#{yearnumber})")
    boolean insertTraffic(Traffic traffic);

    @Update("update traffic " +
            "set daynumber=#{daynumber}," +
            "mothnumber=#{mothnumber}," +
            "yearnumber=#{yearnumber} where date=#{date} ")
    boolean updateTrafficByDate(Traffic traffic);

    @Delete("delete from traffic where date=#{date}")
    boolean deleteTrafficByDate(Date date);
}




