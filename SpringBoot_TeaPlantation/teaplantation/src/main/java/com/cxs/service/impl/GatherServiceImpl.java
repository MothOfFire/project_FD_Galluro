package com.cxs.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxs.entity.Gather;
import com.cxs.entity.Teatree;
import com.cxs.service.GatherService;
import com.cxs.mapper.GatherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author cxs.sakura
* @description 针对表【gather】的数据库操作Service实现
* @createDate 2023-04-09 11:17:21
*/
@Service
public class GatherServiceImpl extends ServiceImpl<GatherMapper, Gather>
    implements GatherService{
    @Autowired
    GatherMapper gatherMapper;

    @Override
    public List<Gather> findGatherList() {
        return gatherMapper.findGatherList();
    }

    @Override
    public boolean insertGather(Gather gather) {
        return gatherMapper.insertGather(gather);
    }

    @Override
    public boolean updateGatherById(Gather gather) {
        return gatherMapper.updateGatherById(gather);
    }

    @Override
    public boolean deleteGatherById(Integer gatherid) {
        return gatherMapper.deleteGatherById(gatherid);
    }

    @Override
    public IPage<Gather> findByAllPage(IPage<Gather> page, Wrapper wrapper) {
        return gatherMapper.findByAllPage(page,wrapper);
    }
}




