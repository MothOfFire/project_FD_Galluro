package com.cxs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxs.common.Result;
import com.cxs.common.StringUtils;
import com.cxs.entity.Equipment;
import com.cxs.entity.Equipmentitem;
import com.cxs.entity.QueryPageParam;
import com.cxs.entity.Repository;
import com.cxs.service.EquipmentService;
import com.cxs.service.EquipmentitemService;
import com.cxs.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/equipment")
public class equipmentController {
    @Autowired
    EquipmentService equipmentService;
    @Autowired
    EquipmentitemService equipmentitemService;
    @Autowired
    RepositoryService repositoryService;

    //根据eid查询仓库数据
    @GetMapping("/selectRepositoryByEid")
    public  Result<Repository> selectRepositoryByEid(String eid){
        System.out.println(eid);
        return Result.suc(repositoryService.selectRepositoryByEid(Integer.parseInt(eid)));
    }

    //添加设备
    @PostMapping("/addEquipmentItem")
    public Result<Equipmentitem> addEquipmentItem(@RequestBody Equipmentitem equipmentitem){
        int total = 1;
        int badnum = 0;
        //判断新增的设备是否损坏
        /***
         * 字符串比较使用equals
         */
        if (equipmentitem.getIsbad().equals("true")){
            badnum += 1;
        }
        //查询equipment数据库中有没有eid与新增equipmentitem的eid相同的数据项
        Equipment equipment = equipmentService.SelectEquipmentByEid(equipmentitem.getEid());
        if (equipment != null){
            //数据库中有,修改
            equipment.setTotal(equipment.getTotal() + 1);
            equipment.setBadnum(equipment.getBadnum() + badnum);
            equipmentService.updateEquipmentByEid(equipment);
        } else {
            //没有则新增
            equipment = new Equipment(null,equipmentitem.getEname(),total,badnum,9);
            equipmentService.insertEquipment(equipment);
        }
        boolean result = equipmentitemService.insertEquipmentItem(equipmentitem);
        //当result为true时，执行Result.suc()
        //当result为false时，执行Result.fail("添加失败!")
        return result?Result.suc():Result.fail("添加失败!");
    }

    //添加仓库
    @PostMapping("/addRepository")
    public Result<Repository> addRepository(@RequestBody Repository repository){
        boolean result = repositoryService.insertRepository(repository);
        //当result为true时，添加数据成功，执行Result.suc()
        //当result为false时，添加数据失败，执行Result.fail("添加失败!")
        return result?Result.suc():Result.fail("添加失败!");
    }

    //修改设备
    @PostMapping("/updateEquipmentItem")
    public Result<Equipmentitem> updateEquipmentItem(@RequestBody Equipmentitem equipmentitem){
        Equipment equipment = equipmentService.SelectEquipmentByEid(equipmentitem.getEid());
        Equipmentitem equipmentitem1 = equipmentitemService.SelectEquipmentItemByIno(equipmentitem.getIno());
        //判断修改的设备损坏状态是否发生改变
        if (equipmentitem1.getIsbad().equals("false") && equipmentitem.getIsbad().equals("true")){
            equipment.setBadnum(equipment.getBadnum() + 1);
        }
        if (equipmentitem1.getIsbad().equals("true") && equipmentitem.getIsbad().equals("false")){
            equipment.setBadnum(equipment.getBadnum() - 1);
        }
        equipmentService.updateEquipmentByEid(equipment);
        boolean result = equipmentitemService.updateEquipmentItemByIno(equipmentitem);
        return result?Result.suc():Result.fail("修改数据失败!");
    }

    //修改仓库
    @PostMapping("/updateRepository")
    public Result<Repository> updateRepository(@RequestBody Repository repository){
        boolean result = repositoryService.updateRepositoryByRid(repository);
        return result?Result.suc():Result.fail();
    }

    //删除设备
    @GetMapping("/deleteEquipmentItem")
    public Result<Equipmentitem> deleteEquipmentItemByIno(@RequestParam String ino){
        Integer id = Integer.parseInt(ino);
        Equipmentitem equipmentitem = equipmentitemService.SelectEquipmentItemByIno(id);
        Equipment equipment = equipmentService.SelectEquipmentByEid(equipmentitem.getEid());
        int count = equipmentitemService.findNUmberByIEid(equipmentitem.getEid());
        if(equipmentitem.getIsbad().equals("true") && count > 1){
            equipment.setBadnum(equipment.getBadnum() - 1);
            equipment.setTotal(equipment.getTotal() - 1);
            equipmentService.updateEquipmentByEid(equipment);
        }
        //equipmentitem中eid对应的数据条目只有一条时，删除equipment中对应eid的数据条目
        if (count == 1){
            equipmentService.deleteEquipmentByEid(equipmentitem.getEid());
        }
        boolean result = equipmentitemService.deleteEquipmentItemByIno(id);
        return result?Result.suc():Result.fail("删除数据失败!");
    }

    //设备分页查询
    @PostMapping("/listAllEquipmentItemPage")
    public Result<List<Equipmentitem>> listAllPage(@RequestBody QueryPageParam query){
        System.out.println(query);
        HashMap param = query.getParam();
        String ino = (String)param.get("ino");
        String isbad = (String)param.get("isbad");
        Page<Equipmentitem> page = new Page<>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        LambdaQueryWrapper<Equipmentitem> lambdaQueryWrapper = new LambdaQueryWrapper<Equipmentitem>();
        if(!StringUtils.isEmpty(ino) && !"null".equals(ino)){
            lambdaQueryWrapper.like(Equipmentitem::getIno,ino);
        }
        if(!StringUtils.isEmpty(isbad) && !"null".equals(isbad)){
            lambdaQueryWrapper.like(Equipmentitem::getIsbad,isbad);
        }
        IPage<Equipmentitem> result = equipmentitemService.findByAllPage(page,lambdaQueryWrapper);
        if(result != null) {
            return Result.suc(result.getRecords(), result.getTotal());
        } else {
            return Result.fail("查询失败！");
        }
    }

    //仓库分页查询
    @PostMapping("/listAllRepositoryPage")
    public Result<List<Repository>> listAllRepositoryPage(@RequestBody QueryPageParam query){
        System.out.println(query);
        HashMap param = query.getParam();
        String rid = (String)param.get("rid");
        String eid = (String)param.get("eid");
        Page<Repository> page = new Page<>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        LambdaQueryWrapper<Repository> lambdaQueryWrapper = new LambdaQueryWrapper<Repository>();
        if(!StringUtils.isEmpty(rid) && !"null".equals(rid)){
            lambdaQueryWrapper.like(Repository::getRid,rid);
        }
        if(!StringUtils.isEmpty(eid) && !"null".equals(eid)){
            lambdaQueryWrapper.like(Repository::getEid,eid);
        }
        IPage<Repository> result = repositoryService.findByAllPage(page,lambdaQueryWrapper);
        if(result != null) {
            return Result.suc(result.getRecords(), result.getTotal());
        } else {
            return Result.fail("查询失败！");
        }
    }
}
