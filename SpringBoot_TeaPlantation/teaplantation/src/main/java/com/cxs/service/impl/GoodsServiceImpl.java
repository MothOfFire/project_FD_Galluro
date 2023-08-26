package com.cxs.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxs.entity.Goods;
import com.cxs.service.GoodsService;
import com.cxs.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods>
    implements GoodsService{
    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public List<Goods> selectGoodsByLikes() {
        return goodsMapper.selectGoodsByLikes();
    }

    @Override
    public long getGoodsTotal() {
        return goodsMapper.getGoodsTotal();
    }

    @Override
    public Integer findStockById(Integer gid) {
        return goodsMapper.findStockById(gid);
    }

    @Override
    public String findGnameById(Integer gid) {
        return goodsMapper.findGnameById(gid);
    }

    @Override
    public Goods findGoodByGid(Integer gid) {
        return goodsMapper.findGoodByGid(gid);
    }

    @Override
    public boolean insertGoods(Goods goods) {
        return goodsMapper.insertGoods(goods);
    }

    @Override
    public boolean updateGoodsById(Goods goods) {
        return goodsMapper.updateGoodsById(goods);
    }

    @Override
    public boolean deleteGoodsById(Integer gid) {
        return goodsMapper.deleteGoodsById(gid);
    }

    @Override
    public IPage<Goods> findByAllPage(IPage<Goods> page, Wrapper wrapper) {
        return goodsMapper.findByAllPage(page,wrapper);
    }
}




