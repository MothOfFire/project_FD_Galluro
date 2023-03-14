package com.cxs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@TableName(value ="equipment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipment implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer eid;//设备id
    private String ename;//设备名称
    private Integer total;//该设备id的总数
    private Integer badnum;//坏的设备数量
    private Integer aid;//管理员id
}