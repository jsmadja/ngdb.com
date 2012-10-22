package com.ngdb.entities;

import org.hibernate.transform.ResultTransformer;

import java.math.BigInteger;
import java.util.List;

public class Top100ItemResultTransformer implements ResultTransformer {

    private Long oldCount = Long.MIN_VALUE;
    private Integer idx = 1;
    private String rank;

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        long id = ((BigInteger) tuple[0]).longValue();
        String title = tuple[1].toString();
        String platform = tuple[2].toString();
        Long count = ((BigInteger) tuple[3]).longValue();
        if (count == oldCount) {
            rank = ".";
        } else {
            rank = idx.toString() + ".";
        }
        idx++;
        oldCount = count;
        return new Top100Item(id, title, platform, count.toString(), rank);
    }

    @Override
    public List transformList(List collection) {
        return collection;
    }
}
