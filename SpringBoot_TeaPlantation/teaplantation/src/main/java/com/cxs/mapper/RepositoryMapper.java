package com.cxs.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cxs.entity.Equipmentitem;
import com.cxs.entity.Repository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

@Mapper
public interface RepositoryMapper extends BaseMapper<Repository> {

    //根据eid查询仓库
    @Select("select * from repository where eid=#{eid}")
    Repository selectRepositoryByEid(@Param("eid") Integer eid);

    @Select("select pushnumber from repository where eid=#{eid}")
    Integer findPushNumberByEid(@Param("eid") Integer eid);

    //添加
    @Insert("insert into repository values(null,#{rname},#{eid},#{stock},#{pushnumber},#{popnumber})")
    boolean insertRepository(Repository repository);

    //修改
    @Update("update repository " +
            "set rname=#{rname},eid=#{eid}," +
            "stock=#{stock},pushnumber=#{pushnumber},popnumber=#{popnumber} " +
            "where rid=#{rid} ")
    boolean updateRepositoryByRid(Repository repository);

    //删除
    @Delete("delete from repository where rid=#{rid}")
    boolean deleteRepositoryByRid(@Param("rid") Integer rid);

    //分页查询
    @Select("select * from repository ${ew.customSqlSegment}")
    IPage<Repository> findByAllPage(IPage<Repository> page, @Param(Constants.WRAPPER) Wrapper wrapper);

}




