package com.ngdb.base;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public aspect Chrono {

    private static final Logger LOG = LoggerFactory.getLogger(Chrono.class);

    Object around() : execution(public * com.ngdb..*(..)) {
        long start = System.currentTimeMillis();
        try {
            return proceed();
        } finally {
            String prefix = "";
            Object method = thisJoinPointStaticPart.getSignature();
            long end = System.currentTimeMillis();
            long duration = end - start;
            if(duration > 10)
                prefix = "SLOW METHOD";
            if(duration > 500)
                prefix = "VERY SLOW METHOD";
            if(duration > 1000)
                prefix = "ULTRA SLOW METHOD";
            if(StringUtils.isNotBlank(prefix) && !method.toString().contains("MDCFilter"))
                LOG.info(prefix+" "+method+" "+duration+" ms");
        }
    }

}