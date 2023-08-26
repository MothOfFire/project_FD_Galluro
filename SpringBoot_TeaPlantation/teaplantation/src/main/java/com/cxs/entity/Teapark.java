package com.cxs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@TableName(value ="teapark")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teapark{
    @TableId(type = IdType.AUTO)
    private Integer pid;//茶园区域id
    private String pname;//茶园区域名称
    private Double area;//面积
    private String function;//园区职能
    private Integer aid;//管理员id
}