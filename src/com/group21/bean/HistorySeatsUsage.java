package com.group21.bean;

public class HistorySeatsUsage {
    private int id;
    private String time;
    private int number;

    public HistorySeatsUsage(int id, String time, int number) {
        this.id = id;
        this.time = time;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "HistorySeatsUsage{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", number=" + number +
                '}';
    }
}
