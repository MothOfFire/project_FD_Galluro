package com.cxs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxs.common.Result;
import com.cxs.common.StringUtils;
import com.cxs.entity.Action;
import com.cxs.entity.QueryPageParam;
import com.cxs.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/action")
public class actionController {
    @Autowired
    ActionService actionService;

    //添加行为
    @PostMapping("/addAction")
    public Result<Action> addAction(@RequestBody Action action){
        if (actionService.insertAction(action)) {
            return Result.suc();
        } else {
            return Result.fail("添加失败！");
        }
    }

    //修改行为
    @PostMapping("/updateAction")
    public Result<Action> updateAction(@RequestBody Action action){
        Action action1 = actionService.selectActionById(action.getActorid());
        action1.setStatus(action.getStatus());
        if (actionService.updateActionById(action1)) {
            return Result.suc();
        } else {
            return Result.fail("修改失败");
        }
    }

    //删除行为
    @GetMapping("/deleteAction")
    public Result<Action> deleteActionById(@RequestParam String actorid){
        Integer id = Integer.parseInt(actorid);
        if (actionService.deleteActionById(id)) {
            return Result.suc();
        } else {
            return Result.fail("删除失败!");
        }
    }

    //分页查询
    @PostMapping("/listAllActionPage")
    public Result<List<Action>> listAllPage(@RequestBody QueryPageParam query){
        System.out.println(query);
        HashMap param = query.getParam();
        String actorid = (String)param.get("actorid");
        String status = (String)param.get("status");
        Page<Action> page = new Page<>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        LambdaQueryWrapper<Action> lambdaQueryWrapper = new LambdaQueryWrapper<Action>();
        if(!StringUtils.isEmpty(actorid) && !"null".equals(actorid)){
            lambdaQueryWrapper.like(Action::getActorid,actorid);
        }
        if(!StringUtils.isEmpty(status) && !"null".equals(status)){
            lambdaQueryWrapper.eq(Action::getStatus,status);
        }
        IPage<Action> result = actionService.findByAllPage(page,lambdaQueryWrapper);
        if(result != null) {
            return Result.suc(result.getRecords(), result.getTotal());
        } else {
            return Result.fail("查询失败！");
        }
    }
}
