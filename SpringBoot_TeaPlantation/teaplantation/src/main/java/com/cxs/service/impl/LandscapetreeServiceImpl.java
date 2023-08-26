package com.cxs.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxs.entity.Landscapetree;
import com.cxs.service.LandscapetreeService;
import com.cxs.mapper.LandscapetreeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LandscapetreeServiceImpl extends ServiceImpl<LandscapetreeMapper, Landscapetree>
    implements LandscapetreeService{
    @Autowired
    LandscapetreeMapper landscapetreeMapper;

    @Override
    public boolean insertLandscapeTree(Landscapetree landscapetree) {
        return landscapetreeMapper.insertLandscapeTree(landscapetree);
    }

    @Override
    public boolean updateLandscapeTreeByLid(Landscapetree landscapetree) {
        return landscapetreeMapper.updateLandscapeTreeByLid(landscapetree);
    }

    @Override
    public boolean deleteLandscapeTreeById(Integer lid) {
        return landscapetreeMapper.deleteLandscapeTreeById(lid);
    }

    @Override
    public IPage<Landscapetree> findByAllPage(IPage<Landscapetree> page, Wrapper wrapper) {
        return landscapetreeMapper.findByAllPage(page,wrapper);
    }
}




