package com.evilgeniuses.lifecare.models;

public class Monitoring implements Comparable<Monitoring> {
    private String ID;
    private int Days;
    private String Date;
    private String Pressure;
    private String Pulse;
    private String Temperature;
    private String Defecation;
    private String Sleep;
    private String SkinDamage;
    private String Pain;
    private String Stroll;
    private String Leisure;
    private String Feeling;
    private String Comment;

    public Monitoring(String ID, int days, String date, String pressure, String pulse, String temperature, String defecation, String sleep, String skinDamage, String pain, String stroll, String leisure, String feeling, String comment) {
        this.ID = ID;
        Days = days;
        Date = date;
        Pressure = pressure;
        Pulse = pulse;
        Temperature = temperature;
        Defecation = defecation;
        Sleep = sleep;
        SkinDamage = skinDamage;
        Pain = pain;
        Stroll = stroll;
        Leisure = leisure;
        Feeling = feeling;
        Comment = comment;
    }

    public Monitoring() {

    }

    @Override
    public int compareTo(Monitoring comparestu) {
        int compareage = ((Monitoring) comparestu).getDays();
        return this.Days - compareage;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getDays() {
        return Days;
    }

    public void setDays(int days) {
        Days = days;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getPressure() {
        return Pressure;
    }

    public void setPressure(String pressure) {
        Pressure = pressure;
    }

    public String getPulse() {
        return Pulse;
    }

    public void setPulse(String pulse) {
        Pulse = pulse;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String temperature) {
        Temperature = temperature;
    }

    public String getDefecation() {
        return Defecation;
    }

    public void setDefecation(String defecation) {
        Defecation = defecation;
    }

    public String getSleep() {
        return Sleep;
    }

    public void setSleep(String sleep) {
        Sleep = sleep;
    }

    public String getSkinDamage() {
        return SkinDamage;
    }

    public void setSkinDamage(String skinDamage) {
        SkinDamage = skinDamage;
    }

    public String getPain() {
        return Pain;
    }

    public void setPain(String pain) {
        Pain = pain;
    }

    public String getStroll() {
        return Stroll;
    }

    public void setStroll(String stroll) {
        Stroll = stroll;
    }

    public String getLeisure() {
        return Leisure;
    }

    public void setLeisure(String leisure) {
        Leisure = leisure;
    }

    public String getFeeling() {
        return Feeling;
    }

    public void setFeeling(String feeling) {
        Feeling = feeling;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
