package com.cxs.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxs.entity.Repository;
import com.cxs.service.RepositoryService;
import com.cxs.mapper.RepositoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepositoryServiceImpl extends ServiceImpl<RepositoryMapper, Repository>
    implements RepositoryService{
    @Autowired
    RepositoryMapper repositoryMapper;

    @Override
    public Repository selectRepositoryByEid(Integer eid) {
        return repositoryMapper.selectRepositoryByEid(eid);
    }

    @Override
    public Integer findPushNumberByEid(Integer eid) {
        return repositoryMapper.findPushNumberByEid(eid);
    }

    @Override
    public boolean insertRepository(Repository repository) {
        return repositoryMapper.insertRepository(repository);
    }

    @Override
    public boolean updateRepositoryByRid(Repository repository) {
        return repositoryMapper.updateRepositoryByRid(repository);
    }

    @Override
    public boolean deleteRepositoryByRid(Integer rid) {
        return repositoryMapper.deleteRepositoryByRid(rid);
    }

    @Override
    public IPage<Repository> findByAllPage(IPage<Repository> page, Wrapper wrapper) {
        return repositoryMapper.findByAllPage(page,wrapper);
    }
}




