package com.cxs.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cxs.entity.Landscapetree;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

public interface LandscapetreeService extends IService<Landscapetree> {

    boolean insertLandscapeTree(Landscapetree landscapetree);
    boolean updateLandscapeTreeByLid(Landscapetree landscapetree);
    boolean deleteLandscapeTreeById(@Param("lid") Integer lid);
    IPage<Landscapetree> findByAllPage(IPage<Landscapetree> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
