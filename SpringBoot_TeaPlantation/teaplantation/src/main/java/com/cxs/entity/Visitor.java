package com.cxs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName visitor
 */
@TableName(value ="visitor")
@Data
public class Visitor implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer vid;

    /**
     * 
     */
    private String vname;

    /**
     * 
     */
    private String vsex;

    /**
     * 
     */
    private Integer vage;

    /**
     * 
     */
    private String vphone;

    /**
     * 
     */
    private Integer cid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}