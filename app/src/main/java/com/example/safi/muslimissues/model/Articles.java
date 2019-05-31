package com.example.safi.muslimissues.model;

import com.google.gson.annotations.SerializedName;

import java.security.Timestamp;

public class Articles {

    @SerializedName("id")
    private Integer id;
    @SerializedName("title")
    private String title;
    @SerializedName("contents")
    private String contents;
    @SerializedName("image")
    private String imgUrl;
    @SerializedName("source")
    private String source;
    @SerializedName("sourceurl")
    private String sourceUrl;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("short_content")
    private String short_content;

    public Articles(Integer id, String title, String contents, String imgUrl, String source, String sourceUrl, String created_at,String short_content) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.imgUrl = imgUrl;
        this.source = source;
        this.sourceUrl = sourceUrl;
        this.created_at = created_at;
        this.short_content=short_content;
    }

    public Integer getId() {
        return id;
    }

    public String getShort_content() {
        return short_content;
    }

    public void setShort_content(String short_content) {
        this.short_content = short_content;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
