package com.group21.web.servlet;

import com.alibaba.fastjson.JSONObject;
import com.group21.bean.VisitInfo;
import com.group21.service.*;
import com.group21.service.FaceSearchService.FaceSearch;
import com.group21.utils.Base64Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/faceSearchServlet")
public class FaceSearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            in = new FileInputStream("/www/wwwroot/group21.cychenye.com/uploads/photo.jpg");
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (data == null || data.length == 0) {
            map.put("status","0");
            JSONObject jsonObject = new JSONObject(map);
            response.getWriter().write(jsonObject.toString());
            return;
        }
        String base64Data = Base64Util.encode(data);
        UserInfoService userInfoService = new UserInfoService();
        VisitInfoService visitInfoService = new VisitInfoService();
        ReserveInfoService reserveInfoService = new ReserveInfoService();
        HistoryReserveSeatService historyReserveSeatService = new HistoryReserveSeatService();
        String stuId = FaceSearch.faceSearch(base64Data);
        //System.out.println(stuId);
        if ("0".equals(stuId)) {
            map.put("status","0");
            JSONObject jsonObject = new JSONObject(map);
            response.getWriter().write(jsonObject.toString());
            return;
        }
        HistorySeatsUsageService historySeatsUsageService = new HistorySeatsUsageService();
        if (userInfoService.existUser(stuId) != null) {
            //存在人脸信息
            //查询历史进出信息
            map.put("status","1");
            map.put("stu_id",stuId);
            VisitInfo latestDataByStuId = visitInfoService.getLatestDataByStuId(stuId);
//            if (latestDataByStuId == null) {
//                response.getWriter().write("null");
//            }else {
//                response.getWriter().write(latestDataByStuId.toString());
//            }
            if (latestDataByStuId == null) {
                //如果是没有记录，则是第一次到访进来
                //System.out.println("1");
                map.put("msg","visit");
                visitInfoService.addVisitInfo(stuId);
                //historySeatsUsageService.updateNum(1,1);
                reserveInfoService.updateFlag(stuId,1);
            }else {
                //如果有记录，还需判断是进来还是出去
                //如果离开记录没有记录
                if ("-1".equals(latestDataByStuId.getLeave_time())){
                    //判断当前时间和进入时间的差值
                    //System.out.println("2");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String leaveHms = sdf.format(new Date()).substring(8);
                    String visitHms = latestDataByStuId.getVisit_time().substring(8);
                    int leaveHmsNum = Integer.parseInt(leaveHms);
                    int visitHmsNum = Integer.parseInt(visitHms);
                    if ((leaveHmsNum / 10000 * 60 * 60 + leaveHmsNum / 100 % 100 * 60 + leaveHmsNum % 100 - visitHmsNum / 10000 * 60 * 60 + visitHmsNum / 100 % 100 * 60 + visitHmsNum % 100) > 3600) {
                        //满足合法值，则添加到打卡记录
                        //System.out.println("3");
                        map.put("msg","leave");
                        visitInfoService.updateLeaveTime(latestDataByStuId.getId());
                        //historySeatsUsageService.updateNum(1,-1);
                        historyReserveSeatService.updateNum(1,1);
                        reserveInfoService.updateFlag(stuId,1);
                    }else {
                        //不合法则跳过
                        map.put("msg","time is too short");
                        //System.out.println("4");
                    }
                }else {
                    //如果离开记录有记录，则创建新的一条
                    //System.out.println("5");
                    map.put("msg","visit");
                    visitInfoService.addVisitInfo(stuId);
                    //historySeatsUsageService.updateNum(1,1);
                    reserveInfoService.updateFlag(stuId,1);
                }
            }
        }else {
            map.put("status","null");
            map.put("stu_id","null");
            map.put("msg","null");
        }
        JSONObject jsonObject = new JSONObject(map);
        response.getWriter().write(jsonObject.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
