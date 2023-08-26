package com.cxs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxs.entity.Gatherspeed;
import com.cxs.service.GatherspeedService;
import com.cxs.mapper.GatherspeedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author cxs.sakura
* @description 针对表【gatherspeed】的数据库操作Service实现
*/
@Service
public class GatherspeedServiceImpl extends ServiceImpl<GatherspeedMapper, Gatherspeed>
    implements GatherspeedService{
    @Autowired
    GatherspeedMapper gatherspeedMapper;

    @Override
    public List<Gatherspeed> findGatherSpeedList() {
        return gatherspeedMapper.findGatherSpeedList();
    }

    @Override
    public Gatherspeed findGatherSpeedById(Integer gatherid) {
        return gatherspeedMapper.findGatherSpeedById(gatherid);
    }

    @Override
    public String getGatherNameById(Integer gatherid) {
        return gatherspeedMapper.getGatherNameById(gatherid);
    }

    @Override
    public float getTotalareaById(Integer gatherid) {
        return gatherspeedMapper.getTotalareaById(gatherid);
    }

    @Override
    public float getReadyareaById(Integer gatherid) {
        return gatherspeedMapper.getReadyareaById(gatherid);
    }

    @Override
    public long getTotal() {
        return gatherspeedMapper.getTotal();
    }

    @Override
    public boolean insertGatherSpeed(Gatherspeed gatherspeed) {
        return gatherspeedMapper.insertGatherSpeed(gatherspeed);
    }

    @Override
    public boolean updateGatherSpeedById(Gatherspeed gatherspeed) {
        return gatherspeedMapper.updateGatherSpeedById(gatherspeed);
    }

    @Override
    public boolean deleteGatherSpeedById(Integer gatherid) {
        return gatherspeedMapper.deleteGatherSpeedById(gatherid);
    }
}




