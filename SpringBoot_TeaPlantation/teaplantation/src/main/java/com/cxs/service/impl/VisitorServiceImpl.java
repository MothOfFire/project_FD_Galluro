package com.cxs.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxs.entity.Visitor;
import com.cxs.service.VisitorService;
import com.cxs.mapper.VisitorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class VisitorServiceImpl extends ServiceImpl<VisitorMapper, Visitor> implements VisitorService{
    @Autowired
    VisitorMapper visitorMapper;

    @Override
    public boolean insertVisitor(Visitor visitor) {
        //判断visitor的名称是否为空
        if (visitor.getVname() == null || visitor.getVname() == "") {
            return false;
        }
        //判断visitor的年龄是否为空
        if (visitor.getVage() == null) {
            return false;
        }
        //判断visitor的电话是否为空
        if (visitor.getVphone() == null || visitor.getVphone() == "") {
            return false;
        }
        return visitorMapper.insertVisitor(visitor);
    }

    @Override
    public List<Visitor> findAllVisitor() {
        return visitorMapper.findAllVisitor();
    }

    @Override
    public long getTotal() {
        return visitorMapper.getTotal();
    }

    @Override
    public Integer getStatusTotal(String str, String date) {
        return visitorMapper.getStatusTotal(str, date);
    }

    @Override
    public IPage<Visitor> findByAllPage(IPage<Visitor> page, Wrapper wrapper) {
        return visitorMapper.findByAllPage(page,wrapper);
    }

    @Override
    public boolean deleteUserById(int vid) {
        return visitorMapper.deleteUserById(vid);
    }

    @Override
    public boolean updateVisitorByVid(Visitor visitor) {
        return visitorMapper.updateVisitorByVid(visitor);
    }

    @Override
    public boolean outVisitorByVid(Visitor visitor) {
        return visitorMapper.outVisitorByVid(visitor);
    }
}




