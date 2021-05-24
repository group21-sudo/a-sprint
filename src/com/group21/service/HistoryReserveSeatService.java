package com.group21.service;

import com.group21.bean.HistoryReserveSeat;
import com.group21.dao.impl.HistoryReserveSeatImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryReserveSeatService {

    private HistoryReserveSeatImpl historyReserveSeatImpl = new HistoryReserveSeatImpl();

    public int querySeatsNumbers() {

        HistoryReserveSeat latestData = historyReserveSeatImpl.getLatestData();
        if (latestData != null) {
            return latestData.getNumber();
        }
        return -1;
    }
    public boolean updateNum(int op, int num){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentTime = sdf.format(new Date());
        historyReserveSeatImpl.updateNum(op,num,currentTime);

        return true;
    }
}
