package com.cxs.controller;

import com.cxs.common.Result;
import com.cxs.entity.*;
import com.cxs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/visualization")
public class visualizationController {
    @Autowired
    TrafficService trafficService;
    @Autowired
    UserService userService;
    @Autowired
    TeaparkService teaparkService;
    @Autowired
    EquipmentService equipmentService;
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    GatherspeedService gatherspeedService;
    @Autowired
    GoodsService goodsService;

    //获取人流量数据
    @GetMapping("/listTraffic")
    public Result<List<Traffic>> findAllTraffic(){
        List<Traffic> trafficList = trafficService.findAllTraffic();
        if (trafficList.size() != 0) {
            return Result.suc(trafficList,trafficService.getTotal());
        } else {
            return Result.fail("数据获取失败!");
        }
    }

    //获取员工的分布数据
    @GetMapping("/employeeTotal")
    public Result<HashMap<String,Integer>> findEmployeeTotalByPid(){
        HashMap<String,Integer> hashMap = new HashMap<>();
        for (int i = 1; i <= teaparkService.getTotal(); i++){
            //将园区名称和园区人数添加到hashMap中
            hashMap.put(teaparkService.findPnameByPid(i), userService.getTotalByPid(i));
        }
        if (hashMap.size() != 0) {
            return Result.suc(hashMap);
        } else {
            return Result.fail("数据获取失败!");
        }
    }

    @GetMapping("/gatherList")
    public Result<HashMap<String,Object>> findGatherList(){
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        for (int i = 1; i <= gatherspeedService.getTotal(); i++){
            //将茶叶名称和未采摘面积添加到hashMap中
            hashMap.put(gatherspeedService.getGatherNameById(i), (gatherspeedService.getTotalareaById(i) - gatherspeedService.getReadyareaById(i)));
        }
        if (hashMap.size() != 0) {
            return Result.suc(hashMap);
        } else {
            return Result.fail("数据获取失败!");
        }
    }

    @GetMapping("/goodsList")
    public Result<HashMap<String,Integer>> findGoodsList(){
        HashMap<String,Integer> hashMap = new HashMap<>();
        for (int i = 1; i <= goodsService.getGoodsTotal(); i++){
            //将产品名称和产品库存添加到hashMap中
            hashMap.put(goodsService.findGnameById(i), goodsService.findStockById(i));
        }
        if (hashMap.size() != 0) {
            return Result.suc(hashMap);
        } else {
            return Result.fail("数据获取失败!");
        }
    }

    //获取损坏设备的数据
    @GetMapping("/badNumber")
    public Result<HashMap<String,Integer>> findBadNumberByEid(){
        HashMap<String,Integer> hashMap = new HashMap<>();
        for (int i = 1; i <= equipmentService.getTotal(); i++){
            //将设备名称和损坏设备数量添加到hashMap中
            hashMap.put(equipmentService.findEnameByEid(i), equipmentService.findBadNumbereByEid(i));
        }
        if (hashMap.size() != 0) {
            return Result.suc(hashMap);
        } else {
            return Result.fail("数据获取失败!");
        }
    }

    //获取需要入库（采购）设备的数据
    @GetMapping("/pushNumber")
    public Result<HashMap<String,Integer>> findPushNumberByRid(){
        HashMap<String,Integer> hashMap = new HashMap<>();
        for (int i = 1; i <= equipmentService.getTotal(); i++){
            //将设备名称和需要入库（采购）设备的数量添加到hashMap中
            hashMap.put(equipmentService.findEnameByEid(i), repositoryService.findPushNumberByEid(i));
        }
        if (hashMap.size() != 0) {
            return Result.suc(hashMap);
        } else {
            return Result.fail("数据获取失败!");
        }

    }

}
