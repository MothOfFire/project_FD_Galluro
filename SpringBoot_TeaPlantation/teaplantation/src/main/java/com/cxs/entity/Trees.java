package com.cxs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName(value ="trees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trees{
    @TableId(type = IdType.AUTO)
    private Integer tid;//树木id
    private String tname;//树木名称
    private Integer tnum;//树木总数
    private String isdeinsectization;//是否需要除虫
    private String iswater;//是否需要浇水
    private String isfertilize;//是否需要施肥
    private String isgather;//是否可以采集
    private String kid;//树的种类
}