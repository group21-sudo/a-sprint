package com.group21.web.servlet;

import com.alibaba.fastjson.JSONObject;
import com.group21.service.UserInfoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        String stuId = request.getParameter("stu_id");
        String name = request.getParameter("name");
        Map<String, Object> map = new HashMap<String, Object>();
        if (stuId == null || name == null || stuId.length() == 0 || name.length() == 0) {
            map.put("status", "0");
        }else {
            boolean loginRes = new UserInfoService().login(stuId, name);
            if (loginRes) {
                map.put("status", "1");
            }else {
                map.put("status", "0");
            }
        }
        //map.put("status", "0");
        JSONObject jsonObject = new JSONObject(map);
        response.getWriter().write(jsonObject.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
