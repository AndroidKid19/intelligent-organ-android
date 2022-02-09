package com.yway.scomponent.commonsdk.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * @ProjectName: Android-Project
 * @Package: com.fatalsignal.outsourcing.home.mvp.ui.utils
 * @ClassName: NumberDealUtil
 * @Description:
 * @Author: Yuanweiwei
 * @CreateDate: 2020/1/10 10:38
 * @UpdateUser:
 * @UpdateDate: 2020/1/10 10:38
 * @UpdateRemark:
 * @Version: 1.0
 */

public class NumberDealUtil {
    /**
     * 移除数字前面和后面冗余的0，只保留2位小数，四舍五入
     * double d1 = 123456.36987
     * isFormat true out d1=123456.37
     * isFormat false out d1=123,456.37
     * @param isFormat 是否需要千位分隔符(,)这种格式输出
     * @param num
     * @return
     */
    public static String trim0(boolean isFormat, double num) {
        NumberFormat nf = NumberFormat.getInstance();
        if (!isFormat) {
            //设置输出格式是否使用“,”分组,默认是使用的
            nf.setGroupingUsed(false);
        }
        String result = nf.format(num);
//        return isFormat ? result : result.replace(",", ""); //用上面代码替换去除分隔符操作
        return result;
    }

    /**
     * 移除数字前面和后面冗余的0，四舍五入
     * double d1 = 123456.36987
     * isFormat true out d1=123456.37
     * isFormat false out d1=123,456.37
     * @param isFormat      是否需要千位分隔符(,)这种格式输出
     * @param num
     * @param fractionDigit 要保留的小数位数
     * @return
     */
    public static String trim0(boolean isFormat, double num, int fractionDigit) {
        NumberFormat nf = NumberFormat.getInstance();
        //设置最多保留小数位数，不足不补0
        nf.setMaximumFractionDigits(fractionDigit);
        //setMaximumFractionDigits不会保留小数点和后面多余的0，不需下面正则去除
//        if (result.contains(".") && result.endsWith("0")) {
//            result = result.replaceAll("0+?$", "");//去掉多余的0
//            result = result.replaceAll("[.]$", "");//如最后一位是.则去掉
//        }
        if (!isFormat) {
            //设置输出格式是否使用“,”分组,默认是使用的
            nf.setGroupingUsed(false);
        }
        String result = nf.format(num);
//        return isFormat ? result : result.replace(",", "");
        return result;
    }

    /**
     * 指定位数输出，不足补0
     * 小数部分如果大于需要的位数四舍五入
     *
     * @param num
     * @param fractionDigit 小数部分位数
     * @return
     */
    public static String add0Format(double num,int fractionDigit) {
        StringBuilder rule = new StringBuilder();
        if (fractionDigit > 0) {
            rule.append(".");
            for (int i = 0; i < fractionDigit; i++) {
                rule.append("0");
            }
        }
        DecimalFormat df = new DecimalFormat(rule.toString());
        return df.format(num);
    }

    /**
     * 指定位数输出，不足补0
     * 整数部分如果位数大于需要的位数按实际位数输出
     * 小数部分如果大于需要的位数四舍五入
     *
     * @param num
     * @param integerDigit  整数部分位数
     * @param fractionDigit 小数部分位数
     * @return
     */
    public static String add0Format(double num, int integerDigit, int fractionDigit) {
        StringBuilder rule = new StringBuilder();
        if (integerDigit <= 0) {
            rule.append("#");
        } else {
            for (int i = 0; i < integerDigit; i++) {
                rule.append("0");
            }
        }
        if (fractionDigit > 0) {
            rule.append(".");
            for (int i = 0; i < fractionDigit; i++) {
                rule.append("0");
            }
        }
        DecimalFormat df = new DecimalFormat(rule.toString());
        return df.format(num);
    }

    /**
     * 保留几位小数，不足不补0，小数部分冗余的0也不显示
     *
     * @param num
     * @param fractionDigit 要保留小数的位数
     * @return
     */
    public static String fractionDigitFormat(double num, int fractionDigit) {
        /*方法一*/
//        StringBuilder rule = new StringBuilder("#");
//        if (fractionDigit > 0) {
//            rule.append(".");
//            for (int i = 0; i < fractionDigit; i++) {
//                rule.append("#");
//            }
//        }
//        DecimalFormat df = new DecimalFormat(rule.toString());
//        return df.format(num);

        /*方法二*/
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(fractionDigit);
        //设置输出格式是否使用“,”分组,这里不使用
        nf.setGroupingUsed(false);
        return nf.format(num);
    }


    /********************货币格式化******************************************/

    /**
     * 货币格式 保留几位小数，不足不补0，小数部分冗余的0也不显示
     * $123,456.37
     * @param num
     * @return
     */
    public static String numberFormatMoney(double num){
        //建立货币格式化引用
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        return currencyFormat.format(new BigDecimal(num));
    }

    /**
     * 货币格式 保留几位小数，不足不补0，小数部分冗余的0也不显示
     * $123,456.37
     * @param num
     * @param fractionDigit 小数部分位数
     * @return
     */
    public static String numberFormatMoney(double num,int fractionDigit){
        //建立货币格式化引用
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        currencyFormat.setMaximumFractionDigits(fractionDigit);
        return currencyFormat.format(new BigDecimal(num));
    }

    /**
     * 货币格式
     * $12,345,678.90
     * @param num
     * @param inLocale 国家 Locale.US
     * @param fractionDigit 小数部分位数
     * @return
     */
    public static String numberFormatMoney(double num,Locale inLocale,int fractionDigit){
        //建立货币格式化引用
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(inLocale);
        return currencyFormat.format(new BigDecimal(num));
    }




    public static void main(String[] args) {
        double d1 = 123456.0, d2 = 12.3698;
        DecimalFormat nf = new DecimalFormat("0000.00");
        System.out.println("d1=" + nf.format(d1) + " d2=" + nf.format(d2));//d1=123456.370 d2=0012.370
        nf = new DecimalFormat("#");
        System.out.println("d1=" + nf.format(d1));//d1=123456
        nf = new DecimalFormat(".####");
        System.out.println("d1=" + nf.format(d1));//d1=123456.3699
        nf = new DecimalFormat("0000,0000.00000");//注意“,”不能放在小数部分
        System.out.println("d1=" + nf.format(d1));//d1=0012,3456.36987
        System.out.println("d1=" + numberFormatMoney(110.1240,5));//
    }


}

