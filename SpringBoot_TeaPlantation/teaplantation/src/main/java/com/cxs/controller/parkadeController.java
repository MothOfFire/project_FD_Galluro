package com.cxs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxs.common.Result;
import com.cxs.common.StringUtils;
import com.cxs.entity.Parkade;
import com.cxs.entity.QueryPageParam;
import com.cxs.service.ParkadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/car")
public class parkadeController {
    @Autowired
    ParkadeService parkadeService;

    //添加parkade只有扩建停车场时使用
    @PostMapping("/addCar")
    public Result<Parkade> addCar(@RequestBody Parkade parkade){
        boolean result = parkadeService.insertCar(parkade);
        return result?Result.suc():Result.fail("添加失败!");
    }

    //修改
    @PostMapping("/updateCar/43")
    public Result<Parkade> updateVisitor(@RequestBody Parkade parkade){
        boolean result = parkadeService.updateCarByCid(parkade);
        return result?Result.suc():Result.fail("修改失败!");
    }

    //只有拆停车场时使用
    @GetMapping("/deleteCar")
    public Result<Parkade> deleteUserById(@RequestParam String cid){
        Integer id = Integer.parseInt(cid);
        boolean result = parkadeService.deleteCarById(id);
        return result?Result.suc():Result.fail("删除失败!");
    }

    //分页查询
    @PostMapping("/listAllPage")
    public Result<List<Parkade>> listAllPage(@RequestBody QueryPageParam query){
        System.out.println(query);
        HashMap param = query.getParam();
        String iscar = (String)param.get("iscar");
        String cid = (String)param.get("cid");
        Page<Parkade> page = new Page<>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        LambdaQueryWrapper<Parkade> lambdaQueryWrapper = new LambdaQueryWrapper<Parkade>();
        if(!StringUtils.isEmpty(iscar) && !"null".equals(iscar)){
            lambdaQueryWrapper.like(Parkade::getIscar,iscar);
        }
        if(!StringUtils.isEmpty(cid) && !"null".equals(cid)){
            lambdaQueryWrapper.eq(Parkade::getCid,cid);
        }
        IPage<Parkade> result = parkadeService.findByAllPage(page,lambdaQueryWrapper);
        if(result != null) {
            return Result.suc(result.getRecords(), result.getTotal());
        } else {
            return Result.fail("查询失败！");
        }
    }
}
