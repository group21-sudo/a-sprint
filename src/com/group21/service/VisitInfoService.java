package com.group21.service;

import com.group21.bean.VisitInfo;
import com.group21.dao.impl.UserInfoImpl;
import com.group21.dao.impl.VisitInfoImpl;

import java.text.SimpleDateFormat;
import java.util.*;

public class VisitInfoService {

    private VisitInfoImpl visitInfoImpl = new VisitInfoImpl();
    private UserInfoImpl userInfoImpl = new UserInfoImpl();

    public List<VisitInfo> getDataByStuId(String stuId){
        return visitInfoImpl.getDataByStuId(stuId);
    }

    public VisitInfo getLatestDataByStuId(String stuId){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String currentTime = sdf.format(new Date());
        return visitInfoImpl.getLatestDataByStuId(stuId,currentTime);
    }

    public boolean addVisitInfo(String stuId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String visitTime = sdf.format(new Date());
        return visitInfoImpl.addVisitInfo(stuId,visitTime);
    }

    public boolean updateRankList(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar=Calendar.getInstance();
        Date currentTime = calendar.getTime();
        calendar.add(Calendar.MONTH,-1);
        Date preTime = calendar.getTime();
        String currentTimeStr = sdf.format(currentTime);
        String preTimeStr = sdf.format(preTime);
        List<VisitInfo> visitInfos = new VisitInfoImpl().getDataByVisitTimeAndLeaveTime(preTimeStr, currentTimeStr);
//        System.out.println("原始数据");
//        for (VisitInfo visitInfo : visitInfos) {
//            System.out.println(visitInfo);
//        }
        HashMap<String, HashSet<String>> map = new HashMap<>();
        for (VisitInfo visitInfo : visitInfos) {
            map.put(visitInfo.getStu_id(), new HashSet<>());
        }
        for (VisitInfo visitInfo : visitInfos) {
            HashSet<String> time = map.get(visitInfo.getStu_id());
            if (!"-1".equals(visitInfo.getLeave_time())){
                time.add(visitInfo.getVisit_time().substring(0,8));
            }
        }
        Set<String> stu_id = map.keySet();
        userInfoImpl.resetSignedNumber();
        boolean res = false;
        for (String key : stu_id) {
            //System.out.println(key + ":" +  map.get(key).size());
            res = userInfoImpl.updateSignedNumberByStuId(key, map.get(key).size());
        }
        return res;
    }

    public boolean updateLeaveTime(int id){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentTime = sdf.format(new Date());
        return visitInfoImpl.updateLeaveTime(id,currentTime);
    }

    public static void main(String[] args) {

    }
}
