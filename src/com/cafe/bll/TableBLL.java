package com.cafe.bll;

import com.cafe.dal.ITableDAO;
import com.cafe.dal.impl.TableMySqlDAO;
import com.cafe.model.CafeTable;

import java.util.List;

public class TableBLL {
    private ITableDAO tableDAO;

    public TableBLL() {
        this(new TableMySqlDAO());
    }

    public TableBLL(ITableDAO tableDAO) {
        this.tableDAO = tableDAO;
    }

    public List<CafeTable> getAllTables() {
        return tableDAO.findAll();
    }
}
