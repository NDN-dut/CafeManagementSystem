package com.cafe.dal.impl;

import com.cafe.context.DbHelper;
import com.cafe.dal.IOrderDAO;
import com.cafe.model.Account;
import com.cafe.model.CafeTable;
import com.cafe.model.Order;
import com.cafe.model.OrderDetail;
import com.cafe.model.Product;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrderMySqlDAO implements IOrderDAO {
    private DbHelper db = DbHelper.getInstance();
    private TableMySqlDAO tableDAO = new TableMySqlDAO();
    private AccountMySqlDAO accountDAO = new AccountMySqlDAO();
    private ProductMySqlDAO productDAO = new ProductMySqlDAO();

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        ResultSet rs = db.executeQuery(sql);
        try {
            while (rs != null && rs.next()) {
                orders.add(mapOrder(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public Order findById(Integer id) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        ResultSet rs = db.executeQuery(sql, id);
        try {
            if (rs != null && rs.next()) {
                return mapOrder(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(Order order) {
        if (order.getOrderId() > 0) {
            String sql = "INSERT INTO orders(order_id, table_id, account_id, order_date, is_paid) VALUES (?, ?, ?, ?, ?)";
            db.executeUpdate(
                sql,
                order.getOrderId(),
                getTableId(order),
                getAccountId(order),
                new Timestamp(order.getOrderDate().getTime()),
                order.isPaid()
            );
        } else {
            String sql = "INSERT INTO orders(table_id, account_id, order_date, is_paid) VALUES (?, ?, ?, ?)";
            db.executeUpdate(
                sql,
                getTableId(order),
                getAccountId(order),
                new Timestamp(order.getOrderDate().getTime()),
                order.isPaid()
            );
        }

        for (OrderDetail detail : order.getDetails()) {
            addDetail(order.getOrderId(), detail);
        }
    }

    @Override
    public void update(Order order) {
        String sql = "UPDATE orders SET table_id = ?, account_id = ?, order_date = ?, is_paid = ? WHERE order_id = ?";
        db.executeUpdate(
            sql,
            getTableId(order),
            getAccountId(order),
            new Timestamp(order.getOrderDate().getTime()),
            order.isPaid(),
            order.getOrderId()
        );
    }

    @Override
    public void delete(Integer id) {
        db.executeUpdate("DELETE FROM order_details WHERE order_id = ?", id);
        db.executeUpdate("DELETE FROM orders WHERE order_id = ?", id);
    }

    @Override
    public Order findUnpaidOrderByTable(int tableId) {
        String sql = "SELECT * FROM orders WHERE table_id = ? AND is_paid = ?";
        ResultSet rs = db.executeQuery(sql, tableId, false);
        try {
            if (rs != null && rs.next()) {
                return mapOrder(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updatePaymentStatus(int orderId, boolean isPaid) {
        String sql = "UPDATE orders SET is_paid = ? WHERE order_id = ?";
        db.executeUpdate(sql, isPaid, orderId);
    }

    @Override
    public void addDetail(int orderId, OrderDetail detail) {
        String sql = "INSERT INTO order_details(order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
        db.executeUpdate(
            sql,
            orderId,
            detail.getProduct().getProductId(),
            detail.getQuantity(),
            detail.getUnitPrice()
        );
    }

    @Override
    public void updateDetailQuantity(int orderId, int productId, int qty) {
        String sql = "UPDATE order_details SET quantity = ? WHERE order_id = ? AND product_id = ?";
        db.executeUpdate(sql, qty, orderId, productId);
    }

    private Order mapOrder(ResultSet rs) throws Exception {
        CafeTable table = tableDAO.findById(rs.getInt("table_id"));
        Account employee = accountDAO.findById(rs.getInt("account_id"));
        Order order = new Order(rs.getInt("order_id"), table, employee);

        // --- SỬA ĐOẠN NÀY ĐỂ TRÁNH LỖI CLASSCAST ---
     // Lấy Object ra để tránh lỗi ép kiểu ngầm của CachedRowSet
        Object dateObj = rs.getObject("order_date");

        if (dateObj != null) {
            if (dateObj instanceof java.time.LocalDateTime) {
                // Nếu MySQL trả về LocalDateTime
                order.setOrderDate(java.sql.Timestamp.valueOf((java.time.LocalDateTime) dateObj));
            } else if (dateObj instanceof java.sql.Timestamp) {
                // Nếu đã là Timestamp sẵn
                order.setOrderDate((java.sql.Timestamp) dateObj);
            } else {
                // Fallback an toàn cho các kiểu dữ liệu khác (String, Date...)
                try {
                    order.setOrderDate(java.sql.Timestamp.valueOf(dateObj.toString()));
                } catch (IllegalArgumentException ex) {
                    System.err.println("Không thể parse ngày tháng: " + dateObj);
                }
            }
        }
        // ------------------------------------------

        order.setPaid(rs.getBoolean("is_paid"));

        for (OrderDetail detail : findDetailsByOrderId(order.getOrderId())) {
            order.addDetail(detail);
        }
        return order;
    }

    private List<OrderDetail> findDetailsByOrderId(int orderId) {
        List<OrderDetail> details = new ArrayList<>();
        String sql = "SELECT * FROM order_details WHERE order_id = ?";
        ResultSet rs = db.executeQuery(sql, orderId);
        try {
            while (rs != null && rs.next()) {
                Product product = productDAO.findById(rs.getInt("product_id"));
                OrderDetail detail = new OrderDetail(product, rs.getInt("quantity"));
                details.add(detail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return details;
    }

    private int getTableId(Order order) {
        return order.getTable() != null ? order.getTable().getTableId() : 0;
    }

    private int getAccountId(Order order) {
        return order.getEmployee() != null ? order.getEmployee().getAccountId() : 0;
    }
}
