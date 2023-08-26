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
 * @TableName gather
 */
@TableName(value ="gather")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Gather implements Serializable {
    /**
     * 采摘id
     */
    @TableId(type = IdType.AUTO)
    private Integer gatherid;

    /**
     * 采摘时间
     */
    private String gathertime;

    /**
     * 采摘茶叶的状态
     */
    private String status;

    /**
     * 用于制作茶叶的种类
     */
    private String makekind;

    /**
     * 采摘进度(0-100)
     */
    private Integer speed;

    /**
     * 采摘人数
     */
    private Integer number;

    /**
     * 是否已经开始采摘
     */
    private String isbegin;

    /**
     * 是否已经结束采摘
     */
    private String isend;

    /**
     * 茶树种类
     * */
    private String kind;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}