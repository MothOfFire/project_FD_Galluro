package com.cxs.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cxs.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

//这个注解表示这是一个mybatis的mapper类--dao
@Mapper
public interface UserMapper extends BaseMapper<User> {

    //@Param("实体类属性“)
    //根据uname和password查找用户（User）
    @Select("select * from user where uname=#{uname} and password=#{password}")
    User findUserByUserNameAndPassword(@Param("uname") String uname,@Param("password") String password);

    //模糊查询
    @Select("select * from user where uname like concat('%',#{keyword},'%')")
    List<User> findUserByUserName(String keyword);

    //查询所有用户
    @Select("select * from user")
    List<User> findAllUser();

    //获取用户的总数
    @Select("select count(*) from user")
    long getTotal();

    //添加用户
    @Insert("insert into user values(null,#{uname},#{password},#{usex},#{uage},#{address},#{uphone},#{role})")
    int insertUser(User user);

    //修改用户
    @Update("update user " +
            "set uname=#{uname}," +
            "password=#{password}," +
            "usex=#{usex},uage=#{uage}," +
            "address=#{address},uphone=#{uphone}," +
            "role=#{role} where uid=#{uid} ")
    void updateUserByUid(User user);

    //删除用户
    @Delete("delete from user where uid=#{uid}")
    void deleteUserById(@Param("uid") Integer uid);

    //分页查询
    @Select("select * from user where uname like concat('%',#{uname},'%')" +
            " limit #{pageNum},#{pageSize} ")
    List<User> findByPage(Map<String,Object> condition);

    @Select("select * from user ${ew.customSqlSegment}")
    IPage<User> findByAllPage(IPage<User> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    //分页总条目
    @Select("select count(*) from user where uid = #{uid} " +
            "and uname like concat('%',#{uname},'%')")
    long getCount(Map<String,Object> condition);

}
