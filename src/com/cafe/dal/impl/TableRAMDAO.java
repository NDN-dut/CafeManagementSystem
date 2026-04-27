package com.cafe.dal.impl;

import com.cafe.dal.ITableDAO;
import com.cafe.model.CafeTable;
import com.cafe.context.DbContext;
import java.util.List;

public class TableRAMDAO implements ITableDAO {
    private DbContext context = DbContext.getInstance();

    @Override
    public List<CafeTable> findAll() {
        return context.tables;
    }

    @Override
    public CafeTable findById(Integer id) {
        return context.tables.stream()
                .filter(t -> t.getTableId() == id)
                .findFirst().orElse(null);
    }

    @Override
    public void updateStatus(int tableId, boolean isOccupied) {
        CafeTable table = findById(tableId);
        if (table != null) {
            table.changeStatus(isOccupied); // Gọi logic GRASP trong Model
        }
    }

    // Các hàm insert, update, delete làm tương tự với ArrayList...
}