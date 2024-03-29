package com.cxs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@TableName(value ="visitor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visitor{
    @TableId(type = IdType.AUTO)
    private Integer vid;//游客id
    private String vname;//游客名称
    private String vsex;//游客性别
    private Integer vage;//游客年龄
    private String vphone;//游客电话
    private Integer cid;//游客的车位id
    private Date entertime;//游客进入茶园的时间
    private Date outtime;//游客出茶园的时间
    private String status;//游客状态1为出园，0为还在园内
}