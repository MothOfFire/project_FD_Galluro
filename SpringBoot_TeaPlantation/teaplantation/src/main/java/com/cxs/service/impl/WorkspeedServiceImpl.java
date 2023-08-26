package com.cxs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxs.entity.Workspeed;
import com.cxs.service.WorkspeedService;
import com.cxs.mapper.WorkspeedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author cxs.sakura
* @description 针对表【workspeed】的数据库操作Service实现
*/
@Service
public class WorkspeedServiceImpl extends ServiceImpl<WorkspeedMapper, Workspeed>
    implements WorkspeedService{
    @Autowired
    WorkspeedMapper workspeedMapper;

    @Override
    public Workspeed findWorkSpeedBtId(Integer workid) {
        return workspeedMapper.findWorkSpeedBtId(workid);
    }

    @Override
    public boolean insertWorkSpeed(Workspeed workspeed) {
        return workspeedMapper.insertWorkSpeed(workspeed);
    }

    @Override
    public boolean updateWorkSpeedById(Workspeed workspeed) {
        return workspeedMapper.updateWorkSpeedById(workspeed);
    }

    @Override
    public boolean deleteWorkSpeedById(Integer workid) {
        return workspeedMapper.deleteWorkSpeedById(workid);
    }
}




