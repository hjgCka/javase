package com.hjg.datetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/10/13
 */
public class DateApp {

    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        System.out.println(now.get(ChronoField.YEAR));

        //获取秒数
        LocalDateTime localDateTime = LocalDateTime.now();
        printSeconds(localDateTime);

        LocalDateTime yesterDay = localDateTime.minusHours(24);
        printSeconds(yesterDay);

        printSeconds(localDateTime.withHour(0).withMinute(0).withSecond(0).withNano(0));
    }

    private static void printSeconds(LocalDateTime localDateTime) {
        Long second = localDateTime.toEpochSecond(ZoneOffset.of("+8"));
        //获取毫秒数
        Long milliSecond = localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();

        System.out.println("second = " + second);
        System.out.println("milliSecond = " + milliSecond);
    }
}
