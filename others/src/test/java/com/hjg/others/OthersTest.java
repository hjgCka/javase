package com.hjg.others;

import org.junit.Test;

public class OthersTest {

    @Test
    public void randomTest() {
        String[] array = {"orange", "black", "green"};

        int count = 18;
        for(int i= 0; i<count; i++) {
            int index = (int)(Math.random()*10) % array.length;
            System.out.println("index = " + index);
        }
    }
}
