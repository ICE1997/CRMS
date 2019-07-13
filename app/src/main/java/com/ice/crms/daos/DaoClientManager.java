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
    private static Connection conn = DataBase.getConnection();

    public static LinkedList<ClientRelation> getAllRelations() {
        int clientNo;
        String clientName;
        int clientType;
        int clientStatus;
        long date;
        String clientAddr;
        LinkedList<ClientRelation> clientRelations = new LinkedList<>();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientRelations;
    }

    public static LinkedList<ClientRelation> getRelationsByNo(int mClientNo) {
        int clientNo;
        String clientName;
        int clientType;
        int clientStatus;
        long date;
        String clientAddr;
        LinkedList<ClientRelation> clientRelations = new LinkedList<>();
        try {
            PreparedStatement pstmt = conn.prepareStatement(" SELECT * FROM " + TB + " WHERE clientNo = ? ");
            pstmt.setInt(1, mClientNo);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientRelations;
    }

    public static LinkedList<ClientRelation> getRelationsByName(String mClientName) {
        int clientNo;
        String clientName;
        int clientType;
        int clientStatus;
        long date;
        String clientAddr;
        LinkedList<ClientRelation> clientRelations = new LinkedList<>();
        try {
            PreparedStatement pstmt = conn.prepareStatement(" SELECT * FROM " + TB + " WHERE clientName like ?");
            pstmt.setString(1, "%" + mClientName + "%");
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientRelations;
    }

    public static void addRelation(ClientRelation relation) {
        int clientNo = relation.getClientNo();
        String clientName = relation.getClientName();
        int clientType = relation.getClientType();
        int clientStatus = relation.getClientStatus();
        long date = relation.getDate();
        String clientAddr = relation.getClientAddr();

        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO " + TB + " ( clientNo, clientName, clientType, clientStatus, buildRelTime ,clientAddr ) VALUES(?,?,?,?,?,?) ");
            pstmt.setInt(1, clientNo);
            pstmt.setString(2, clientName);
            pstmt.setInt(3, clientType);
            pstmt.setInt(4, clientStatus);
            pstmt.setDate(5, new java.sql.Date(date));
            pstmt.setString(6, clientAddr);
            int res = pstmt.executeUpdate();

            if (res > 0) {
                System.out.println("新增成功!");
            } else {
                System.out.println("新增失败!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void modifyRelation(ClientRelation relation) {
        int clientNo = relation.getClientNo();
        String clientName = relation.getClientName();
        int clientType = relation.getClientType();
        int clientStatus = relation.getClientStatus();
        long date = relation.getDate();
        String clientAddr = relation.getClientAddr();

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
                System.out.println("修改成功!");
            } else {
                System.out.println("修改失败!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteRelationByNO(int clientNo) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM " + TB + " WHERE clientNo = ? ");
            pstmt.setInt(1, clientNo);
            int res = pstmt.executeUpdate();
            if (res > 0) {
                System.out.println("删除成功!");
            } else {
                System.out.println("删除失败!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
