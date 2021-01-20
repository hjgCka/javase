package com.hjg.formatter;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/20
 */
public class NumberFormatter {

    public static void main(String[] args) {
        //这个对象不是线程安全的
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());

        numberFormat.setGroupingUsed(true);
        numberFormat.setMaximumFractionDigits(2);

        System.out.println(numberFormat.format(50000.878));
        //50,000.88
    }
}
