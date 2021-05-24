package com.group21.dao;

import com.group21.bean.ReserveInfo;

import java.util.List;

public interface ReserveInfoDao {
    /**
     * 获取全部ReserveInfo对象
     * @return
     */
    public List<ReserveInfo> getTotalData();


    /**
     * 根据学号获取预约信息
     * @param stuId
     * @return
     */
    public List<ReserveInfo> getDataByStuId(String stuId);


    /**
     * 根据预约时间获取预约信息
     * @param reserveTime
     * @return
     */
    public List<ReserveInfo> getDataByReserveTime(String reserveTime);

    public ReserveInfo getLatestDataByStuIdAndTime(String stuId,String time);

    public boolean addReserveInfo(String stuId,String time);

    public boolean delReserveInfo(String stuId,String time);

    public boolean updateFlag(String stuId,int op,String time);

    public boolean updateFlagByStuId(String stuId);

    public boolean updateFlagById(int id,int op);

}
