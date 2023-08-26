package com.cxs.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cxs.entity.Teaprocess;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
* @author cxs.sakura
* @description 针对表【teaprocess】的数据库操作Mapper
* @Entity com.cxs.entity.Teaprocess
*/
@Mapper
public interface TeaprocessMapper extends BaseMapper<Teaprocess> {

    //根据makekind查询工序
    @Select("select * from teaprocess where makekind = #{makekind} order by ordernumber ASC")
    List<Teaprocess> selectProcessByMakeKind(@Param("makekind") String makekind);

    @Select("select * from teaprocess where workid=#{workid}")
    Teaprocess findProcessById(@Param("workid") Integer workid);

    @Select("select count(*) from teaprocess where makekind = #{makekind}")
    long getProcessTotalByMakeKind(@Param("makekind") String makekind);

    //添加
    @Insert("insert into teaprocess values(null,#{workname}," +
            "#{makekind},#{speed},#{number},#{ordernumber},#{isbegin},#{isend})")
    boolean insertTeaProcess(Teaprocess teaprocess);

    //修改
    @Update("update teaprocess " +
            "set workname=#{workname}," +
            "makekind=#{makekind},ordernumber=#{ordernumber}, " +
            "speed=#{speed},number=#{number}," +
            "isbegin=#{isbegin},isend=#{isend}" +
            " where workid=#{workid} ")
    boolean updateTeaProcessById(Teaprocess teaprocess);

    //删除
    @Delete("delete from teaprocess where workid=#{workid}")
    boolean deleteTeaProcessById(@Param("workid") Integer workid);

    //分页查询
    @Select("select * from teaprocess ${ew.customSqlSegment}")
    IPage<Teaprocess> findByAllPage(IPage<Teaprocess> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}




