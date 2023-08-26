package com.cxs.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cxs.entity.Action;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

public interface ActionService extends IService<Action> {
    Action selectActionById(@Param("actorid") Integer actorid);
    boolean insertAction(Action action);
    boolean updateActionById(Action action);
    boolean deleteActionById(@Param("actorid") Integer actorid);
    IPage<Action> findByAllPage(IPage<Action> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
