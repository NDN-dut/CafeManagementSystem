package com.cafe.bll;

import com.cafe.dal.ITableDAO;
import com.cafe.dal.impl.TableRAMDAO;
import com.cafe.model.CafeTable;
import java.util.List;

public class TableBLL {
    private ITableDAO tableDAO = new TableRAMDAO();

    public List<CafeTable> getAllTables() {
        return tableDAO.findAll();
    }
}