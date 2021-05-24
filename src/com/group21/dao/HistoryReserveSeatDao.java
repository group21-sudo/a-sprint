package com.group21.dao;

import com.group21.bean.HistoryReserveSeat;

import java.util.List;

public interface HistoryReserveSeatDao {
    /**
     * 获取全部HistoryReserveSeat对象
     * @return
     */
    public List<HistoryReserveSeat> getTotalData();

    /**
     * 获取最新的数据
     * @return
     */
    public HistoryReserveSeat getLatestData();

    /**
     * 根据时间获取数据
     * @param time
     * @return
     */
    public List<HistoryReserveSeat> getDataByTime(String time);

    public boolean updateNum(int op,int num,String time);
}
