package com.cxs.mapper;

import com.cxs.entity.Workspeed;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

/**
* @author cxs.sakura
* @description 针对表【workspeed】的数据库操作Mapper
* @Entity com.cxs.entity.Workspeed
*/
@Mapper
public interface WorkspeedMapper extends BaseMapper<Workspeed> {

    @Select("select * from workspeed where workid=#{workid}")
    Workspeed findWorkSpeedBtId(@Param("workid") Integer workid);

    //添加
    @Insert("insert into workspeed values(#{workid},#{totalweight},#{readyweight},#{speed})")
    boolean insertWorkSpeed(Workspeed workspeed);

    //修改
    @Update("update workspeed " +
            "set totalweight=#{totalweight}," +
            "readyweight=#{readyweight},speed=#{speed} " +
            "where workid=#{workid} ")
    boolean updateWorkSpeedById(Workspeed workspeed);

    //删除
    @Delete("delete from workspeed where workid=#{workid}")
    boolean deleteWorkSpeedById(@Param("workid") Integer workid);
}




