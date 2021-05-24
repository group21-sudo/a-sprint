package com.group21.service;

import com.group21.bean.UserInfo;
import com.group21.dao.UserInfoDao;
import com.group21.dao.impl.UserInfoImpl;

import java.util.List;

public class UserInfoService {

    private UserInfoImpl userInfoImpl = new UserInfoImpl();

    public boolean login(String stuId, String name) {

        UserInfo userinfo = userInfoImpl.getDataByStuIdAndName(stuId, name);
        if (userinfo != null) {
            return true;
        } else {
            return false;
        }
    }

    public UserInfo existUser(String stuId) {
        UserInfo userInfo = userInfoImpl.getDataByStuId(stuId);
        return userInfo;
    }

    public List<UserInfo> signedNumberRankList(int number){
        return userInfoImpl.signedNumberRankList(number);
    }

    public List<String> getTotalStuId(){
        return userInfoImpl.getTotalStuId();
    }

    public boolean resetSignedNumber(){
        return userInfoImpl.resetSignedNumber();
    }
}
