package com.cxs.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxs.entity.User;
import com.cxs.mapper.UserMapper;
import com.cxs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User findUserByUserNameAndPassword(String uname, String password) {
        return userMapper.findUserByUserNameAndPassword(uname,password);
    }

    @Override
    public List<User> findUserByUserName(String keyword) {
        return userMapper.findUserByUserName(keyword);
    }

    @Override
    public List<User> findAllUser() {
        return userMapper.findAllUser();
    }

    @Override
    public int insertUser(User user) {
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
