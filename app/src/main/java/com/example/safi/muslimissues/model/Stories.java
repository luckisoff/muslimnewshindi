package com.example.safi.muslimissues.model;

public class Stories {
    private Integer id;
    private String title;
    private String contents;
    private String created_at;

    public Stories(Integer id, String title, String contents, String created_at) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.created_at = created_at;
    }

    public Stories(Integer id, String title, String contents) {
        this.id = id;

        this.title = title;
        this.contents = contents;
    }

    public Integer getId() {
        return id;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String crated_at) {
        this.created_at = crated_at;
    }
}
