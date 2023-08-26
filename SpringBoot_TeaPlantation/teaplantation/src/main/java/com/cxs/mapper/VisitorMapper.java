package com.cxs.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cxs.entity.User;
import com.cxs.entity.Visitor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface VisitorMapper extends BaseMapper<Visitor> {

    @Insert("insert into visitor values(null,#{vname},#{vsex},#{vage},#{vphone},#{cid},#{entertime},#{outtime},#{status})")
    boolean insertVisitor(Visitor visitor);

    @Select("select * from visitor")
    List<Visitor> findAllVisitor();

    @Select("select count(*) from visitor")
    long getTotal();

    @Select("select count(*) from visitor where status = #{str} " +
            "and outtime like concat('%',#{date}, '%')")
    Integer getStatusTotal(String str, String date);

    //分页查询1
    @Select("select * from visitor ${ew.customSqlSegment}")
    IPage<Visitor> findByAllPage(IPage<Visitor> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    //分页查询2
    @Select("select * from visitor where vname like concat('%',#{vname},'%')" +
            " limit #{pageNum},#{pageSize} ")
    List<User> findByPage(Map<String,Object> condition);

    //分页总条目
    @Select("select count(*) from visitor where vid = #{vid} " +
            "and vname like concat('%',#{vname},'%')")
    long getCount(Map<String,Object> condition);

    //删除
    @Delete("delete from visitor where vid=#{vid}")
    boolean deleteUserById(int vid);

    //修改
    @Update("update visitor " +
            "set vname=#{vname}," +
            "vsex=#{vsex},vage=#{vage}," +
            "cid=#{cid},vphone=#{vphone}," +
            "entertime=#{entertime},status=#{status} where vid=#{vid} ")
    boolean updateVisitorByVid(Visitor visitor);

    @Update("update visitor set outtime=#{outtime},status=#{status}  where vid=#{vid} ")
    boolean outVisitorByVid(Visitor visitor);
}




