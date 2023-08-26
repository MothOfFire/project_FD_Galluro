package com.cxs.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cxs.entity.Teatree;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

public interface TeatreeService extends IService<Teatree> {

    Teatree findTeatreeByKid(@Param("kid") String kid);
    boolean insertTeaTree(Teatree teatree);
    boolean updateTeaTreeByTid(Teatree teatree);
    boolean deleteTeaTreeById(@Param("tid") Integer tid);
    IPage<Teatree> findByAllPage(IPage<Teatree> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
