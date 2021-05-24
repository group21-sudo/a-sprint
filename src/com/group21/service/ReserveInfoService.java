package com.group21.service;

import com.group21.bean.ReserveInfo;
import com.group21.dao.impl.ReserveInfoImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReserveInfoService {

    private ReserveInfoImpl reserveInfoImpl = new ReserveInfoImpl();

    public ReserveInfo getLatestDataByStuId(String stuId){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String currentTime = sdf.format(new Date());
        return reserveInfoImpl.getLatestDataByStuIdAndTime(stuId,currentTime);
    }

    public boolean addReserveInfo(String stuId){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String currentTime = sdf.format(new Date());
        ReserveInfo latestDataByStuIdAndTime = reserveInfoImpl.getLatestDataByStuIdAndTime(stuId, currentTime);
        boolean res= false;
        if (latestDataByStuIdAndTime == null || latestDataByStuIdAndTime.getFlag() == 1){
            res = reserveInfoImpl.addReserveInfo(stuId, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            //System.out.println("111");
        }else if (latestDataByStuIdAndTime.getFlag() == 0){
            String currentHms = new SimpleDateFormat("HHmmss").format(new Date());
            String reserveTime = latestDataByStuIdAndTime.getReserve_time().substring(8);
            int currentHmsNum = Integer.parseInt(currentHms);
            int reserveTimeNum = Integer.parseInt(reserveTime);
            //System.out.println((currentHmsNum / 10000 * 60 * 60 + currentHmsNum / 100 % 100 * 60 + currentHmsNum % 100));
            //System.out.println((reserveTimeNum / 10000 * 60 * 60 + reserveTimeNum / 100 % 100 * 60 + reserveTimeNum % 100));
            if ((currentHmsNum / 10000 * 60 * 60 + currentHmsNum / 100 % 100 * 60 + currentHmsNum % 100) - (reserveTimeNum / 10000 * 60 * 60 + reserveTimeNum / 100 % 100 * 60 + reserveTimeNum % 100) > 1800) {
                reserveInfoImpl.updateFlag(stuId, -1, new SimpleDateFormat("yyyyMMdd").format(new Date()));
                res = reserveInfoImpl.addReserveInfo(stuId, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                //System.out.println("222");
            }else {
                res = false;
                //System.out.println("333");
            }
        }else {
            //System.out.println("444");
            res = reserveInfoImpl.addReserveInfo(stuId, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        }
        return res;
    }

    public boolean delReserveInfo(String stuId){
        ReserveInfo latestDataByStuIdAndTime = reserveInfoImpl.getLatestDataByStuIdAndTime(stuId, new SimpleDateFormat("yyyyMMdd").format(new Date()));
        boolean res = false;
        if (latestDataByStuIdAndTime.getFlag() == 1) {
            res = false;
        }else if (latestDataByStuIdAndTime.getFlag() == -1){
            res = false;
        }else {
            String currentHms = new SimpleDateFormat("HHmmss").format(new Date());
            String reserveTime = latestDataByStuIdAndTime.getReserve_time().substring(8);
            int currentHmsNum = Integer.parseInt(currentHms);
            int reserveTimeNum = Integer.parseInt(reserveTime);
            if ((currentHmsNum / 10000 * 60 * 60 + currentHmsNum / 100 % 100 * 60 + currentHmsNum % 100) - (reserveTimeNum / 10000 * 60 * 60 + reserveTimeNum / 100 % 100 * 60 + reserveTimeNum % 100) > 1800) {
                reserveInfoImpl.updateFlag(stuId, -1, new SimpleDateFormat("yyyyMMdd").format(new Date()));
                res = false;
            }else {
                res = reserveInfoImpl.delReserveInfo(stuId,new SimpleDateFormat("yyyyMMdd").format(new Date()));
            }
        }
        return res;
    }

    public boolean updateFlag(String stuId,int op){
        return reserveInfoImpl.updateFlag(stuId,op,new SimpleDateFormat("yyyyMMdd").format(new Date()));
    }

    public List<ReserveInfo> getHistoryReserveRecords(String stuId){
        reserveInfoImpl.updateFlagByStuId(stuId);
        return reserveInfoImpl.getDataByStuId(stuId);
    }

    public static void main(String[] args) {
        ReserveInfoService reserveInfoService = new ReserveInfoService();
        System.out.println(reserveInfoService.addReserveInfo("211806207"));
        //System.out.println(reserveInfoService.delReserveInfo("211806207"));
        List<ReserveInfo> historyReserveRecords = reserveInfoService.getHistoryReserveRecords("211806207");
        System.out.println(historyReserveRecords);
    }
}
