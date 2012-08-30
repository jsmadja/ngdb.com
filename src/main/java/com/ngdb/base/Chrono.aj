package com.ngdb.base;

import org.apache.commons.lang.StringUtils;

public aspect Chrono {

    Object around() : execution(public * com.ngdb..*(..)) {
        long start = System.currentTimeMillis();
        try {
            return proceed();
        } finally {
            String prefix = "";
            Object method = thisJoinPointStaticPart.getSignature();
            long end = System.currentTimeMillis();
            long duration = end - start;
            if(duration > 100)
                prefix = "SLOW METHOD";
            if(duration > 500)
                prefix = "VERY SLOW METHOD";
            if(duration > 1000)
                prefix = "ULTRA SLOW METHOD";
            if(StringUtils.isNotBlank(prefix))
                System.err.print("Chronometer "+prefix+" "+method+" "+duration+" ms\r\n");
        }
    }

}