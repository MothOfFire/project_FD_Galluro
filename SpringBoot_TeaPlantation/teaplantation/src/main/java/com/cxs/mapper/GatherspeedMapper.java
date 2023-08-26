package com.cxs.mapper;

import com.cxs.entity.Gatherspeed;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
* @author cxs.sakura
* @description 针对表【gatherspeed】的数据库操作Mapper
* @Entity com.cxs.entity.Gatherspeed
*/
@Mapper
public interface GatherspeedMapper extends BaseMapper<Gatherspeed> {

    @Select("select * from gatherspeed")
    List<Gatherspeed> findGatherSpeedList();

    @Select("select * from gatherspeed where gatherid=#{gatherid}")
    Gatherspeed findGatherSpeedById(@Param("gatherid") Integer gatherid);

    @Select("select gathername from gatherspeed where gatherid=#{gatherid}")
    String getGatherNameById(@Param("gatherid") Integer gatherid);

    @Select("select totalarea from gatherspeed where gatherid=#{gatherid}")
    float getTotalareaById(@Param("gatherid") Integer gatherid);

    @Select("select readyarea from gatherspeed where gatherid=#{gatherid}")
    float getReadyareaById(@Param("gatherid") Integer gatherid);

    @Select("select count(*) from gatherspeed")
    long getTotal();

    //添加
    @Insert("insert into gatherspeed values(#{gaterid},#{totalarea},#{readyarea},#{speed},#{gathername})")
    boolean insertGatherSpeed(Gatherspeed gatherspeed);

    //修改
    @Update("update gatherspeed " +
            "set totalarea=#{totalarea},gathername=#{gathername}," +
            "readyarea=#{readyarea},speed=#{speed} " +
            "where gatherid=#{gatherid} ")
    boolean updateGatherSpeedById(Gatherspeed gatherspeed);

    //删除
    @Delete("delete from gatherspeed where gatherid=#{gatherid}")
    boolean deleteGatherSpeedById(@Param("gatherid") Integer gatherid);

}




