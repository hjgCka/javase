package com.hjg.formatter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/20
 */
public class DecimalFormatter {

    public static void main(String[] args) {
        //这个对象不是线程安全的
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());
        DecimalFormat decimalFormat = null;
        if (numberFormat instanceof DecimalFormat) {
            decimalFormat = (DecimalFormat) numberFormat;
            decimalFormat.setDecimalSeparatorAlwaysShown(true);
        }

        //可以对格式进行自定义，这里整数部分不变，小数部分保留3位，超过部分四舍五入
        decimalFormat.applyPattern(".000");

        String result = decimalFormat.format(200000.3599);
        System.out.println(result);
        //200000.360
    }
}
