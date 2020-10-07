package com.hjg.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/10/7
 */
public class DemoApp {

    private static final Logger logger = LoggerFactory.getLogger(DemoApp.class);

    public static void main(String[] args) {
        logger.trace("trace");
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
    }

}
