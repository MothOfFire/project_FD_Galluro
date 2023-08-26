package com.cxs.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cxs.entity.Teatree;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TeatreeMapper extends BaseMapper<Teatree> {
    @Select("select * from teatree where kid=#{kid}")
    Teatree findTeatreeByKid(@Param("kid") String kid);

    //添加
    @Insert("insert into teatree values(null,#{area},#{isdeinsectization}," +
            "#{iswater},#{isfertilize},#{isgather},#{isprune},#{isweed},#{kid},#{pid})")
    boolean insertTeaTree(Teatree teatree);

    //修改
    @Update("update teatree " +
            "set area=#{area}," +
            "isdeinsectization=#{isdeinsectization},iswater=#{iswater}," +
            "isfertilize=#{isfertilize},isgather=#{isgather}," +
            "isprune=#{isprune},isweed=#{isweed}," +
            "kid=#{kid},pid=#{pid} where tid=#{tid} ")
    boolean updateTeaTreeByTid(Teatree teatree);

    //删除
    @Delete("delete from teatree where tid=#{tid}")
    boolean deleteTeaTreeById(@Param("tid") Integer tid);

    //分页查询
    @Select("select * from teatree ${ew.customSqlSegment}")
    IPage<Teatree> findByAllPage(IPage<Teatree> page, @Param(Constants.WRAPPER) Wrapper wrapper);

}




