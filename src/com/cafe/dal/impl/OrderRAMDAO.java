package com.cafe.dal.impl;

import com.cafe.dal.IOrderDAO;
import com.cafe.model.Order;
import com.cafe.model.OrderDetail;
import com.cafe.context.DbContext;
import java.util.List;

public class OrderRAMDAO implements IOrderDAO {
    private DbContext context = DbContext.getInstance();

    @Override
    public List<Order> findAll() {
        return context.orders;
    }

    @Override
    public Order findById(Integer id) {
        for (var order : context.orders) {
            if (order.getOrderId() == id)
                return order;
        }
        return null;
    }

    @Override
    public void insert(Order entity) {
        context.orders.add(entity);
    }

    @Override
    public void update(Order entity) {
        for (int i = 0; i < context.orders.size(); ++i) {
            if (context.orders.get(i).getOrderId() == entity.getOrderId())
                context.orders.set(i, entity);
        }
    }

    @Override
    public void delete(Integer id) {
        for (int i = 0; i < context.orders.size(); ++i) {
            if (context.orders.get(i).getOrderId() == id) {
                context.orders.remove(i);
                break;
            }
        } 
    }

    @Override
    public Order findUnpaidOrderByTable(int tableId) {
        return DbContext.getInstance().orders.stream()
                .filter(o -> o.getTable().getTableId() == tableId && !o.isPaid())
                .findFirst()
                .orElse(null);
    }

    @Override
    public void updatePaymentStatus(int orderId, boolean isPaid) {
        Order order = findById(orderId);
        if (order != null) {
            order.setPaid(isPaid);
        }
    }

    @Override
    public void addDetail(int orderId, OrderDetail detail) {
        findById(orderId).addDetail(detail);
    }

    @Override
    public void updateDetailQuantity(int orderId, int productId,int qty) {
        OrderDetail orderDetail = findById(orderId).getDetails().stream()
                .filter(od -> od.getProduct().getProductId() == productId)
                .findFirst()
                .orElse(null);
        if (orderDetail != null)
            orderDetail.setQuantity(qty);
    }
}
