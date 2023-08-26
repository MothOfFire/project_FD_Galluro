package com.cxs.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cxs.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author cxs.sakura
* @description 针对表【goods】的数据库操作Service
* @createDate 2023-04-07 14:44:14
*/
public interface GoodsService extends IService<Goods> {
    List<Goods> selectGoodsByLikes();
    long getGoodsTotal();
    Integer findStockById(@Param("gid") Integer gid);
    String findGnameById(@Param("gid") Integer gid);
    Goods findGoodByGid(@Param("gid") Integer gid);
    boolean insertGoods(Goods goods);
    boolean updateGoodsById(Goods goods);
    boolean deleteGoodsById(@Param("gid") Integer gid);
    IPage<Goods> findByAllPage(IPage<Goods> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
