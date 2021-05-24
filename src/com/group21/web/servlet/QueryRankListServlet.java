package com.group21.web.servlet;

import com.alibaba.fastjson.JSONObject;
import com.group21.bean.UserInfo;
import com.group21.service.UserInfoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/queryRankListServlet")
public class QueryRankListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        String number = request.getParameter("number");
        Map<String, Object> map = new HashMap<String, Object>();
        if (number == null || number.length() == 0 || Integer.parseInt(number)<0) {
            map.put("status","0");
            map.put("rankList","");
        }else {
            List<UserInfo> userInfos = new UserInfoService().signedNumberRankList(Integer.parseInt(number));
            for (UserInfo userInfo : userInfos) {
                userInfo.setName("");
            }
            map.put("status","1");
            map.put("rankList",userInfos);
        }
        //JSON封装
        JSONObject jsonObject = new JSONObject(map);
        response.getWriter().write(jsonObject.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
