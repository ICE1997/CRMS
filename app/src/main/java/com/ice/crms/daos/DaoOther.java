package com.ice.crms.daos;

import com.ice.crms.db.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoOther {
    private static final String StatusTB = "ClientStatus";
    private static final String TypeTB = "ClientType";

    public static ArrayList<String> getAllClientTypes() {
        Connection conn = DataBase.getConnection();
        ArrayList<String> types = new ArrayList<>();
        try {
            PreparedStatement pstmt = conn.prepareStatement(" SELECT * FROM " + TypeTB);
            ResultSet res = pstmt.executeQuery();
            while (res.next()) {
                types.add(res.getString("typeName"));
            }
            return types;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return types;
    }

    public static ArrayList<String> getAllClientStatus() {
        Connection conn = DataBase.getConnection();
        ArrayList<String> status = new ArrayList<>();
        try {
            PreparedStatement pstmt = conn.prepareStatement(" SELECT * FROM " + StatusTB);
            ResultSet res = pstmt.executeQuery();
            while (res.next()) {
                status.add(res.getString("statusName"));
            }
            return status;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static String getStatusNameByNo(int i) {
        Connection conn = DataBase.getConnection();
        String result = "";
        try {
            PreparedStatement pstmt = conn.prepareStatement(" SELECT statusName FROM " + StatusTB + " WHERE statusNo = " + i);
            ResultSet res = pstmt.executeQuery();
            if (res.next()) {
                result = res.getString("statusName");
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getTypeNameByNo(int i) {
        Connection conn = DataBase.getConnection();
        String result = "";
        try {
            PreparedStatement pstmt = conn.prepareStatement(" SELECT typeName FROM " + TypeTB + " WHERE typeNo = " + i);
            ResultSet res = pstmt.executeQuery();
            if (res.next()) {
                result = res.getString("typeName");
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
