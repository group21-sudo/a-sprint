package com.group21.bean;

public class UserInfo {
    private int id;
    private String stu_id;
    private String name;
    private int signed_number;

    public UserInfo(int id, String stu_id, String name, int signed_number) {
        this.id = id;
        this.stu_id = stu_id;
        this.name = name;
        this.signed_number = signed_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSigned_number() {
        return signed_number;
    }

    public void setSigned_number(int signed_number) {
        this.signed_number = signed_number;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", stu_id='" + stu_id + '\'' +
                ", name='" + name + '\'' +
                ", signed_number=" + signed_number +
                '}';
    }
}
