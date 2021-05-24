package com.group21.dao;

import com.group21.bean.VisitInfo;

import java.util.List;

public interface VisitInfoDao {

    /**
     * 根据进出时间段获取数据
     * @param visitTime
     * @param leaveTime
     * @return
     */
    public List<VisitInfo> getDataByVisitTimeAndLeaveTime(String visitTime,String leaveTime);

    /**
     * 根据学号获取数据
     * @param stuId
     * @return
     */
    public List<VisitInfo> getDataByStuId(String stuId);


    public VisitInfo getLatestDataByStuId(String stuId,String visitTime);

    public boolean addVisitInfo(String stuId,String time);

    public boolean updateLeaveTime(int id,String leaveTime);
}
