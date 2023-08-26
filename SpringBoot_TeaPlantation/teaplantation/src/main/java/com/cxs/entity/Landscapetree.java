package com.cxs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName landscapetree
 */
@TableName(value ="landscapetree")
@Data
public class Landscapetree implements Serializable {
    /**
     * 景观树id
     */
    @TableId(type = IdType.AUTO)
    private Integer lid;

    /**
     * 景观树数量
     */
    private Integer number;

    /**
     * 是否需要除虫
     */
    private String isdeinsectization;

    /**
     * 是否需要除草
     */
    private String isweed;

    /**
     * 是否需要浇水
     */
    private String iswater;

    /**
     * 是否需要施肥
     */
    private String isfertilize;

    /**
     * 是否需要修剪
     */
    private String isprune;

    /**
     * 景观树的形态
     */
    private String shape;

    /**
     * 景观树的种类
     */
    private String kid;

    /**
     * 花期
     */
    private String florescence;

    /**
     * 所在园区id
     */
    private Integer pid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}