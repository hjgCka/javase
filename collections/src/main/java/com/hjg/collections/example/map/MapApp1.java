package com.hjg.collections.example.map;

import java.util.HashMap;
import java.util.Map;

public class MapApp1 {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("key", "123");

        System.out.println(map);
    }
}
