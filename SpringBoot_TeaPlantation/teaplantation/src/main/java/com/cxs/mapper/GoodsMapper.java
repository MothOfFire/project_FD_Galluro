package com.cxs.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cxs.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    //根据likes降序顺序查找
    @Select("select * from goods order by likes DESC")
    List<Goods> selectGoodsByLikes();

    @Select("select count(*) from goods")
    long getGoodsTotal();

    @Select("select stock from goods where gid=#{gid}")
    Integer findStockById(@Param("gid") Integer gid);

    @Select("select gname from goods where gid=#{gid}")
    String findGnameById(@Param("gid") Integer gid);

    //添加
    @Insert("insert into goods values(null,#{gname},#{price}," +
            "#{packtype},#{stock},#{picture},#{characterize},#{likes})")
    boolean insertGoods(Goods goods);

    @Select("select * from goods where gid=#{gid}")
    Goods findGoodByGid(@Param("gid") Integer gid);

    //修改
    @Update("update goods " +
            "set gname=#{gname}," +
            "price=#{price},packtype=#{packtype}," +
            "picture=#{picture},characterize=#{characterize},likes=#{likes} " +
            "where gid=#{gid} ")
    boolean updateGoodsById(Goods goods);

    //删除
    @Delete("delete from goods where gid=#{gid}")
    boolean deleteGoodsById(@Param("gid") Integer gid);

    //分页查询
    @Select("select * from goods ${ew.customSqlSegment}")
    IPage<Goods> findByAllPage(IPage<Goods> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}




