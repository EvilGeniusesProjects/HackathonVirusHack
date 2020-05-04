package com.evilgeniuses.lifecare.models;

public class Monitoring implements Comparable<Monitoring>{
    public String ID;
    public int Days;
    public String Date;
    public String Pressure;
    public String Pulse;
    public String Temperature;
    public String Defecation;
    public String Sleep;
    public String SkinDamage;
    public String Pain;
    public String Stroll;
    public String Leisure;

    public Monitoring(String ID, int milliseconds, String date, String pressure, String pulse, String temperature, String defecation, String sleep, String skinDamage, String pain, String stroll, String leisure) {
        this.ID = ID;
        Days = milliseconds;
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
    }

    Monitoring(){

    }

    @Override
    public int compareTo(Monitoring comparestu) {
        int compareage=((Monitoring)comparestu).getDays();
        return this.Days -  compareage;
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

}
