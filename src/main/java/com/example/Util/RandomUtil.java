package com.example.Util;

import java.util.Random;

/**
 * Created by Administrator on 2017/12/11.
 */
public class RandomUtil {

    private static Random random = new Random();
    /**
     * 生成随机字母(小写)
     * @return
     */
    public static char getRandomChar(){
        return (char) (random.nextInt(27)+'a');
    }
    /**
     * 生成随机数字
     * @return
     */
    public static char getRandomNum(){
        return (char) (random.nextInt(10)+'0');
    }
    /**
     * 生成指定数量的字母字符串(小写)
     * @param count
     * @return
     */
    public static String getRandomChar(int count){
        StringBuilder rs=new StringBuilder();
        while(count-->0)
            rs.append(getRandomChar());
        return rs.toString();
    }
    /**
     * 生成指定数量的随数字 字符�?
     * @param count	字符�?
     * @return
     */
    public static String getRandomNumber(int count){
        StringBuilder rs=new StringBuilder();
        while(count-->0)
            rs.append(getRandomNum());
        System.out.println(rs.toString());
        return rs.toString();
    }
}
