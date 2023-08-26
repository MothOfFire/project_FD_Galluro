package com.cxs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName repository
 */
@TableName(value ="repository")
@Data
public class Repository implements Serializable {
    /**
     * 仓库id
     */
    @TableId(type = IdType.AUTO)
    private Integer rid;

    /**
     * 仓库名称
     */
    private String rname;

    /**
     * 存放设备的id
     */
    private Integer eid;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 需要入库的数量
     */
    private Integer pushnumber;

    /**
     * 需要出库的数量
     */
    private Integer popnumber;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}