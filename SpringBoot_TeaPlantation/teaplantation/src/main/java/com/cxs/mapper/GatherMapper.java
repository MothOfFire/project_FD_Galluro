package com.cxs.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cxs.entity.Gather;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
* @author cxs.sakura
* @description 针对表【gather】的数据库操作Mapper
* @createDate 2023-04-09 11:17:21
* @Entity com.cxs.entity.Gather
*/
@Mapper
public interface GatherMapper extends BaseMapper<Gather> {

    @Select("select * from gather")
    List<Gather> findGatherList();

    //添加
    @Insert("insert into gather values(null,#{gathertime},#{status}," +
            "#{makekind},#{speed},#{number},#{isbegin},#{isend},#{kind})")
    boolean insertGather(Gather gather);

    //修改
    @Update("update gather " +
            "set gathertime=#{gathertime}," +
            "status=#{status},makekind=#{makekind}," +
            "speed=#{speed},number=#{number}," +
            "isbegin=#{isbegin},isend=#{isend}," +
            "kind=#{kind} where gatherid=#{gatherid} ")
    boolean updateGatherById(Gather gather);

    //删除
    @Delete("delete from gather where gatherid=#{gatherid}")
    boolean deleteGatherById(@Param("gatherid") Integer gatherid);

    //分页查询
    @Select("select * from gather ${ew.customSqlSegment}")
    IPage<Gather> findByAllPage(IPage<Gather> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}




