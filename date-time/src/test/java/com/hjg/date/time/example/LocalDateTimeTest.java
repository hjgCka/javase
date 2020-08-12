package com.hjg.date.time.example;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class LocalDateTimeTest {

    @Test
    public void toLong() {
        //获取毫秒数
        long createTime = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println("createTime = " + createTime);

        //获取秒数
        long second = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        System.out.println("second = " + second);
    }
}
