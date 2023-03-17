package com.cxs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxs.common.QueryPageParam;
import com.cxs.common.Result;
import com.cxs.common.StringUtils;
import com.cxs.entity.User;
import com.cxs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/find")
    public User findUserByUserNameAndPassword(){
        return userService.findUserByUserNameAndPassword("Moon","123456");
    }

    //查询所有user
    @GetMapping("/listUser")
    public Result<List<User>> findAllUser(){
        return Result.suc(userService.findAllUser(),userService.getTotal());
    }

    @GetMapping("/findByUname")
    public Result<List<User>> findByUname(@RequestParam String uname){
        List<User> list = userService.lambdaQuery().eq(User::getUname,uname).list();
        return list.size()>0?Result.suc(list):Result.fail();
    }

    //模糊查询
    @GetMapping("/findUserByKeyWord")
    public List<User> findUserByKeyWord(String keyword){

        return userService.findUserByUserName(keyword);
    }

    //添加user
    @PostMapping("/addUser")
    public Result<User> addUser(@RequestBody User user){
        return userService.save(user)?Result.suc():Result.fail();
    }

    //修改user
    @PostMapping("/updateUser")
    public Result<User> updateUser(@RequestBody User user){
        return userService.updateUserByUid(user)?Result.suc():Result.fail();
    }

    //删除user
    @GetMapping("/deleteUser")
    public Result<User> deleteUserById(@RequestParam String uid){
        return userService.deleteUserById(Integer.parseInt(uid))?Result.suc():Result.fail();
    }

    //登录
    @PostMapping("/login")
    public Result<User> login(@RequestBody User user){
        User result = userService
                      .findUserByUserNameAndPassword(user.getUname(),user.getPassword());
        return result != null?Result.suc(result):Result.fail();
    }

    //注册
    @PostMapping("/register")
    public Result<User> register(@RequestBody User user){
        if (user.getUname() != null && user.getUname() != ""
                && user.getPassword() != null && user.getPassword() != ""){
             return userService.save(user)?Result.suc():Result.fail();
        }else {
            return Result.fail();
        }
    }

    //分页查询
    @PostMapping("/listAllPage")
    public Result<List<User>> listAllPage(@RequestBody QueryPageParam query){
        System.out.println(query);
        HashMap param = query.getParam();
        String uname = (String)param.get("uname");
        String usex = (String)param.get("usex");
        Page<User> page = new Page<>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
        if(!StringUtils.isEmpty(uname) && !"null".equals(uname)){
            lambdaQueryWrapper.like(User::getUname,uname);
        }
        if(!StringUtils.isEmpty(usex) && !"null".equals(usex)){
            lambdaQueryWrapper.eq(User::getUsex,usex);
        }
        IPage<User> result = userService.findByAllPage(page,lambdaQueryWrapper);
        return Result.suc(result.getRecords(), result.getTotal());
    }

}
