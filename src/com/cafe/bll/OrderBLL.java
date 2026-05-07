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
            Account employee = SessionManager.getInstance().getCurrentAccount();
            currentOrder = new Order(0, table, employee);
            orderDAO.insert(currentOrder);
            currentOrder = orderDAO.findUnpaidOrderByTable(tableId);
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
}
