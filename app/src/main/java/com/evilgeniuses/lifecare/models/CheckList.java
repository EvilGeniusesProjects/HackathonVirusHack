package com.evilgeniuses.lifecare.models;

import org.joda.time.DateTime;

public class CheckList implements Comparable<CheckList> {

    public String сheckID;
    public String сheckListName;
    public String сheckListDate;
    public String сheckListTime;
    public String сheckListComment;
    public String сheckListDuration;

    public String сheckListStartDate;
    public String сheckListEndDate;

    public int сheckListMilliseconds;

    boolean сheckListMonday;
    boolean сheckListTuesday;
    boolean сheckListWednesday;
    boolean сheckListThursday;
    boolean сheckListFriday;
    boolean сheckListSaturday;
    boolean сheckListSunday;
    boolean сheckListNotification;

    public CheckList(String сheckID, String сheckListName, String сheckListDate, String сheckListTime, String сheckListComment, String сheckListDuration, String сheckListStartDate, String сheckListEndDate, int сheckListMilliseconds, boolean сheckListMonday, boolean сheckListTuesday, boolean сheckListWednesday, boolean сheckListThursday, boolean сheckListFriday, boolean сheckListSaturday, boolean сheckListSunday, boolean сheckListNotification) {
        this.сheckID = сheckID;
        this.сheckListName = сheckListName;
        this.сheckListDate = сheckListDate;
        this.сheckListTime = сheckListTime;
        this.сheckListComment = сheckListComment;
        this.сheckListDuration = сheckListDuration;
        this.сheckListStartDate = сheckListStartDate;
        this.сheckListEndDate = сheckListEndDate;
        this.сheckListMilliseconds = сheckListMilliseconds;
        this.сheckListMonday = сheckListMonday;
        this.сheckListTuesday = сheckListTuesday;
        this.сheckListWednesday = сheckListWednesday;
        this.сheckListThursday = сheckListThursday;
        this.сheckListFriday = сheckListFriday;
        this.сheckListSaturday = сheckListSaturday;
        this.сheckListSunday = сheckListSunday;
        this.сheckListNotification = сheckListNotification;
    }


    @Override
    public int compareTo(CheckList comparestu) {
        int compareage=((CheckList)comparestu).getСheckListMilliseconds();
        return this.сheckListMilliseconds -  compareage;
    }



    public CheckList(){

    }


    public String getСheckID() {
        return сheckID;
    }

    public void setСheckID(String сheckID) {
        this.сheckID = сheckID;
    }

    public String getСheckListName() {
        return сheckListName;
    }

    public void setСheckListName(String сheckListName) {
        this.сheckListName = сheckListName;
    }

    public String getСheckListDate() {
        return сheckListDate;
    }

    public void setСheckListDate(String сheckListDate) {
        this.сheckListDate = сheckListDate;
    }

    public String getСheckListTime() {
        return сheckListTime;
    }

    public void setСheckListTime(String сheckListTime) {
        this.сheckListTime = сheckListTime;
    }

    public String getСheckListComment() {
        return сheckListComment;
    }

    public void setСheckListComment(String сheckListComment) {
        this.сheckListComment = сheckListComment;
    }

    public String getСheckListDuration() {
        return сheckListDuration;
    }

    public void setСheckListDuration(String сheckListDuration) {
        this.сheckListDuration = сheckListDuration;
    }

    public String getСheckListStartDate() {
        return сheckListStartDate;
    }

    public void setСheckListStartDate(String сheckListStartDate) {
        this.сheckListStartDate = сheckListStartDate;
    }

    public String getСheckListEndDate() {
        return сheckListEndDate;
    }

    public void setСheckListEndDate(String сheckListEndDate) {
        this.сheckListEndDate = сheckListEndDate;
    }

    public int getСheckListMilliseconds() {
        return сheckListMilliseconds;
    }

    public void setСheckListMilliseconds(int сheckListMilliseconds) {
        this.сheckListMilliseconds = сheckListMilliseconds;
    }

    public boolean isСheckListMonday() {
        return сheckListMonday;
    }

    public void setСheckListMonday(boolean сheckListMonday) {
        this.сheckListMonday = сheckListMonday;
    }

    public boolean isСheckListTuesday() {
        return сheckListTuesday;
    }

    public void setСheckListTuesday(boolean сheckListTuesday) {
        this.сheckListTuesday = сheckListTuesday;
    }

    public boolean isСheckListWednesday() {
        return сheckListWednesday;
    }

    public void setСheckListWednesday(boolean сheckListWednesday) {
        this.сheckListWednesday = сheckListWednesday;
    }

    public boolean isСheckListThursday() {
        return сheckListThursday;
    }

    public void setСheckListThursday(boolean сheckListThursday) {
        this.сheckListThursday = сheckListThursday;
    }

    public boolean isСheckListFriday() {
        return сheckListFriday;
    }

    public void setСheckListFriday(boolean сheckListFriday) {
        this.сheckListFriday = сheckListFriday;
    }

    public boolean isСheckListSaturday() {
        return сheckListSaturday;
    }

    public void setСheckListSaturday(boolean сheckListSaturday) {
        this.сheckListSaturday = сheckListSaturday;
    }

    public boolean isСheckListSunday() {
        return сheckListSunday;
    }

    public void setСheckListSunday(boolean сheckListSunday) {
        this.сheckListSunday = сheckListSunday;
    }

    public boolean isСheckListNotification() {
        return сheckListNotification;
    }

    public void setСheckListNotification(boolean сheckListNotification) {
        this.сheckListNotification = сheckListNotification;
    }
}
