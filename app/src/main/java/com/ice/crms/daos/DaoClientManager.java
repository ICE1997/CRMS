package com.ice.crms.daos;

import com.ice.crms.db.DataBase;
import com.ice.crms.models.ClientRelation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class DaoClientManager {
    private static final String TB = "Client";

    public static LinkedList<ClientRelation> getAllRelations() {
        int clientNo;
        String clientName;
        int clientType;
        int clientStatus;
        long date;
        String clientAddr;
        LinkedList<ClientRelation> clientRelations = new LinkedList<>();

        Connection conn = DataBase.getConnection();

        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM " + TB);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                clientNo = resultSet.getInt("clientNo");
                clientName = resultSet.getString("clientName");
                clientType = resultSet.getInt("clientType");
                clientStatus = resultSet.getInt("clientStatus");
                date = resultSet.getDate("buildRelTime").getTime();
                clientAddr = resultSet.getString("clientAddr");
                clientRelations.add(new ClientRelation(clientNo, clientName, clientType, clientStatus, date, clientAddr));
            }
            resultSet.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return clientRelations;
    }

    public static Boolean addRelation(ClientRelation relation) {
        int clientNo = relation.getClientNo();
        String clientName = relation.getClientName();
        int clientType = relation.getClientType();
        int clientStatus = relation.getClientStatus();
        long date = relation.getDate();
        String clientAddr = relation.getClientAddr();
        Boolean result = false;
        Connection conn = DataBase.getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO " + TB + " ( clientNo, clientName, clientType, clientStatus, buildRelTime ,clientAddr ) VALUES(?,?,?,?,?,?) ");
            pstmt.setInt(1, clientNo);
            pstmt.setString(2, clientName);
            pstmt.setInt(3, clientType);
            pstmt.setInt(4, clientStatus);
            pstmt.setDate(5, new java.sql.Date(date));
            pstmt.setString(6, clientAddr);
            int res = pstmt.executeUpdate();

            System.out.println(res);

            if (res > 0) {
                result = true;
            }

            conn.close();
            pstmt.close();

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static boolean modifyRelation(ClientRelation relation) {
        int clientNo = relation.getClientNo();
        String clientName = relation.getClientName();
        int clientType = relation.getClientType();
        int clientStatus = relation.getClientStatus();
        long date = relation.getDate();
        String clientAddr = relation.getClientAddr();

        boolean result = false;

        Connection conn = DataBase.getConnection();

        try {
            PreparedStatement pstmt = conn.prepareStatement("UPDATE " + TB + " SET clientName = ?, clientType = ?, clientStatus = ?, buildRelTime = ? ,clientAddr = ? WHERE clientNo = ? ");
            pstmt.setString(1, clientName);
            pstmt.setInt(2, clientType);
            pstmt.setInt(3, clientStatus);
            pstmt.setDate(4, new java.sql.Date(date));
            pstmt.setString(5, clientAddr);
            pstmt.setInt(6, clientNo);
            int res = pstmt.executeUpdate();

            if (res > 0) {
                result = true;
            }

            pstmt.close();
            conn.close();

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static boolean deleteRelationByNO(int clientNo) {

        Connection conn = DataBase.getConnection();

        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM " + TB + " WHERE clientNo = ? ");
            pstmt.setInt(1, clientNo);
            int res = pstmt.executeUpdate();
            pstmt.close();
            if (res > 0) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
