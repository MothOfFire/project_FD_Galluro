package com.cxs.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cxs.entity.Action;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ActionMapper extends BaseMapper<Action> {

    @Select("select * from action where actorid=#{actorid}")
    Action selectActionById(@Param("actorid") Integer actorid);

    //添加
    @Insert("insert into action values(#{actorid},#{power},#{action},#{actiontime},#{status},#{eid})")
    boolean insertAction(Action action);

    //修改
    @Update("update action " +
            "set power=#{power},action=#{action}," +
            "actiontime=#{actiontime},status=#{status},eid=#{eid} " +
            "where actorid=#{actorid} ")
    boolean updateActionById(Action action);

    //删除
    @Delete("delete from action where actorid=#{actorid}")
    boolean deleteActionById(@Param("actorid") Integer actorid);

    //分页查询
    @Select("select * from action ${ew.customSqlSegment}")
    IPage<Action> findByAllPage(IPage<Action> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}




