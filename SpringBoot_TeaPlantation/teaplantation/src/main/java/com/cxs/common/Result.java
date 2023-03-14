package com.cxs.common;

import lombok.Data;

@Data
public class Result<T> {

    private int code;//编码200或400
    private String msg;//成功或失败
    private Long total;//总记录数
    private T data;//数据

    //失败
    public static<T> Result<T> fail(){
        return result(400,"失败",0L,null);
    }

    //成功
    public static<T> Result<T> suc(){
        return result(200,"成功",0L,null);
    }
    public static<T> Result<T> suc(T data){
        return result(200,"成功",0L,data);
    }
    public static<T> Result<T> suc(T data,Long total){
        return result(200,"成功",total,data);
    }

    private static<T> Result<T> result(int code,String msg,Long total,T data){
        Result<T> result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setTotal(total);
        result.setData(data);
        return result;
    }
}

