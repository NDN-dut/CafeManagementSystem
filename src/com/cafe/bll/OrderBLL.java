package com.cafe.bll;

import com.cafe.context.SessionManager;
import com.cafe.dal.IOrderDAO;
import com.cafe.dal.IProductDAO;
import com.cafe.dal.ITableDAO;
import com.cafe.dal.impl.OrderMySqlDAO;
import com.cafe.dal.impl.ProductMySqlDAO;
import com.cafe.dal.impl.TableMySqlDAO;
import com.cafe.model.Account;
import com.cafe.model.CafeTable;
import com.cafe.model.Order;
import com.cafe.model.OrderDetail;
import com.cafe.model.Product;

import java.nio.file.Files;
import java.nio.file.Paths;

public class OrderBLL {
    private IOrderDAO orderDAO;
    private ITableDAO tableDAO;
    private IProductDAO productDAO;

    public OrderBLL() {
        this(new OrderMySqlDAO(), new TableMySqlDAO(), new ProductMySqlDAO());
    }

    public OrderBLL(IOrderDAO orderDAO, ITableDAO tableDAO, IProductDAO productDAO) {
        this.orderDAO = orderDAO;
        this.tableDAO = tableDAO;
        this.productDAO = productDAO;
    }

    public Order getOrCreateOrder(int tableId) {
        Order currentOrder = orderDAO.findUnpaidOrderByTable(tableId);
        if (currentOrder == null) {
            CafeTable table = tableDAO.findById(tableId);
            Account currentStaff = com.cafe.context.SessionManager.getInstance().getCurrentAccount();

            // Đặt ID là 0 để MySQL AUTO_INCREMENT tự làm việc
            currentOrder = new Order(0, table, currentStaff);

            orderDAO.insert(currentOrder);
            tableDAO.updateStatus(tableId, true);
        }
        return currentOrder;
    }

    public void addProductToOrder(int tableId, int productId, int quantity) {
        Order order = getOrCreateOrder(tableId);

        OrderDetail existingDetail = null;
        for (OrderDetail d : order.getDetails()) {
            if (d.getProduct().getProductId() == productId) {
                existingDetail = d;
                break;
            }
        }

        if (existingDetail != null) {
            int newQty = existingDetail.getQuantity() + quantity;
            existingDetail.setQuantity(newQty);
            orderDAO.updateDetailQuantity(order.getOrderId(), productId, newQty);
        } else {
            Product product = productDAO.findById(productId);
            OrderDetail detail = new OrderDetail(product, quantity);
            orderDAO.addDetail(order.getOrderId(), detail);
        }
    }

    public void confirmPayment(int orderId, int tableId) {
        orderDAO.updatePaymentStatus(orderId, true);
        tableDAO.updateStatus(tableId, false);
    }

    public void printInvoice(Order order) {
        try {
            String content = "--- HOA DON CAFE ---\n";
            content += "Ma HD: " + order.getOrderId() + "\n";
            content += "Ban: " + order.getTable().getTableName() + "\n";
            content += "Nhan vien: " + order.getEmployee().getUsername() + "\n";
            content += "--------------------\n";

            for (OrderDetail detail : order.getDetails()) {
                content += detail.getProduct().getProductName() + " x" + detail.getQuantity()
                        + " : " + detail.calculateSubTotal() + " VND\n";
            }

            content += "--------------------\n";
            content += "TONG CONG: " + order.calculateTotal() + " VND\n";

            // Ghi trực tiếp chuỗi vào file (Tên file theo mã hóa đơn)
            Files.write(Paths.get("HoaDon_" + order.getOrderId() + ".txt"), content.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
