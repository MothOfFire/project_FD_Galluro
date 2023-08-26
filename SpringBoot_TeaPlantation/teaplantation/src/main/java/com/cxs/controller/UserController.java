package com.cxs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxs.entity.QueryPageParam;
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
    public User findUserByUserNameAndPassword(String uname,String password){
        return userService.findUserByUserNameAndPassword(uname,password);
    }

    //查询所有user
    @GetMapping("/listUser")
    public Result<List<User>> findAllUser(){
        List<User> userList = userService.findAllUser();
        if (userList.size() != 0) {
            return Result.suc(userService.findAllUser(),userService.getTotal());
        } else {
            return Result.fail("无用户数据!");
        }
    }

    @GetMapping("/findByUname")
    public Result<List<User>> findByUname(@RequestParam String uname){
        boolean result;
        //查询数据库中是否已经有该名称的数据项
        List<User> list = userService.findUserByUName(uname);
        if (list.size()>0) {
            result = true;
        } else {
            result = false;
        }
        return result?Result.suc(list):Result.fail();
    }

    //模糊查询
    @GetMapping("/findUserByKeyWord")
    public List<User> findUserByKeyWord(String keyword){

        return userService.findUserByUserName(keyword);
    }

    //添加user
    @PostMapping("/addUser")
    public Result<User> addUser(@RequestBody User user){
        if(userService.insertUser(user)) {
            return Result.suc();
        } else {
            return Result.fail("用户添加失败!");
        }
    }

    //修改user
    @PostMapping("/updateUser")
    public Result<User> updateUser(@RequestBody User user){
        if (userService.updateUserByUid(user)) {
            return Result.suc();
        } else {
            return Result.fail("修改数据失败！");
        }
    }

    //删除user
    @GetMapping("/deleteUser")
    public Result<User> deleteUserById(@RequestParam String uid){
        if (userService.deleteUserById(Integer.parseInt(uid))) {
            return Result.suc();
        }else {
            return Result.fail("删除数据失败！");
        }
    }

    //登录
    @PostMapping("/login")
    public Result<User> login(@RequestBody User user){
        User result = userService.findUserByUserNameAndPassword(user.getUname(),user.getPassword());
        if (result != null) {
            return Result.suc(result);
        }else {
            return Result.fail("用户名或密码错误");
        }
    }

    //注册
    @PostMapping("/register")
    public Result<User> register(@RequestBody User user){
        //判空
        if (user.getUname() != null && user.getUname() != ""
                && user.getPassword() != null && user.getPassword() != ""){
            //检验用户名是否已经存在
            if(userService.findUserByUserName(user.getUname()) == null ) {
                return userService.save(user)?Result.suc():Result.fail();
            }else {
                return Result.fail("该用户名已存在");
            }
        }else {
            return Result.fail("用户名或密码不能为空");
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
        if(result != null) {
            return Result.suc(result.getRecords(), result.getTotal());
        } else {
            return Result.fail("查询失败！");
        }
    }

}
