package com.cafe.dal;

import com.cafe.model.CafeTable;
import com.cafe.model.Order;
import com.cafe.model.OrderDetail;

// Quản lý Bàn & Đặt món
public interface ITableDAO extends IBaseDAO<CafeTable, Integer> {
    void updateStatus(int tableId, boolean isOccupied);
}
