package com.cxs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName teatree
 */
@TableName(value ="teatree")
@Data
public class Teatree implements Serializable {
    /**
     * 茶树种植区域id
     */
    @TableId(type = IdType.AUTO)
    private Integer tid;

    /**
     * 种植面积
     */
    private Double area;

    /**
     * 是否需要除虫
     */
    private String isdeinsectization;

    /**
     * 是否需要浇水
     */
    private String iswater;

    /**
     * 是否需要施肥
     */
    private String isfertilize;

    /**
     * 是否可以采摘
     */
    private String isgather;

    /**
     * 茶树种类
     */
    private String kid;

    /**
     * 所在园区
     */
    private Integer pid;

    /**
     * 是否需要修剪
     */
    private String isprune;

    /**
     * 是否需要除草
     */
    private String isweed;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}