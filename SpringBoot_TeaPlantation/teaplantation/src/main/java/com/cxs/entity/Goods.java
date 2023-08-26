package com.cxs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@TableName(value ="goods")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goods implements Serializable {
    /**
     * 商品id
     */
    @TableId(type = IdType.AUTO)
    private Integer gid;

    /**
     * 商品名称
     */
    private String gname;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 包装类型
     * */
    private String packtype;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 图片地址
     */
    private String picture;

    /**
     * 商品描述
     */
    private String characterize;

    /**
     * 点赞数，用于推荐排序
     */
    private Integer likes;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}