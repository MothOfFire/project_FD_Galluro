package com.cxs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxs.common.Result;
import com.cxs.common.StringUtils;
import com.cxs.entity.*;
import com.cxs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teaLeaf")
public class teaLeafController {
    @Autowired
    TeaprocessService teaprocessService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    GatherService gatherService;
    @Autowired
    GatherspeedService gatherspeedService;
    @Autowired
    TeatreeService teatreeService;
    @Autowired
    WorkspeedService workspeedService;

    @GetMapping("/getProcessList")
    public Result<List<Teaprocess>> getProcessList(@RequestParam String makekind) {
        List<Teaprocess> teaprocessList = teaprocessService.selectProcessByMakeKind(makekind);
        if (teaprocessList.size() != 0) {
            return Result.suc(teaprocessList,teaprocessService.getProcessTotalByMakeKind(makekind));
        } else {
            return Result.fail("查询失败!");
        }
    }

    @PostMapping("/addGather")
    public Result<Gather> addGather(@RequestBody Gather gather){
        Gatherspeed gatherspeed = new Gatherspeed();
        gatherspeed.setTotalarea(teatreeService.findTeatreeByKid(gather.getKind()).getArea());
        String name = gather.getGathertime() + gather.getStatus() + gather.getKind();
        gatherspeed.setGathername(name);
        gatherspeed.setReadyarea(gatherspeed.getTotalarea() * gather.getSpeed());
        gatherspeed.setSpeed(gather.getSpeed());
        gatherspeedService.insertGatherSpeed(gatherspeed);
        boolean result = gatherService.insertGather(gather);
        return result?Result.suc():Result.fail("添加失败!");
    }

    @PostMapping("/addGoods")
    public Result<Goods> addGoods(@RequestBody Goods goods){
//        System.out.println(goods);
        boolean result = goodsService.insertGoods(goods);
        return result?Result.suc():Result.fail("添加失败！");
    }

    @GetMapping("/getGoodsList")
    public Result<List<Goods>> getGoodsList() {
        List<Goods> goodsList = goodsService.selectGoodsByLikes();
        if (goodsList.size() != 0) {
            return Result.suc(goodsList,goodsService.getGoodsTotal());
        } else {
            return Result.fail("查询失败!");
        }

    }

    @PostMapping("/updateGather")
    public Result<Gather> updateGather(@RequestBody Gather gather){
        //根据传入后端的数据中的gatherid在数据库中gatherspeed查找数据项
        Gatherspeed gatherspeed = gatherspeedService.findGatherSpeedById(gather.getGatherid());
        //将传入后端的speed添加进gatherspeed中
        gatherspeed.setSpeed(gather.getSpeed());
        //计算未采摘的面积
        Double ready = gatherspeed.getTotalarea() * (gatherspeed.getSpeed() * 0.01);
        //将未采摘的面积添加到gatherspeed
        gatherspeed.setReadyarea(ready);
        //修改数据库中gatherspeed表中的数据
        gatherspeedService.updateGatherSpeedById(gatherspeed);
        //修改数据库中gather表中的数据
        boolean result = gatherService.updateGatherById(gather);
        return result?Result.suc():Result.fail("修改失败!");
    }

    //修改加工步骤信息
    @PostMapping("/updateProcess")
    public Result<Teaprocess> updateProcess(@RequestBody Teaprocess teaprocess){
        boolean result = teaprocessService.updateTeaProcessById(teaprocess);
        return result?Result.suc():Result.fail("修改失败！");
    }

    @PostMapping("/updateWorkSpeed")
    public Result<Workspeed> updateWorkSpeed(@RequestBody Map<String, Object> jsonData){
        String readyweight = jsonData.get("readyweight").toString();
        Integer workid = Integer.parseInt(jsonData.get("workid").toString());
        Workspeed workspeed = workspeedService.findWorkSpeedBtId(workid);
        Workspeed workspeed1 = workspeed;
        Teaprocess teaprocess = teaprocessService.findProcessById(workid);
        if(workspeed.getReadyweight() != 0) {
            teaprocess.setIsbegin("true");
        }
        Double ready = Double.parseDouble(readyweight) + workspeed.getReadyweight();
        Double total = workspeed.getTotalweight();
        if(ready >= total){
            //完成该步骤
            workspeed.setReadyweight(0.00);
            workspeed1.setReadyweight(total);
            workspeed.setSpeed(0);
            workspeed1.setSpeed(100);
            teaprocess.setIsbegin("false");
            teaprocess.setIsend("true");
            teaprocess.setSpeed(0);
            teaprocessService.updateTeaProcessById(teaprocess);
        }else {
            //未完成
            int speed = (int) ((ready / total) * 100);
            workspeed.setReadyweight(ready);
            workspeed1.setReadyweight(ready);
            workspeed.setSpeed(speed);
            workspeed1.setSpeed(speed);
            teaprocess.setSpeed(speed);
            teaprocessService.updateTeaProcessById(teaprocess);
        }
        boolean result = workspeedService.updateWorkSpeedById(workspeed);
        return result?Result.suc(workspeed1):Result.fail();
    }

    @PostMapping("/updateGoods")
    public Result<Goods> updateGoods(@RequestBody Goods goods){
        Goods newGoods = goodsService.findGoodByGid(goods.getGid());
        newGoods.setLikes(goods.getLikes());
//        System.out.println(newGoods);
        return goodsService.updateGoodsById(newGoods)?Result.suc():Result.fail();
    }

    @GetMapping("/deleteGather")
    public Result<Gather> deleteGatherByIno(@RequestParam String gatherid){
        Integer id = Integer.parseInt(gatherid);
        boolean result = gatherService.deleteGatherById(id);
        return result?Result.suc():Result.fail();
    }

    @PostMapping("/listAllGatherPage")
    public Result<List<Gather>> listAllPage(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String makekind = (String)param.get("makekind");
        String gathertime = (String)param.get("gathertime");
        Page<Gather> page = new Page<>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        LambdaQueryWrapper<Gather> lambdaQueryWrapper = new LambdaQueryWrapper<Gather>();
        if(!StringUtils.isEmpty(makekind) && !"null".equals(makekind)){
            lambdaQueryWrapper.like(Gather::getMakekind,makekind);
        }
        if(!StringUtils.isEmpty(gathertime) && !"null".equals(gathertime)){
            lambdaQueryWrapper.like(Gather::getGathertime,gathertime);
        }
        IPage<Gather> result = gatherService.findByAllPage(page,lambdaQueryWrapper);
        if(result != null) {
            return Result.suc(result.getRecords(), result.getTotal());
        } else {
            return Result.fail("查询失败！");
        }
    }

}
