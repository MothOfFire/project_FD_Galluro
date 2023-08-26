package com.cxs.service;

import com.cxs.entity.Traffic;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cxs.entity.User;

import java.util.Date;
import java.util.List;


public interface TrafficService extends IService<Traffic> {

    List<Traffic> findAllTraffic();
    long getTotal();
    boolean insertTraffic(Traffic traffic);
    Traffic findByDate(Date date);
    boolean updateTrafficByDate(Traffic traffic);
    boolean deleteTrafficByDate(Date date);
}
