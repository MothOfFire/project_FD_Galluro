package com.cxs.common;


import java.util.Date;

public class DateUtils {
    //yyyy-MM-dd
    public String changeDayTime(Date date) {
        String str = new String();
        if(date.getMonth()>10 && date.getDate()>10){
            str = date.toLocaleString().substring(0,10);
        }
        else if(date.getMonth()>10 && date.getDate()<10){
            str = date.toLocaleString().substring(0,8);
            String substr = date.toLocaleString().substring(8,9);
            substr = "0"+ substr;
            str = str + substr;
        }
        else if (date.getMonth()<10 && date.getDate()<10){
            str = date.toLocaleString().substring(0,5);
            String substr1 = date.toLocaleString().substring(5,7);
            substr1= "0" + substr1;
            String substr2 = date.toLocaleString().substring(7,8);
            substr2 = "0"+ substr2;
            str = str + substr1 + substr2;
        }
        return str;
    }

    //yyy-MM
    public String changeMothTime(Date date) {
        String strMoth = new String();
        if(date.getMonth()>10){
            strMoth = date.toLocaleString().substring(0,8);
        }
        else if (date.getMonth()<10){
            strMoth = date.toLocaleString().substring(0,5);
            String substr1 = date.toLocaleString().substring(5,7);
            substr1= "0" + substr1;
            strMoth = strMoth + substr1;
        }
        return strMoth;
    }

    //yyyy
    public String changeYearTime(Date date) {
        String strYear = date.toLocaleString().substring(0,4);;
        return strYear;
    }
}
