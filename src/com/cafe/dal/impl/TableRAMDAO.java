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
        for (var table : context.tables) {
            if (table.getTableId() == id.intValue())
                return table;
        }
        return null;
    }

    @Override
    public void updateStatus(int tableId, boolean isOccupied) {
        CafeTable table = findById(tableId);
        if (table != null) {
            table.changeStatus(isOccupied); // Gọi logic GRASP trong Model
        }
    }

    @Override
    public void insert(CafeTable entity) {
        context.tables.add(entity);
    }

    @Override
    public void update(CafeTable entity) {
        for (int i = 0; i < context.tables.size(); ++i) {
            if (context.tables.get(i).getTableId() == entity.getTableId())
                context.tables.set(i, entity);
        }
    }

    @Override
    public void delete(Integer id) {
        for (int i = 0; i < context.tables.size(); ++i) {
            if (context.tables.get(i).getTableId() == id.intValue()) {
                context.tables.remove(i);
            }
        }
    }
}