package com.cxs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@TableName(value ="equipmentitem")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipmentitem implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer inum;//设备编码（用于标识每个设备）
    private Integer eid;//设备id与Equipment对应（用于标识每种设备）
    private String isbad;//是否损坏
    private Integer aid;//管理员id
}