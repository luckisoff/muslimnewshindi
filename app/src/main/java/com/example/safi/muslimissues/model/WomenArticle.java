package com.example.safi.muslimissues.model;

import java.util.List;

public class WomenArticle {
    private int current_page,from,last_page,per_page,to,total;
    private String first_page_url,last_page_url,next_page_url,prev_page_url,path;

    private List<Data> data;

    public WomenArticle(String path, int current_page, int from, int last_page, int per_page, int to, int total, String first_page_url, String last_page_url, String next_page_url, String prev_page_url, List<Data> data) {
        this.current_page = current_page;
        this.from = from;
        this.last_page = last_page;
        this.per_page = per_page;
        this.to = to;
        this.total = total;
        this.first_page_url = first_page_url;
        this.last_page_url = last_page_url;
        this.next_page_url = next_page_url;
        this.prev_page_url = prev_page_url;

        this.data = data;
        this.path=path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setFirst_page_url(String first_page_url) {
        this.first_page_url = first_page_url;
    }

    public void setLast_page_url(String last_page_url) {
        this.last_page_url = last_page_url;
    }

    public void setNext_page_url(String next_page_url) {
        this.next_page_url = next_page_url;
    }

    public void setPrev_page_url(String prev_page_url) {
        this.prev_page_url = prev_page_url;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }


}
