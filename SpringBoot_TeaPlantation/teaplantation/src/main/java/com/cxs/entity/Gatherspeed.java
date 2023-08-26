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
 * @TableName gatherspeed
 */
@TableName(value ="gatherspeed")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Gatherspeed implements Serializable {
    /**
     * id
     */
    @TableId
    private Integer gatherid;

    /**
     * 总面积
     */
    private Double totalarea;

    /**
     * 已采摘的面积
     */
    private Double readyarea;

    /**
     * 进度（0-100）
     */
    private Integer speed;
    /**
     * 采摘茶叶茶树的名字加季节
     * */
    private String gathername;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}