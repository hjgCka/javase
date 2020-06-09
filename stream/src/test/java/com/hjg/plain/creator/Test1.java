package com.hjg.plain.creator;

import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Test1 {

    @Test
    public void generate() {
        //Stream.of
        //Stream.generate
        //Stream.iterate

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");

        Stream.generate(new Supplier<String>() {
            int i = 0;

            @Override
            public String get() {
                LocalDate now = LocalDate.now().minusDays(i++);
                return now.format(dtf);
            }
        }).limit(7).forEach(str -> System.out.println(str));
    }
}
