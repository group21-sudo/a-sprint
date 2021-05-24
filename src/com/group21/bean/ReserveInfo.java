package com.group21.bean;

public class ReserveInfo {
    private int id;
    private String stu_id;
    private String reserve_time;
    private int flag;

    public ReserveInfo(int id, String stu_id, String reserve_time, int flag) {
        this.id = id;
        this.stu_id = stu_id;
        this.reserve_time = reserve_time;
        this.flag = flag;
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

    public String getReserve_time() {
        return reserve_time;
    }

    public void setReserve_time(String reserve_time) {
        this.reserve_time = reserve_time;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "ReserveInfo{" +
                "id=" + id +
                ", stu_id='" + stu_id + '\'' +
                ", reserve_time='" + reserve_time + '\'' +
                ", flag=" + flag +
                '}';
    }
}
