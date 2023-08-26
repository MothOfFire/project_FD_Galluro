package com.cxs.service;

import com.cxs.entity.Gatherspeed;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author cxs.sakura
* @description 针对表【gatherspeed】的数据库操作Service
* @createDate 2023-04-09 12:36:12
*/
public interface GatherspeedService extends IService<Gatherspeed> {
    List<Gatherspeed> findGatherSpeedList();
    Gatherspeed findGatherSpeedById(@Param("gatherid") Integer gatherid);
    String getGatherNameById(@Param("gatherid") Integer gatherid);
    float getTotalareaById(@Param("gatherid") Integer gatherid);
    float getReadyareaById(@Param("gatherid") Integer gatherid);
    long getTotal();
    boolean insertGatherSpeed(Gatherspeed gatherspeed);
    boolean updateGatherSpeedById(Gatherspeed gatherspeed);
    boolean deleteGatherSpeedById(@Param("gatherid") Integer gatherid);
}
