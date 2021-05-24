package com.group21.dao.impl;

import com.group21.bean.ReserveInfo;
import com.group21.dao.ReserveInfoDao;
import com.group21.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReserveInfoImpl implements ReserveInfoDao {
    @Override
    public List<ReserveInfo> getTotalData() {
        String queryStr = "select * from reserve_info";
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ReserveInfo reserveInfo = null;
        List<ReserveInfo> reserveInfos = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(queryStr);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reserveInfo = new ReserveInfo(resultSet.getInt("id"), resultSet.getString("stu_id"), resultSet.getString("reserve_time"),resultSet.getInt("flag"));
                reserveInfos.add(reserveInfo);
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
        return reserveInfos;
    }

    @Override
    public List<ReserveInfo> getDataByStuId(String stuId) {
        String queryStr = "select * from reserve_info where stu_id = ? order by reserve_time desc";
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ReserveInfo reserveInfo = null;
        List<ReserveInfo> reserveInfos = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(queryStr);
            preparedStatement.setString(1, stuId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reserveInfo = new ReserveInfo(resultSet.getInt("id"), resultSet.getString("stu_id"), resultSet.getString("reserve_time"),resultSet.getInt("flag"));
                reserveInfos.add(reserveInfo);
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
        return reserveInfos;
    }

    /**
     * 用like做
     * @param reserveTime
     * @return
     */
    @Override
    public List<ReserveInfo> getDataByReserveTime(String reserveTime) {
        String queryStr = "select * from reserve_info where reserve_time like ?";
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ReserveInfo reserveInfo = null;
        List<ReserveInfo> reserveInfos = new ArrayList<>();
        try {
            reserveTime = reserveTime+"%";
            preparedStatement = connection.prepareStatement(queryStr);
            preparedStatement.setString(1, reserveTime);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reserveInfo = new ReserveInfo(resultSet.getInt("id"), resultSet.getString("stu_id"), resultSet.getString("reserve_time"),resultSet.getInt("flag"));
                reserveInfos.add(reserveInfo);
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
        return reserveInfos;
    }

    @Override
    public ReserveInfo getLatestDataByStuIdAndTime(String stuId,String time) {
        String queryStr = "select * from reserve_info where stu_id = ? and reserve_time like ? order by reserve_time desc limit 1";
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ReserveInfo reserveInfo = null;
        try {
            preparedStatement = connection.prepareStatement(queryStr);
            preparedStatement.setString(1, stuId);
            preparedStatement.setString(2,time + "%");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                reserveInfo = new ReserveInfo(resultSet.getInt("id"), resultSet.getString("stu_id"), resultSet.getString("reserve_time"),resultSet.getInt("flag"));
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
        return reserveInfo;
    }

    @Override
    public boolean addReserveInfo(String stuId, String time) {
        ReserveInfo latestDataByStuIdAndTime = getLatestDataByStuIdAndTime(stuId, time);
        String insertStr = "insert into reserve_info(stu_id,reserve_time,flag) values(?,?,0)";
        Connection connection=DBConnection.getConnection();
        PreparedStatement preparedStatement=null;
        int i=-1;
        try {
            preparedStatement =connection.prepareStatement(insertStr);
            preparedStatement.setString(1,stuId);
            preparedStatement.setString(2,time);
            i= preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            if (preparedStatement!=null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (i>0)
            {
                return true;
            }else {
                return false;
            }
        }
    }

    @Override
    public boolean delReserveInfo(String stuId, String time) {

        ReserveInfo latestDataByStuIdAndTime = getLatestDataByStuIdAndTime(stuId, time);
        String deleteStr = "delete from reserve_info where id = ? ";
        Connection connection=DBConnection.getConnection();
        PreparedStatement preparedStatement=null;
        int i=-1;
        try {
            preparedStatement =connection.prepareStatement(deleteStr);
            preparedStatement.setInt(1,latestDataByStuIdAndTime.getId());
            i= preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            if (preparedStatement!=null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (i>0)
            {
                return true;
            }else {
                return false;
            }
        }
    }

    @Override
    public boolean updateFlag(String stuId, int op,String time) {
        ReserveInfo latestDataByStuIdAndTime = getLatestDataByStuIdAndTime(stuId, time);
        String updateStr = "update reserve_info set flag = ? where id = ? ";
        Connection connection=DBConnection.getConnection();
        PreparedStatement preparedStatement=null;
        int i=-1;
        try {
            preparedStatement =connection.prepareStatement(updateStr);
            preparedStatement.setInt(1,op);
            preparedStatement.setInt(2,latestDataByStuIdAndTime.getId());
            i= preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            if (preparedStatement!=null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (i>0)
            {
                return true;
            }else {
                return false;
            }
        }
    }

    @Override
    public boolean updateFlagByStuId(String stuId) {
        List<ReserveInfo> dataByStuId = getDataByStuId(stuId);
        for (ReserveInfo reserveInfo : dataByStuId) {
            if (reserveInfo.getFlag() == 0) {
                System.out.println(Integer.parseInt(reserveInfo.getReserve_time().substring(0,8)));
                System.out.println(Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date())));
                if (Integer.parseInt(reserveInfo.getReserve_time().substring(0,8)) < Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()))){
                    updateFlagById(reserveInfo.getId(),-1);
                    continue;
                }
                String currentHms = new SimpleDateFormat("HHmmss").format(new Date());
                String reserveTime = reserveInfo.getReserve_time().substring(8);
                int currentHmsNum = Integer.parseInt(currentHms);
                int reserveTimeNum = Integer.parseInt(reserveTime);
                if ((currentHmsNum / 10000 * 60 * 60 + currentHmsNum / 100 % 100 * 60 + currentHmsNum % 100) - (reserveTimeNum / 10000 * 60 * 60 + reserveTimeNum / 100 % 100 * 60 + reserveTimeNum % 100) > 1800) {
                    updateFlagById(reserveInfo.getId(),-1);
                }else {
                }
            }
        }
        return true;
    }

    @Override
    public boolean updateFlagById(int id,int op) {
        String updateStr = "update reserve_info set flag = ? where id = ? ";
        Connection connection=DBConnection.getConnection();
        PreparedStatement preparedStatement=null;
        int i=-1;
        try {
            preparedStatement =connection.prepareStatement(updateStr);
            preparedStatement.setInt(1,op);
            preparedStatement.setInt(2,id);
            i= preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            if (preparedStatement!=null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (i>0)
            {
                return true;
            }else {
                return false;
            }
        }
    }


    public static void main(String[] args) {
        ReserveInfoImpl reserveInfo = new ReserveInfoImpl();
//        List<ReserveInfo> dataByReserveTime = reserveInfo.getDataByReserveTime("20210515");
//        System.out.println(dataByReserveTime);
//        List<ReserveInfo> dataByStuId = reserveInfo.getDataByStuId("211806207");
//        System.out.println(dataByStuId);
//        List<ReserveInfo> totalData = reserveInfo.getTotalData();
//        System.out.println(totalData);
//        ReserveInfo latestDataByStuIdAndTime = reserveInfo.getLatestDataByStuIdAndTime("211806207", "20210515");
//        System.out.println(latestDataByStuIdAndTime);
//        boolean res = reserveInfo.addReserveInfo("211806207", "20210515162332");
//        boolean b = reserveInfo.delReserveInfo("211806207", "20210515");
//        boolean b = reserveInfo.updateFlag("211806207", 1, "20210515");
    }
}
