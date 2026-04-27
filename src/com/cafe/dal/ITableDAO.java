package com.cafe.dal;

import com.cafe.model.CafeTable;
import com.cafe.model.Order;
import com.cafe.model.OrderDetail;

// B. Quản lý Bàn & Đặt món (Thành viên 2)
public interface ITableDAO extends IBaseDAO<CafeTable, Integer> {
    // Hàm đặc hiệu để đổi trạng thái bàn nhanh
    void updateStatus(int tableId, boolean isOccupied);
}