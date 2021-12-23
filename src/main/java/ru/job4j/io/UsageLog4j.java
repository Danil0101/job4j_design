package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsageLog4j {
    private static final Logger LOG = LoggerFactory.getLogger(UsageLog4j.class.getName());

    public static void main(String[] args) {
        LOG.trace("trace message");
        LOG.debug("debug message");
        LOG.info("info message");
        LOG.warn("warn message");
        LOG.error("error message");
        byte v1 = 1;
        short v2 = 2;
        int v3 = 3;
        long v4 = 4L;
        float v5 = 5F;
        double v6 = 6D;
        char v7 = '7';
        boolean v8 = true;
        LOG.debug("{} {} {} {} {} {} {} {}", v1, v2, v3, v4, v5, v6, v7, v8);
    }
}
