package com.cxs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName workspeed
 */
@TableName(value ="workspeed")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Workspeed implements Serializable {
    /**
     * 工序id
     */
    private Integer workid;

    /**
     * 茶叶总重量
     */
    private Double totalweight;

    /**
     * 已完成加工的茶叶重量
     */
    private Double readyweight;

    /**
     * 进度(0-100)
     */
    private Integer speed;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}