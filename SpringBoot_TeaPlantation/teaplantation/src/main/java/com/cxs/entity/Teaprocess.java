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
 * @TableName teaprocess
 */
@TableName(value ="teaprocess")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teaprocess implements Serializable {
    /**
     * 工序id
     */
    @TableId
    private Integer workid;

    /**
     * 工序名称
     */
    private String workname;

    /**
     * 加工成品的茶叶种类
     */
    private String makekind;


    /**
     * 工序的顺序
     * */
    private Integer ordernumber;

    /**
     * 是否已经开始加工
     */
    private String isbegin;

    /**
     * 是否已经结束加工
     */
    private String isend;

    /**
     * 加工进度(0-100)
     */
    private Integer speed;

    /**
     * 加工人数
     */
    private Integer number;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}