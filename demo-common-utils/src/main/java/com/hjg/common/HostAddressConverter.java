package com.hjg.common;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.security.krb5.internal.HostAddress;

import java.net.InetAddress;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/12/21
 */
public class HostAddressConverter extends ClassicConverter {

    private static final Logger logger = LoggerFactory.getLogger(HostAddress.class);

    @Override
    public String convert(ILoggingEvent event) {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            logger.error("获取本地主机IP异常", e);
        }
        return null;
    }
}
