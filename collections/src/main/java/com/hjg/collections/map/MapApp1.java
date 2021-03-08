package com.hjg.collections.map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class MapApp1 {

    private static final Logger logger = LoggerFactory.getLogger(MapApp1.class);

    public static void main(String[] args) {
        //TODO COW这个集合还没用过
        Map<String, String> map = new HashMap<>();
        map.put("key", "123");

        logger.info("map ={}", map);
    }
}
