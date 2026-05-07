package com.cafe.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private int orderId;
    private CafeTable table;
    private Account employee;
    private Date orderDate;
    private boolean isPaid;
    private List<OrderDetail> details;

    public Order(int orderId, CafeTable table, Account employee) {
        this.orderId = orderId;
        this.table = table;
        this.employee = employee;
        this.orderDate = new Date();
        this.isPaid = false;
        this.details = new ArrayList<>();
    }

    // GRASP: Chuyên gia tính tổng tiền của toàn bộ hóa đơn
    public double calculateTotal() {
        double total = 0;
        for (OrderDetail detail : details) {
            total += detail.calculateSubTotal();
        }
        return total;
    }

    public void addDetail(OrderDetail detail) {
        this.details.add(detail);
    }

    // Getters
    public int getOrderId() { return orderId; }
    public CafeTable getTable() { return table; }
    public Account getEmployee() { return employee; }
    public Date getOrderDate() { return orderDate; }
    public boolean isPaid() { return isPaid; }
    public List<OrderDetail> getDetails() { return details; }
    
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
    public void setPaid(boolean paid) { isPaid = paid; }

    
}
