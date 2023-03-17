package com.cxs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName(value ="treeitem")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Treeitem {
    @TableId(type = IdType.AUTO)
    private Integer tno;//树木编号
    private Integer tid;//树木id
    private Integer pid;//树木所在园区id
    private String shape;//树木的形态（修剪的外形，诺没造型则为null）
    private String isprune;//是否需要修剪
}