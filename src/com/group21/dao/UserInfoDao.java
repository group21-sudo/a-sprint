package com.group21.dao;

import com.group21.bean.UserInfo;

import java.util.List;

public interface UserInfoDao {

    /**
     * 根据学号和姓名获取对象
     * @param stuId
     * @param name
     * @return
     */
    public UserInfo getDataByStuIdAndName(String stuId, String name);

    /**
     * 根据学号查询学生的打卡次数
     * @param stuId
     * @return
     */
    public UserInfo getSignedNumberByStuId(String stuId);


    public UserInfo getDataByStuId(String stuId);

    public List<UserInfo> signedNumberRankList(int number);

    public boolean updateSignedNumberByStuId(String stuId,int signedNumber);

    public List<String> getTotalStuId();

    public boolean resetSignedNumber();
}
