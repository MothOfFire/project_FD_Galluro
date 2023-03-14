package com.cxs.common;

import java.math.BigInteger;
import java.security.MessageDigest;

//MD5加密工具类
public class Md5Utils {
    public static String md5(String s){
        try {
            MessageDigest md5=MessageDigest.getInstance("MD5");
            //更新
            md5.update(s.getBytes("utf-8"));
            //获取摘要数据
            byte[] digest = md5.digest();
            //转成16进制的字符串
            return new BigInteger(1,digest).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
