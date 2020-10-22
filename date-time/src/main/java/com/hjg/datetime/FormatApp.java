package com.hjg.datetime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/10/19
 */
public class FormatApp {

    public static void main(String[] args) {
        //YYYYMMDDHHMISS
        String format = "yyyyMMddHHmmss";

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        System.out.println(df.format(now));

        //YYYYMMDD24HHMISS
        String format2 = "yyyyMMdd24HHmmss";
        DateTimeFormatter df2 = DateTimeFormatter.ofPattern(format2);
        System.out.println(df2.format(now));
    }
}
