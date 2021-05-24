package com.group21.dao;

import com.group21.bean.HistorySeatsUsage;

import java.util.List;

public interface HistorySeatsUsageDao {
    /**
     * 获取全部HistorySeatsUsage对象
     * @return
     */
    public List<HistorySeatsUsage> getTotalData();

    /**
     * 获取最新的数据
     * @return
     */
    public HistorySeatsUsage getLatestData();

    /**
     * 根据时间获取数据
     * @param time
     * @return
     */
    public List<HistorySeatsUsage> getDataByTime(String time);

    public boolean updateNum(int op,int num,String time);
}
