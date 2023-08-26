package com.cxs.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxs.entity.Visitor;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

public interface VisitorService extends IService<Visitor> {

    boolean insertVisitor(Visitor visitor);

    List<Visitor> findAllVisitor();

    long getTotal();

    Integer getStatusTotal(String str, String date);

    IPage<Visitor> findByAllPage(IPage<Visitor> page, Wrapper wrapper);

    boolean deleteUserById(int vid);

    boolean updateVisitorByVid(Visitor visitor);

    boolean outVisitorByVid(Visitor visitor);
}
