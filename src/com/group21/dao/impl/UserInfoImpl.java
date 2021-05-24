package com.group21.dao.impl;

import com.group21.bean.UserInfo;
import com.group21.dao.UserInfoDao;
import com.group21.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserInfoImpl implements UserInfoDao {
    @Override
    public UserInfo getDataByStuIdAndName(String stuId, String name) {
        String queryStr = "select * from user_info where stu_id = ? and name =?";
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        UserInfo userInfo = null;
        try {
            preparedStatement = connection.prepareStatement(queryStr);
            preparedStatement.setString(1, stuId);
            preparedStatement.setString(2, name);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userInfo = new UserInfo(resultSet.getInt("id"), resultSet.getString("stu_id"), resultSet.getString("name"), resultSet.getInt("signed_number"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return userInfo;
    }

    @Override
    public UserInfo getSignedNumberByStuId(String stuId) {
        String queryStr = "select * from user_info where stu_id = ?";
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        UserInfo userInfo = null;
        try {
            preparedStatement = connection.prepareStatement(queryStr);
            preparedStatement.setString(1, stuId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userInfo = new UserInfo(resultSet.getInt("id"), resultSet.getString("stu_id"), resultSet.getString("name"), resultSet.getInt("signed_number"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return userInfo;
    }

    @Override
    public UserInfo getDataByStuId(String stuId) {
        String queryStr = "select * from user_info where stu_id = ?";
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        UserInfo userInfo = null;
        try {
            preparedStatement = connection.prepareStatement(queryStr);
            preparedStatement.setString(1, stuId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userInfo = new UserInfo(resultSet.getInt("id"), resultSet.getString("stu_id"), resultSet.getString("name"), resultSet.getInt("signed_number"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return userInfo;

    }

    /**
     * 获取打卡次数最高的前n位对象
     * @param number
     * @return
     */
    @Override
    public List<UserInfo> signedNumberRankList(int number) {
        String queryStr = "select * from user_info order by signed_number desc limit ?";
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        UserInfo userInfo = null;
        List<UserInfo> userInfos = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(queryStr);
            preparedStatement.setInt(1, number);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userInfo = new UserInfo(resultSet.getInt("id"), resultSet.getString("stu_id"), resultSet.getString("name"), resultSet.getInt("signed_number"));
                userInfos.add(userInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return userInfos;
    }

    @Override
    public boolean updateSignedNumberByStuId(String stuId, int signedNumber) {
        String updateStr = "update user_info set signed_number = ? where stu_id = ?";
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        int i = -1;
        try {
            preparedStatement = connection.prepareStatement(updateStr);
            preparedStatement.setInt(1,signedNumber);
            preparedStatement.setString(2,stuId);
            i = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (i>0){
                return true;
            }else {
                return false;
            }
        }
    }

    @Override
    public List<String> getTotalStuId() {
        String queryStr = "select distinct stu_id from user_info";
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        UserInfo userInfo = null;
        List<String> strings = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(queryStr);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                strings.add(resultSet.getString("stu_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return strings;
    }

    @Override
    public boolean resetSignedNumber() {
        String updateStr = "update user_info set signed_number = 0";
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        int i = -1;
        try {
            preparedStatement = connection.prepareStatement(updateStr);
            i = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (i>0){
                return true;
            }else {
                return false;
            }
        }
    }


    public static void main(String[] args) {
        UserInfoImpl userInfo = new UserInfoImpl();
//        UserInfo dataByStuId = userInfo.getDataByStuId("211806207");
//        System.out.println(dataByStuId);
//        UserInfo userInfo1 = userInfo.getDataByStuIdAndName("211806207", "陈烨");
//        System.out.println(userInfo1);
//        UserInfo signedNumberByStuId = userInfo.getSignedNumberByStuId("211806207");
//        System.out.println(signedNumberByStuId);
//        List<UserInfo> userInfos = userInfo.signedNumberRankList(5);
////        for (UserInfo info : userInfos) {
////            System.out.println(info);
////        }
//        System.out.println(userInfo.getTotalStuId());
//        userInfo.updateSignedNumberByStuId("211806205",100);
        userInfo.resetSignedNumber();
    }
}
