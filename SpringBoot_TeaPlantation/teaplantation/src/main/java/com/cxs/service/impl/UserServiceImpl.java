package com.cxs.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxs.entity.User;
import com.cxs.mapper.UserMapper;
import com.cxs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User findUserByUserNameAndPassword(String uname, String password) {
        if (uname == null || uname == "") {
            return null;
        }
        if (password == null || password == "") {
            return null;
        }
        return userMapper.findUserByUserNameAndPassword(uname,password);
    }

    @Override
    public List<User> findUserByUserName(String keyword) {
        return userMapper.findUserByUserName(keyword);
    }

    @Override
    public List<User> findUserByUName(String uname) {
        return userMapper.findUserByUName(uname);
    }

    @Override
    public List<User> findAllUser() {
        return userMapper.findAllUser();
    }

    @Override
    public boolean insertUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public boolean updateUserByUid(User user) {
        if(user == null){
            return false;
        }else {
            userMapper.updateUserByUid(user);
            return true;
        }
    }

    @Override
    public boolean deleteUserById(Integer uid) {
        if(uid == null){
            return false;
        }else {
            userMapper.deleteUserById(uid);
            return true;
        }
    }

    @Override
    public long getTotal() {
        return userMapper.getTotal();
    }

    @Override
    public Integer getTotalByPid(Integer pid) {
        return userMapper.getTotalByPid(pid);
    }

    @Override
    public List<User> findByPage(Map<String, Object> condition) {
        return userMapper.findByPage(condition);
    }

    @Override
    public long getCount(Map<String, Object> condition) {
        return userMapper.getCount(condition);
    }

    @Override
    public IPage<User> findByAllPage(IPage<User> page , Wrapper wrapper) {
        return userMapper.findByAllPage(page,wrapper);
    }

}
