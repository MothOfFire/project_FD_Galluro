package com.cxs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxs.entity.Traffic;
import com.cxs.service.TrafficService;
import com.cxs.mapper.TrafficMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
 public class TrafficServiceImpl extends ServiceImpl<TrafficMapper, Traffic>
    implements TrafficService{
    @Autowired
    TrafficMapper trafficMapper;

    @Override
    public List<Traffic> findAllTraffic() {
        return trafficMapper.findAllTraffic();
    }

    @Override
    public long getTotal() {
        return trafficMapper.getTotal();
    }

    @Override
    public boolean insertTraffic(Traffic traffic) {
        return trafficMapper.insertTraffic(traffic);
    }

    @Override
    public Traffic findByDate(Date date) {
        return trafficMapper.findByDate(date);
    }

    @Override
    public boolean updateTrafficByDate(Traffic traffic) {
        return trafficMapper.updateTrafficByDate(traffic);
    }

    @Override
    public boolean deleteTrafficByDate(Date date) {
        return trafficMapper.deleteTrafficByDate(date);
    }
}




