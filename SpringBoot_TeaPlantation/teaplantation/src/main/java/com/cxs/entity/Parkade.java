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

@TableName(value ="parkade")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parkade{
    @TableId(type = IdType.AUTO)
    private Integer cid;//车位号
    private String iscar;//是否有车停放
    private Integer aid;//管理员id
    private Integer vid;//停放车辆的游客id
    private String carnumber;//车牌号
    private Date ptime;//停放时间
}