package com.cxs.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxs.entity.Action;
import com.cxs.service.ActionService;
import com.cxs.mapper.ActionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionServiceImpl extends ServiceImpl<ActionMapper, Action>
    implements ActionService{
    @Autowired
    ActionMapper actionMapper;

    @Override
    public Action selectActionById(Integer actorid) {
        return actionMapper.selectActionById(actorid);
    }

    @Override
    public boolean insertAction(Action action) {
        return actionMapper.insertAction(action);
    }

    @Override
    public boolean updateActionById(Action action) {
        return actionMapper.updateActionById(action);
    }

    @Override
    public boolean deleteActionById(Integer actorid) {
        return actionMapper.deleteActionById(actorid);
    }

    @Override
    public IPage<Action> findByAllPage(IPage<Action> page, Wrapper wrapper) {
        return actionMapper.findByAllPage(page,wrapper);
    }
}




