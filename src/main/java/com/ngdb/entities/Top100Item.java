package com.ngdb.entities;

public class Top100Item {

    private Long id;
    private String title;
    private String originTitle;
    private long count;
    private String rank;

    public Top100Item(Long id, String title, String originTitle, long count, String rank) {
        this.id = id;
        this.title = title;
        this.originTitle = originTitle;
        this.count = count;
        this.rank = rank;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginTitle() {
        return originTitle;
    }

    public long getCount() {
        return count;
    }

    public String getRank() {
        return rank;
    }
}
