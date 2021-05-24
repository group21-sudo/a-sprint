package com.group21.web.servlet;

import com.alibaba.fastjson.JSONObject;
import com.group21.bean.ReserveInfo;
import com.group21.service.ReserveInfoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/queryHistoryReserveInfoServlet")
public class QueryHistoryReserveInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        String stuId = request.getParameter("stu_id");
        String password = request.getParameter("password");
        Map<String, Object> map = new HashMap<String, Object>();
        if (stuId == null || stuId.length() == 0 || password == null || password.length() == 0 || !"group21password".equals(password)) {
            map.put("status","0");
        }else {
            List<ReserveInfo> historyReserveRecords = new ReserveInfoService().getHistoryReserveRecords(stuId);
            for (ReserveInfo historyReserveRecord : historyReserveRecords) {
                String reserve_time = historyReserveRecord.getReserve_time();
                String tmpTime = (reserve_time.substring(0, 4) + "/" + reserve_time.substring(4, 6) + "/" + reserve_time.substring(6, 8) + " " + reserve_time.substring(8, 10) + ":" + reserve_time.substring(10, 12) + ":"+  reserve_time.substring(12, 14));
                historyReserveRecord.setReserve_time(tmpTime);
            }
            map.put("status","1");
            map.put("info",historyReserveRecords);
        }
        JSONObject jsonObject = new JSONObject(map);
        response.getWriter().write(jsonObject.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
