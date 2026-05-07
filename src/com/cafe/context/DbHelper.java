package com.cafe.context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

public class DbHelper {
    private static DbHelper instance;

    public static DbHelper getInstance() {
        if (instance == null)
            instance = new DbHelper();
        return instance;
    }

    private DbHelper() {
    }

    private final String url = "jdbc:mysql://localhost:3306/cafe_management";
    private final String username = "root";
    private final String password = "";

    public RowSet executeQuery(String query, Object... args) {
        try (
            Connection conn = DriverManager.getConnection(url, username, password);
            PreparedStatement pstmt = conn.prepareStatement(query);
        ) {
            for (int i = 0; i < args.length; i++) {
                pstmt.setObject(i + 1, args[i]);
            }
            ResultSet rs = pstmt.executeQuery();
            CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.populate(rs);
            return crs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int executeUpdate(String query, Object... args) {
        try (
            Connection conn = DriverManager.getConnection(url, username, password);
            PreparedStatement pstmt = conn.prepareStatement(query);
        ) {
            for (int i = 0; i < args.length; i++) {
                pstmt.setObject(i + 1, args[i]);
            }
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
