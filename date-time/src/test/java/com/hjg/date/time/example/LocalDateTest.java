package com.hjg.date.time.example;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class LocalDateTest {

    @Test
    public void date2LocalDateTest() {
        Date date = new Date();

        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();

        LocalDate localDate = instant.atZone(zoneId).toLocalDate();

        System.out.println(localDate.getMonthValue());
    }
}
