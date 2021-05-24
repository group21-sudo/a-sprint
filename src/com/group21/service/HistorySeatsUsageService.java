package com.group21.service;

import com.group21.bean.HistoryReserveSeat;
import com.group21.bean.HistorySeatsUsage;
import com.group21.dao.impl.HistorySeatsUsageImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HistorySeatsUsageService {

    private HistorySeatsUsageImpl historySeatsUsageImpl = new HistorySeatsUsageImpl();


    public int querySeatsNumbers() {

        HistorySeatsUsage latestData = historySeatsUsageImpl.getLatestData();
        if (latestData != null) {
            return latestData.getNumber();
        }
        return -1;
    }

    public boolean updateNum(int op, int num){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentTime = sdf.format(new Date());
        return historySeatsUsageImpl.updateNum(op,num,currentTime);


    }
}
