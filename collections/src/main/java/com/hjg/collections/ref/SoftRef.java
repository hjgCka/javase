package com.hjg.collections.ref;

import java.lang.ref.SoftReference;
import java.util.Date;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/3/8
 */
public class SoftRef {

    public static void main(String[] args) {
        SoftReference<Date> dateSoftReference = new SoftReference<>(new Date());

        Date date = dateSoftReference.get();
        if(date == null) {
            System.out.println("gc cleared");
        } else {
            System.out.println(date);
        }
    }
}
