package com.group21.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static String url="jdbc:mysql://localhost:3306/group21?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";

    private static String userName="group21";

    private static String passWord="group21password";

    private  static  String driverName="com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        Connection connection= null;
        try {
            connection = DriverManager.getConnection(url,userName,passWord);

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return connection;
    }
}
