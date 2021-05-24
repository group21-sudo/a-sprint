package com.group21.dao.impl;

import com.group21.bean.HistoryReserveSeat;
import com.group21.bean.HistorySeatsUsage;
import com.group21.dao.HistoryReserveSeatDao;
import com.group21.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoryReserveSeatImpl implements HistoryReserveSeatDao {
    @Override
    public List<HistoryReserveSeat> getTotalData() {
        String queryStr = "select * from history_reserve_seat";
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        HistoryReserveSeat historyReserveSeat = null;
        List<HistoryReserveSeat> historyReserveSeats = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(queryStr);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                historyReserveSeat = new HistoryReserveSeat(resultSet.getInt("id"), resultSet.getString("time"),  resultSet.getInt("number"));
                historyReserveSeats.add(historyReserveSeat);
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
        return historyReserveSeats;
    }

    @Override
    public HistoryReserveSeat getLatestData() {
        String queryStr = "select * from history_reserve_seat order by time desc limit 1";
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        HistoryReserveSeat historyReserveSeat = null;
        try {
            preparedStatement = connection.prepareStatement(queryStr);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                historyReserveSeat = new HistoryReserveSeat(resultSet.getInt("id"), resultSet.getString("time"),  resultSet.getInt("number"));
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
        return historyReserveSeat;
    }

    /**
     * 用like去做，记得加   %
     * @param time
     * @return
     */
    @Override
    public List<HistoryReserveSeat> getDataByTime(String time) {
        String queryStr = "select * from history_reserve_seat where time like ?";
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        HistoryReserveSeat historyReserveSeat = null;
        List<HistoryReserveSeat> historyReserveSeats = new ArrayList<>();
        try {
            time = time + "%";
            preparedStatement = connection.prepareStatement(queryStr);
            preparedStatement.setString(1,time);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                historyReserveSeat = new HistoryReserveSeat(resultSet.getInt("id"), resultSet.getString("time"),  resultSet.getInt("number"));
                historyReserveSeats.add(historyReserveSeat);
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
        return historyReserveSeats;
    }
    /**
     * 建议通过getLatestData()获取最新数据
     * op:0 直接将最新数据更新为num，op:1，num作为偏移量
     * @param op
     * @param num
     * @return
     */
    @Override
    public boolean updateNum(int op, int num,String time) {
        if (op != 0 && op != 1) {
            return false;
        }

        String insertStr = "insert into history_reserve_seat(time,number) values(?,?)";
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
        HistoryReserveSeatImpl historyReserveSeat = new HistoryReserveSeatImpl();
//        List<HistoryReserveSeat> totalData = historyReserveSeat.getTotalData();
//        for (HistoryReserveSeat totalDatum : totalData) {
//            System.out.println(totalDatum);
//        }
//        HistoryReserveSeat latestData = historyReserveSeat.getLatestData();
//        System.out.println(latestData);
//        List<HistoryReserveSeat> dataByTime = historyReserveSeat.getDataByTime("2021");
//        for (HistoryReserveSeat reserveSeat : dataByTime) {
//            System.out.println(reserveSeat);
//        }
//        boolean res = historyReserveSeat.updateNum(1, 5);
//        System.out.println(res);
//        System.out.println(historyReserveSeat.getLatestData());
    }
}
