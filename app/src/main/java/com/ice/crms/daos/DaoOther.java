package com.ice.crms.daos;

import com.ice.crms.db.DataBase;

import java.sql.Connection;
import java.sql.SQLException;

public class DaoOther {
    private static final String StatusTB = "ClientStatus";
    private static final String TypeTB = "ClientType";

    public static void getAllClientTypes() {
        Connection conn = DataBase.getConnection();
        try {
            conn.prepareStatement(" SELECT * FROM " + TypeTB);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
