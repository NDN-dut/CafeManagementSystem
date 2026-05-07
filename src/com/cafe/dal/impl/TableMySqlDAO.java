package com.cafe.dal.impl;

import com.cafe.context.DbHelper;
import com.cafe.dal.ITableDAO;
import com.cafe.model.CafeTable;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TableMySqlDAO implements ITableDAO {
    private DbHelper db = DbHelper.getInstance();

    @Override
    public List<CafeTable> findAll() {
        List<CafeTable> tables = new ArrayList<>();
        String sql = "SELECT * FROM cafe_tables";
        ResultSet rs = db.executeQuery(sql);
        try {
            while (rs != null && rs.next()) {
                tables.add(mapTable(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tables;
    }

    @Override
    public CafeTable findById(Integer id) {
        String sql = "SELECT * FROM cafe_tables WHERE table_id = ?";
        ResultSet rs = db.executeQuery(sql, id);
        try {
            if (rs != null && rs.next()) {
                return mapTable(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateStatus(int tableId, boolean isOccupied) {
        String sql = "UPDATE cafe_tables SET is_occupied = ? WHERE table_id = ?";
        db.executeUpdate(sql, isOccupied, tableId);
    }

    @Override
    public void insert(CafeTable table) {
        if (table.getTableId() > 0) {
            String sql = "INSERT INTO cafe_tables(table_id, table_name, is_occupied) VALUES (?, ?, ?)";
            db.executeUpdate(sql, table.getTableId(), table.getTableName(), table.isOccupied());
        } else {
            String sql = "INSERT INTO cafe_tables(table_name, is_occupied) VALUES (?, ?)";
            db.executeUpdate(sql, table.getTableName(), table.isOccupied());
        }
    }

    @Override
    public void update(CafeTable table) {
        String sql = "UPDATE cafe_tables SET table_name = ?, is_occupied = ? WHERE table_id = ?";
        db.executeUpdate(sql, table.getTableName(), table.isOccupied(), table.getTableId());
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM cafe_tables WHERE table_id = ?";
        db.executeUpdate(sql, id);
    }

    private CafeTable mapTable(ResultSet rs) throws Exception {
        CafeTable table = new CafeTable(
            rs.getInt("table_id"),
            rs.getString("table_name")
        );
        table.changeStatus(rs.getBoolean("is_occupied"));
        return table;
    }
}
