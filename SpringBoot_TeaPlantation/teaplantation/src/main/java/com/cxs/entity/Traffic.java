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


@TableName(value ="traffic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Traffic implements Serializable {
    /**
     * 日人流量
     */
    private Integer daynumber;

    /**
     * 日期时间
     */
    private Date date;

    /**
     * 月人流量
     */
    private Integer mothnumber;

    /**
     * 年人流量
     */
    private Integer yearnumber;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}