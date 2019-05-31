package com.example.safi.muslimissues.model;

public class Quran {
    private int id;
    private int sura;
    private int aya;
    private String text;

    public Quran() {
    }

    public Quran(int id, int sura, int aya, String text) {
        this.id = id;
        this.sura = sura;
        this.aya = aya;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSura() {
        return sura;
    }

    public void setSura(int sura) {
        this.sura = sura;
    }

    public int getAya() {
        return aya;
    }

    public void setAya(int aya) {
        this.aya = aya;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
