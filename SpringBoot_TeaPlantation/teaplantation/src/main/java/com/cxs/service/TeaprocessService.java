package com.cxs.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cxs.entity.Teaprocess;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author cxs.sakura
* @description 针对表【teaprocess】的数据库操作Service
* @createDate 2023-04-09 11:17:32
*/
public interface TeaprocessService extends IService<Teaprocess> {
    List<Teaprocess> selectProcessByMakeKind(@Param("makekind") String makekind);
    Teaprocess findProcessById(@Param("workid") Integer workid);
    long getProcessTotalByMakeKind(@Param("makekind") String makekind);
    boolean insertTeaProcess(Teaprocess teaprocess);
    boolean updateTeaProcessById(Teaprocess teaprocess);
    boolean deleteTeaProcessById(@Param("workid") Integer workid);
    IPage<Teaprocess> findByAllPage(IPage<Teaprocess> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
