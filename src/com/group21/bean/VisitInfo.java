package com.group21.bean;

public class VisitInfo {
    private int id;
    private String stu_id;
    private String visit_time;
    private String leave_time;

    public VisitInfo(int id, String stu_id, String visit_time, String leave_time) {
        this.id = id;
        this.stu_id = stu_id;
        this.visit_time = visit_time;
        this.leave_time = leave_time;
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

    public String getVisit_time() {
        return visit_time;
    }

    public void setVisit_time(String visit_time) {
        this.visit_time = visit_time;
    }

    public String getLeave_time() {
        return leave_time;
    }

    public void setLeave_time(String leave_time) {
        this.leave_time = leave_time;
    }

    @Override
    public String toString() {
        return "VisitInfo{" +
                "id=" + id +
                ", stu_id='" + stu_id + '\'' +
                ", visit_time='" + visit_time + '\'' +
                ", leave_time='" + leave_time + '\'' +
                '}';
    }
}
