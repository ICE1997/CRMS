package com.ice.crms.db;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase{
    private static final String TAG = DataBase.class.getSimpleName();
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://47.106.132.194:3306/PRJCRMS?useUnicode=yes&characterEncoding=utf8";
    private static final String USER = "iclient";
    private static final String PASSWORD = "ice@1997";

    private DataBase(){}
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Log.d(TAG, "getConnection: WHTA?");
            Class.forName(DRIVER);
            Log.d(TAG, "getConnection: 正在连接数据库......");
            connection = DriverManager.getConnection(DB_URL,USER,PASSWORD);
            Log.d(TAG, "getConnection: 连接数据库成功!");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
