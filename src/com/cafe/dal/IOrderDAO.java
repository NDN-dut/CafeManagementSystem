package com.cafe.dal;

import com.cafe.model.CafeTable;
import com.cafe.model.Order;
import com.cafe.model.OrderDetail;

// Quản lý Bàn & Đặt món
public interface IOrderDAO extends IBaseDAO<Order, Integer> {
    // Tìm hóa đơn chưa thanh toán của một bàn
    Order findUnpaidOrderByTable(int tableId);
    
    // Chốt thanh toán hóa đơn
    void updatePaymentStatus(int orderId, boolean isPaid);
    
    // Thêm chi tiết món ăn (OrderDetail) vào hóa đơn
    void addDetail(int orderId, OrderDetail detail);

    void updateDetailQuantity(int orderId, int productId, int qty);
}
