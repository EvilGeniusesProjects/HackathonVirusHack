package com.evilgeniuses.lifecare.models;

public class Diary implements Comparable<Diary>{
    private String id;
    private String date;
    private String imgage1;
    private String imgage2;
    private String imgage3;
    private String imgage4;
    private String imgage5;
    private String comment;
    private int days;
    private int pain;

    public Diary(String id, String date, String imgage1, String imgage2, String imgage3, String imgage4, String imgage5, String comment, int days, int pain) {
        this.id = id;
        this.date = date;
        this.imgage1 = imgage1;
        this.imgage2 = imgage2;
        this.imgage3 = imgage3;
        this.imgage4 = imgage4;
        this.imgage5 = imgage5;
        this.comment = comment;
        this.days = days;
        this.pain = pain;
    }

    @Override
    public int compareTo(Diary comparestu) {
        int compareage=((Diary)comparestu).getDays();
        return this.days -  compareage;
    }


    public Diary(){

    }

    public int getPain() {
        return pain;
    }

    public void setPain(int pain) {
        this.pain = pain;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImgage1() {
        return imgage1;
    }

    public void setImgage1(String imgage1) {
        this.imgage1 = imgage1;
    }

    public String getImgage2() {
        return imgage2;
    }

    public void setImgage2(String imgage2) {
        this.imgage2 = imgage2;
    }

    public String getImgage3() {
        return imgage3;
    }

    public void setImgage3(String imgage3) {
        this.imgage3 = imgage3;
    }

    public String getImgage4() {
        return imgage4;
    }

    public void setImgage4(String imgage4) {
        this.imgage4 = imgage4;
    }

    public String getImgage5() {
        return imgage5;
    }

    public void setImgage5(String imgage5) {
        this.imgage5 = imgage5;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
