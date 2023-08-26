package com.cxs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxs.common.Result;
import com.cxs.common.StringUtils;
import com.cxs.entity.*;
import com.cxs.service.LandscapetreeService;
import com.cxs.service.TeaparkService;
import com.cxs.service.TeatreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/teaPark")
public class teaPantationController {

    @Autowired
    TeaparkService teaparkService;
    @Autowired
    TeatreeService teatreeService;
    @Autowired
    LandscapetreeService landscapetreeService;

    //添加园区
    @PostMapping("/addPark")
    public Result<Teapark> addPark(@RequestBody Teapark teapark){
        boolean result = teaparkService.insertPark(teapark);
        return result?Result.suc():Result.fail("添加失败！");
    }

    //添加茶树
    @PostMapping("/addTeaTree")
    public Result<Teatree> addTeaTree(@RequestBody Teatree teatree){
        boolean result = teatreeService.insertTeaTree(teatree);
        return result?Result.suc():Result.fail("添加失败！");
    }

    //添加景观树
    @PostMapping("/addLandscapeTree")
    public Result<Landscapetree> addLandscapeTree(@RequestBody Landscapetree landscapetree){
        boolean result = landscapetreeService.insertLandscapeTree(landscapetree);
        return result?Result.suc():Result.fail("添加失败!");
    }

    //修改园区
    @PostMapping("/updatePark")
    public Result<Teapark> updatePark(@RequestBody Teapark teapark){
        boolean result = teaparkService.updateParkByPid(teapark);
        return result?Result.suc():Result.fail("修改失败!");
    }

    //修改茶树
    @PostMapping("/updateTeaTree")
    public Result<Teatree> updateTeaTree(@RequestBody Teatree teatree){
        boolean result = teatreeService.updateTeaTreeByTid(teatree);
        return result?Result.suc():Result.fail("修改失败!");
    }

    //修改景观树
    @PostMapping("/updateLandscapeTree")
    public Result<Landscapetree> updateLandscapeTree(@RequestBody Landscapetree landscapetree){
        boolean result = landscapetreeService.updateLandscapeTreeByLid(landscapetree);
        return result?Result.suc():Result.fail("删除失败！");
    }

    //删除园区
    @GetMapping("/deletePark")
    public Result<Teapark> deleteParkById(@RequestParam String pid){
        Integer id = Integer.parseInt(pid);
        boolean result = teaparkService.deleteParkById(id);
        return result?Result.suc():Result.fail("删除失败！");
    }

    //删除茶树
    @GetMapping("/deleteTeaTree")
    public Result<Teatree> deleteTeaTreeById(@RequestParam String tid){
        Integer id = Integer.parseInt(tid);
        boolean result = teatreeService.deleteTeaTreeById(id);
        return result?Result.suc():Result.fail("删除失败!");
    }

    //删除景观树
    @GetMapping("/deleteLandscapeTree")
    public Result<Landscapetree> deleteLandscapeTreeById(@RequestParam String lid){
        Integer id = Integer.parseInt(lid);
        boolean result = landscapetreeService.deleteLandscapeTreeById(id);
        return result?Result.suc():Result.fail("删除失败!");
    }

    //园区分页查询
    @PostMapping("/listAllTeaParkPage")
    public Result<List<Teapark>> listAllPage(@RequestBody QueryPageParam query){
        System.out.println(query);
        HashMap param = query.getParam();
        String pid = (String)param.get("pid");
        String function = (String)param.get("function");
        Page<Teapark> page = new Page<>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        LambdaQueryWrapper<Teapark> lambdaQueryWrapper = new LambdaQueryWrapper<Teapark>();
        if(!StringUtils.isEmpty(pid) && !"null".equals(pid)){
            lambdaQueryWrapper.like(Teapark::getPid,pid);
        }
        if(!StringUtils.isEmpty(function) && !"null".equals(function)){
            lambdaQueryWrapper.like(Teapark::getFunction,function);
        }
        IPage<Teapark> result = teaparkService.findByAllPage(page,lambdaQueryWrapper);
        if(result != null) {
            return Result.suc(result.getRecords(), result.getTotal());
        } else {
            return Result.fail("查询失败！");
        }
    }

    //茶树的分页查询
    @PostMapping("/listAllTeaTreePage")
    public Result<List<Teatree>> listAllTeaTreePage(@RequestBody QueryPageParam query){
        System.out.println(query);
        HashMap param = query.getParam();
        String tid = (String)param.get("tid");
        String kid = (String)param.get("kid");
        Page<Teatree> page = new Page<>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        LambdaQueryWrapper<Teatree> lambdaQueryWrapper = new LambdaQueryWrapper<Teatree>();
        if(!StringUtils.isEmpty(tid) && !"null".equals(tid)){
            lambdaQueryWrapper.like(Teatree::getTid,tid);
        }
        if(!StringUtils.isEmpty(kid) && !"null".equals(kid)){
            lambdaQueryWrapper.like(Teatree::getKid,kid);
        }
        IPage<Teatree> result = teatreeService.findByAllPage(page,lambdaQueryWrapper);
        if(result != null) {
            return Result.suc(result.getRecords(), result.getTotal());
        } else {
            return Result.fail("查询失败！");
        }
    }

    //景观树分页查询
    @PostMapping("/listAllLandscapeTreePage")
    public Result<List<Landscapetree>> listAllLandscapeTreePage(@RequestBody QueryPageParam query){
        System.out.println(query);
        HashMap param = query.getParam();
        String lid = (String)param.get("lid");
        String kid = (String)param.get("kid");
        Page<Landscapetree> page = new Page<>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        LambdaQueryWrapper<Landscapetree> lambdaQueryWrapper = new LambdaQueryWrapper<Landscapetree>();
        if(!StringUtils.isEmpty(lid) && !"null".equals(lid)){
            lambdaQueryWrapper.like(Landscapetree::getLid,lid);
        }
        if(!StringUtils.isEmpty(kid) && !"null".equals(kid)){
            lambdaQueryWrapper.like(Landscapetree::getKid,kid);
        }
        IPage<Landscapetree> result = landscapetreeService.findByAllPage(page,lambdaQueryWrapper);
        if(result != null) {
            return Result.suc(result.getRecords(), result.getTotal());
        } else {
            return Result.fail("查询失败！");
        }
    }
}
