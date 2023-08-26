package com.cxs.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cxs.entity.Gather;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cxs.entity.Teatree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author cxs.sakura
* @description 针对表【gather】的数据库操作Service
* @createDate 2023-04-09 11:17:21
*/
public interface GatherService extends IService<Gather> {
    List<Gather> findGatherList();
    boolean insertGather(Gather gather);
    boolean updateGatherById(Gather gather);
    boolean deleteGatherById(@Param("gatherid") Integer gatherid);
    IPage<Gather> findByAllPage(IPage<Gather> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
