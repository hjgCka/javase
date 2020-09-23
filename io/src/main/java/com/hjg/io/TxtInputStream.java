package com.hjg.io;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/9/23
 */
public class TxtInputStream {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        InputStream is = TxtInputStream.class.getClassLoader().getResourceAsStream("order.txt");

        List<String> itemList = new ArrayList<>();
        int threshold = 1000;

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        br.lines().forEach(line -> {
            itemList.add(line);

            int length = itemList.size();
            if(length >= threshold) {
                System.out.println("得到1000条, [0] = " + itemList.get(0));
                itemList.clear();
            }
        });

        long spanTime = System.currentTimeMillis() - startTime;
        System.out.println("spanTime = " + spanTime);
    }
}
