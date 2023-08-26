package com.cxs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxs.common.DateUtils;
import com.cxs.entity.QueryPageParam;
import com.cxs.common.Result;
import com.cxs.common.StringUtils;
import com.cxs.entity.Traffic;
import com.cxs.entity.Visitor;
import com.cxs.service.ParkadeService;
import com.cxs.service.TrafficService;
import com.cxs.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/visitor")
public class visitorController {
    @Autowired
    VisitorService visitorService;
    @Autowired
    TrafficService trafficService;
    @Autowired
    ParkadeService parkadeService;

    //查询所有visitor
    @GetMapping("/listVisitor")
    public Result<List<Visitor>> findAllVisitor(){
        return Result.suc(visitorService.findAllVisitor(),visitorService.getTotal());
    }

    @GetMapping("/getNewVid")
    public Result<Integer> getVisitorTotal(){
        Integer vid = new Long(visitorService.getTotal()).intValue()  + 1;
        return Result.suc(vid);
    }

    //添加visitor
    @PostMapping("/addVisitor")
    public Result<Visitor> addVisitor(@RequestBody Visitor visitor){
        boolean result = visitorService.insertVisitor(visitor);
        return result?Result.suc():Result.fail("添加失败！");
    }

    //修改visitor
    @PostMapping("/updateVisitor")
    public Result<Visitor> updateVisitor(@RequestBody Visitor visitor){
        boolean result = visitorService.updateVisitorByVid(visitor);
        return result?Result.suc():Result.fail();
    }

    //出园
    @PostMapping("/outVisitor")
    public Result<Visitor> outVisitor(@RequestBody Visitor visitor){
        if (visitor == null) {
            return Result.fail("传回的数据为空");
        }
        System.out.println(visitor);
        DateUtils dateUtils = new DateUtils();
        //计算日人流量
        String date = dateUtils.changeDayTime(visitor.getOuttime());
        System.out.println("date:"+date);
        Integer daynumber = visitorService.getStatusTotal("1",date) + 1;
        System.out.println(daynumber);
        //计算月人流量
        String Moth = dateUtils.changeMothTime(visitor.getOuttime());
        Integer mothnumber = visitorService.getStatusTotal("1",Moth) + 1;
        //计算年人流量
        String Year = dateUtils.changeYearTime(visitor.getOuttime());
        Integer yearnumber = visitorService.getStatusTotal("1",Year) + 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date day = sdf.parse(date);
            System.out.println("day:"+day);
            //创建人流量实体类实例
            Traffic traffic = new Traffic(daynumber,day,mothnumber,yearnumber);
            //判断traffic表中是否有day日期的数据，没有则添加，有则修改
            if (trafficService.findByDate(day) == null){
                //添加进数据库
                trafficService.insertTraffic(traffic);
            } else {
                //修改
                trafficService.updateTrafficByDate(traffic);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //修改游客出园时间的信息
        boolean result = visitorService.outVisitorByVid(visitor);
        return result?Result.suc():Result.fail("修改失败!");
    }

    //删除visitor
    @GetMapping("/deleteVisitor")
    public Result<Visitor> deleteUserById(@RequestParam String vid){
        Integer id = Integer.parseInt(vid);
        boolean result = visitorService.deleteUserById(id);
        return result?Result.suc():Result.fail("删除失败！");
    }

    //分页查询
    @PostMapping("/listAllPage")
    public Result<List<Visitor>> listAllPage(@RequestBody QueryPageParam query){
        System.out.println(query);
        HashMap param = query.getParam();
        String vname = (String)param.get("vname");
        String vsex = (String)param.get("vsex");
        Page<Visitor> page = new Page<>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        LambdaQueryWrapper<Visitor> lambdaQueryWrapper = new LambdaQueryWrapper<Visitor>();
        if(!StringUtils.isEmpty(vname) && !"null".equals(vname)){
            lambdaQueryWrapper.like(Visitor::getVname,vname);
        }
        if(!StringUtils.isEmpty(vsex) && !"null".equals(vsex)){
            lambdaQueryWrapper.eq(Visitor::getVsex,vsex);
        }
        IPage<Visitor> result = visitorService.findByAllPage(page,lambdaQueryWrapper);
        if(result != null) {
            return Result.suc(result.getRecords(), result.getTotal());
        } else {
            return Result.fail("查询失败！");
        }
    }

}
