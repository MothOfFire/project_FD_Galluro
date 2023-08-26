package com.cxs.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cxs.entity.Repository;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

public interface RepositoryService extends IService<Repository> {
    Repository selectRepositoryByEid(@Param("eid") Integer eid);
    Integer findPushNumberByEid(@Param("eid") Integer eid);
    boolean insertRepository(Repository repository);
    boolean updateRepositoryByRid(Repository repository);
    boolean deleteRepositoryByRid(@Param("rid") Integer rid);
    IPage<Repository> findByAllPage(IPage<Repository> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
