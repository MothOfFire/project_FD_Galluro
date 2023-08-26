package com.cxs.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName(value = "user")
@Data
@NoArgsConstructor//无参构造注解
@AllArgsConstructor//有参构造注解
public class User {
    private Integer uid;//用户id
    private String uname;//用户姓名
    private String password;//密码
    private String usex;//性别
    private Integer uage;//年龄
    private String address;//地址
    private String uphone;//电话
    private Integer role;//角色
    private Integer pid;
}
