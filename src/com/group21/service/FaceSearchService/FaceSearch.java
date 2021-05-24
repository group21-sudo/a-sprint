
package com.group21.service.FaceSearchService;

import com.group21.utils.Base64Util;
import com.group21.utils.FileUtil;
import com.group21.utils.GsonUtils;
import com.group21.utils.HttpUtil;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * 人脸搜索
 */
public class FaceSearch {

    public static String faceSearch() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/search";
        String imagePath = "G:\\cs2.jpg";
        byte[] imgData = new byte[0];
        try {
            imgData = FileUtil.readFileByBytes(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String imageBase64 = Base64Util.encode(imgData);
        //System.out.println(imageBase64);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", imageBase64);
            map.put("liveness_control", "NORMAL");
            map.put("group_id_list", "ceshi");
            map.put("image_type", "BASE64");
            map.put("quality_control", "LOW");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String tmp = AuthService.getAuth();
            if (tmp == null || tmp.length() == 0) {
                tmp = "24.eef618214e2f3511846284e89f339487.2592000.1622705686.282335-24106333";
            }
            String accessToken = tmp;

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            //System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param imgData Base64的数据
     * @return
     */
    public static String faceSearch(String imgData) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/search";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", imgData);
            map.put("liveness_control", "NORMAL");
            map.put("group_id_list", "ceshi");
            map.put("image_type", "BASE64");
            map.put("quality_control", "LOW");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String tmp = AuthService.getAuth();
            if (tmp == null || tmp.length() == 0) {
                tmp = "24.eef618214e2f3511846284e89f339487.2592000.1622705686.282335-24106333";
            }
            String accessToken = tmp;

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            JSONObject object = (JSONObject) JSONObject.parse(result);
            if ("SUCCESS".equals(object.get("error_msg"))){
                Object o = object.getJSONObject("result").get("user_list");
                List<Object> list = (List<Object>) o;
                object = (JSONObject) list.get(0);
                //System.out.println(object);
                return object.get("user_id").toString();
            }
            else {
                //System.out.println("error");
                return "-1";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "-1";
    }

    public static void main(String[] args) {
        String res = FaceSearch.faceSearch();
        System.out.println("->" + res);
        JSONObject object = (JSONObject) JSONObject.parse(res);
        if (object.get("error_msg").equals("SUCCESS")){
            Object o = object.getJSONObject("result").get("user_list");
            List<Object> list = (List<Object>) o;
            object = (JSONObject) list.get(0);
            System.out.println(object);
            System.out.println(object.get("user_id"));
        }
        else {
            System.out.println("error");
        }
    }
}
