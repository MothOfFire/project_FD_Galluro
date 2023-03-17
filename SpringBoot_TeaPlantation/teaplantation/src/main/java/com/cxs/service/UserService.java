package com.cxs.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cxs.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {

    User findUserByUserNameAndPassword(String uname,String password);
    List<User> findUserByUserName(String keyword);
    List<User> findAllUser();
    int insertUser(User user);
    boolean updateUserByUid(User user);
    boolean deleteUserById(Integer uid);
    long getTotal();
    List<User> findByPage(Map<String,Object> condition);
    long getCount(Map<String,Object> condition);
    IPage<User> findByAllPage(IPage<User> page ,@Param(Constants.WRAPPER) Wrapper wrapper);
}
