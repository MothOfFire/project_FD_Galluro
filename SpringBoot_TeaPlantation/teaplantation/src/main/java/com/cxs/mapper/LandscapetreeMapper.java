package com.cxs.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cxs.entity.Landscapetree;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LandscapetreeMapper extends BaseMapper<Landscapetree> {
    //添加
    @Insert("insert into landscapetree values(null,#{number},#{isdeinsectization},#{isweed}," +
            "#{iswater},#{isfertilize},#{isprune},#{shape},#{kid},#{florescence},#{pid})")
    boolean insertLandscapeTree(Landscapetree landscapetree);

    //修改
    @Update("update landscapetree " +
            "set number=#{number}," +
            "isdeinsectization=#{isdeinsectization},iswater=#{iswater}," +
            "isfertilize=#{isfertilize},shape=#{shape}," +
            "isprune=#{isprune},isweed=#{isweed},florescence=#{florescence}," +
            "kid=#{kid},pid=#{pid} where lid=#{lid} ")
    boolean updateLandscapeTreeByLid(Landscapetree landscapetree);

    //删除
    @Delete("delete from landscapetree where lid=#{lid}")
    boolean deleteLandscapeTreeById(@Param("lid") Integer lid);

    //分页查询
    @Select("select * from landscapetree ${ew.customSqlSegment}")
    IPage<Landscapetree> findByAllPage(IPage<Landscapetree> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}




