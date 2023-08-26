package com.cxs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@TableName(value ="action")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Action implements Serializable {
    /**
     * 执行者id
     */
    @TableId(type = IdType.AUTO)
    private Integer actorid;

    /**
     * 权限
     */
    private String power;

    /**
     * 行为
     */
    private String action;

    /**
     * 执行时间
     */
    private Date actiontime;

    /**
     * 状态
     */
    private String status;

    /**
     * 被执行的设备id
     * */
    private Integer eid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}