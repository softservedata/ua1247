package com.softserve.edu07log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplLog {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(ApplLog.class); // org.slf4j.LoggerFactory
        logger.info("AppLog Message info");
        logger.debug("AppLog Message debug");
    }
}
