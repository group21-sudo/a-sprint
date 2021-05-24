package com.group21.dao.impl;

import com.group21.bean.HistorySeatsUsage;
import com.group21.dao.HistorySeatsUsageDao;
import com.group21.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistorySeatsUsageImpl implements HistorySeatsUsageDao {
    @Override
    public List<HistorySeatsUsage> getTotalData() {
        String queryStr = "select * from history_seats_usage";
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        HistorySeatsUsage historySeatsUsage = null;
        List<HistorySeatsUsage> historySeatsUsages = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(queryStr);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                historySeatsUsage = new HistorySeatsUsage(resultSet.getInt("id"), resultSet.getString("time"), resultSet.getInt("number"));
                historySeatsUsages.add(historySeatsUsage);
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
        return historySeatsUsages;
    }

    @Override
    public HistorySeatsUsage getLatestData() {
        String queryStr = "select * from history_seats_usage order by time desc limit 1";
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        HistorySeatsUsage historySeatsUsage = null;
        try {
            preparedStatement = connection.prepareStatement(queryStr);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                historySeatsUsage = new HistorySeatsUsage(resultSet.getInt("id"), resultSet.getString("time"), resultSet.getInt("number"));
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
        return historySeatsUsage;
    }

    /**
     * 用like去做    %
     * @param time
     * @return
     */
    @Override
    public List<HistorySeatsUsage> getDataByTime(String time) {
        String queryStr = "select * from history_seats_usage where time like ?";
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        HistorySeatsUsage historySeatsUsage = null;
        List<HistorySeatsUsage> historySeatsUsages = new ArrayList<>();
        try {
            time = time + "%";
            preparedStatement = connection.prepareStatement(queryStr);
            preparedStatement.setString(1, time);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                historySeatsUsage = new HistorySeatsUsage(resultSet.getInt("id"), resultSet.getString("time"), resultSet.getInt("number"));
                historySeatsUsages.add(historySeatsUsage);
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
        return historySeatsUsages;
    }

    /**
     * 建议通过getLatestData()获取最新数据
     * op:0 直接将最新数据更新为num，op:1，num作为偏移量
     *
     * @param op
     * @param num
     * @return
     */
    @Override
    public boolean updateNum(int op, int num,String time) {
        if (op != 0 && op != 1) {
            return false;
        }
        System.out.println(num + time);
        String insertStr = "insert into history_seats_usage(time,number) values(?,?)";
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        boolean flag = false;
        try {
            preparedStatement = connection.prepareStatement(insertStr);
            preparedStatement.setString(1,time);
            if (op == 0) {
                preparedStatement.setInt(2,num);
            } else if (op == 1) {
                preparedStatement.setInt(2,this.getLatestData().getNumber()+num);
            }else {

            }
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
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
        return flag;
    }


    public static void main(String[] args) {
        HistorySeatsUsageImpl historySeatsUsage = new HistorySeatsUsageImpl();
//        HistorySeatsUsage latestData = historySeatsUsage.getLatestData();
//        System.out.println(latestData);
//        List<HistorySeatsUsage> dataByTime = historySeatsUsage.getDataByTime("20210516");
//        System.out.println(dataByTime);
//        List<HistorySeatsUsage> totalData = historySeatsUsage.getTotalData();
//        for (HistorySeatsUsage totalDatum : totalData) {
//            System.out.println(totalDatum);
//        }
    }
}
