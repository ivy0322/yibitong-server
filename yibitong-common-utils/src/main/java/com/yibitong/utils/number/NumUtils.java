package com.yibitong.utils.number;


import com.yibitong.utils.idcard.DateUtils;
import com.yibitong.utils.math.RandomUtils;

import java.util.Date;

/**
 * ClassName：NumUtils
 * Description：编号生成工具类
 * Author：叶孤城
 * Created：2017/4/17
 */
public class NumUtils {

    /**
     * 生成订单编号(20位)
     * @return
     */
    public static String getOrderNum(){
        return DateUtils.dateToStr(new Date(),DateUtils.DATE_FORMAT_DATETIMEMS) + get3Random();
    }


    /**
     * 生成交易流水编号(40位)
     * @return
     */
    public static String getTransactionId(){
        return DateUtils.dateToStr(new Date(),DateUtils.DATE_FORMAT_DATETIMEMS) + get13Random();
    }


    /**
     * 生成3位随机数
     * @return
     */
    public static String get3Random() {
        int rst = 0;
        while(rst < 100){
            rst = RandomUtils.genRandomInt(3);
        }
        return String.valueOf(rst);
    }

    /**
     * 生成6位随机数
     * @return
     */
    public static String get6Random() {
        int rst = 0;
        while(rst < 100000){
            rst = RandomUtils.genRandomInt(6);
        }
        return String.valueOf(rst);
    }

    /**
     * 生成13位随机数
     * @return
     */
    public static String get13Random() {
        long rst = 0;
        while(rst < 1000000000000L){
            rst = RandomUtils.genRandomLong(13);
        }
        return String.valueOf(rst);
    }
}
