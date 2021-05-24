package com.group21.dao.impl;

import com.group21.bean.VisitInfo;
import com.group21.dao.VisitInfoDao;
import com.group21.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VisitInfoImpl implements VisitInfoDao {
    @Override
    public List<VisitInfo> getDataByVisitTimeAndLeaveTime(String visitTime, String leaveTime) {
        String queryStr = "select * from visit_info where visit_time >= ? and leave_time <= ?";
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        VisitInfo visitInfo = null;
        List<VisitInfo> visitInfos = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(queryStr);
            preparedStatement.setString(1, visitTime);
            preparedStatement.setString(2, leaveTime);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                visitInfo = new VisitInfo(resultSet.getInt("id"), resultSet.getString("stu_id"), resultSet.getString("visit_time"), resultSet.getString("leave_time"));
                //System.out.println(visitInfo);
                visitInfos.add(visitInfo);
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
        return visitInfos;
    }

    @Override
    public List<VisitInfo> getDataByStuId(String stuId) {
        String queryStr = "select * from visit_info where stu_id = ?";
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        VisitInfo visitInfo = null;
        List<VisitInfo> visitInfos = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(queryStr);
            preparedStatement.setString(1, stuId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                visitInfo = new VisitInfo(resultSet.getInt("id"), resultSet.getString("stu_id"), resultSet.getString("visit_time"), resultSet.getString("leave_time"));
                visitInfos.add(visitInfo);
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
        return visitInfos;
    }

    /**
     * 根据visitTime获取最新记录，注意，这里的visitTime精确到年月日，不存在时分秒信息，需用like查询。若当日有多条数据，请返回最新的visitTime记录
     * @param stuId
     * @param visitTime
     * @return
     */
    @Override
    public VisitInfo getLatestDataByStuId(String stuId,String visitTime) {
        String queryStr = "select * from visit_info where stu_id = ? and visit_time like ? order by id desc limit 1";
        Connection connection = DBConnection.getConnection();
        visitTime += "%";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        VisitInfo visitInfo = null;
        try {
            preparedStatement = connection.prepareStatement(queryStr);
            preparedStatement.setString(1, stuId);
            preparedStatement.setString(2,visitTime);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                visitInfo = new VisitInfo(resultSet.getInt("id"), resultSet.getString("stu_id"), resultSet.getString("visit_time"), resultSet.getString("leave_time"));
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
        return visitInfo;
    }

    /**
     * 新增到访记录
     * @param stuId
     * @param visitTime 精确到年月日时分秒，leaveTime请置空或置为特殊值，该函数写完后，请在这里补充leaveTime的填充值 leaveTime填充值为-1
     * @return
     */
    @Override
    public boolean addVisitInfo(String stuId, String visitTime) {
        String insertStr = "insert into visit_info(stu_id,visit_time,leave_time) values(?,?,?)";
        Connection connection=DBConnection.getConnection();
        PreparedStatement preparedStatement=null;
        int i=-1;
        try {
            preparedStatement =connection.prepareStatement(insertStr);
            preparedStatement.setString(1,stuId);
            preparedStatement.setString(2,visitTime);
            preparedStatement.setString(3,"-1");
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
    public boolean updateLeaveTime(int id, String leaveTime) {
        String updateStr = "update visit_info set leave_time = ? where id = ?";
        Connection connection=DBConnection.getConnection();
        PreparedStatement preparedStatement=null;
        int i=-1;
        try {
            preparedStatement =connection.prepareStatement(updateStr);
            preparedStatement.setString(1,leaveTime);
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
        VisitInfoImpl visitInfo = new VisitInfoImpl();
//        List<VisitInfo> dataByVisitTimeAndLeaveTime = visitInfo.getDataByVisitTimeAndLeaveTime("20210000000000", "202200000000");
//        System.out.println(dataByVisitTimeAndLeaveTime);
//        List<VisitInfo> dataByStuId = visitInfo.getDataByStuId("211806205");
//        for (VisitInfo info : dataByStuId) {
//            System.out.println(info);
//        }
//        boolean res = visitInfo.addVisitInfo("211806207", "20201020202020");
//        VisitInfo latestDataByStuId = visitInfo.getLatestDataByStuId("211806207","20210515");
//        System.out.println(latestDataByStuId);
//        boolean res = visitInfo.updateLeaveTime(141, "20202020202020");
//        System.out.println(res);
    }

}
