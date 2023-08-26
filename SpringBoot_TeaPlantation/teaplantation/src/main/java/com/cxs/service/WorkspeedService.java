package com.cxs.service;

import com.cxs.entity.Workspeed;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
* @author cxs.sakura
* @description 针对表【workspeed】的数据库操作Service
*/
public interface WorkspeedService extends IService<Workspeed> {
    Workspeed findWorkSpeedBtId(@Param("workid") Integer workid);
    boolean insertWorkSpeed(Workspeed workspeed);
    boolean updateWorkSpeedById(Workspeed workspeed);
    boolean deleteWorkSpeedById(@Param("workid") Integer workid);
}
