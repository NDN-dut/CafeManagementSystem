package com.cafe.bll;

import com.cafe.dal.IOrderDAO;
import com.cafe.dal.IProductDAO;
import com.cafe.dal.ITableDAO;
import com.cafe.dal.impl.OrderRAMDAO;
import com.cafe.dal.impl.ProductRAMDAO;
import com.cafe.dal.impl.TableRAMDAO;
import com.cafe.model.*;
import java.util.List;

public class OrderBLL {
    private IOrderDAO orderDAO = new OrderRAMDAO();
    private ITableDAO tableDAO = new TableRAMDAO();
    private IProductDAO productDAO = new ProductRAMDAO();

    public Order getOrCreateOrder(int tableId) {
        Order currentOrder = orderDAO.findUnpaidOrderByTable(tableId);
        if (currentOrder == null) {
            // Logic tạo Order mới nếu chưa có
            // Lưu ý: Ở dự án thật cần lấy Account nhân viên đang đăng nhập
            CafeTable table = tableDAO.findById(tableId);
            currentOrder = new Order(System.currentTimeMillis() != 0 ? (int)(System.currentTimeMillis()%10000) : 1, table, null);
            orderDAO.insert(currentOrder);
            tableDAO.updateStatus(tableId, true); // Đổi trạng thái bàn sang "Có khách"
        }
        return currentOrder;
    }

    public void addProductToOrder(int tableId, int productId, int quantity) {
        Order order = getOrCreateOrder(tableId);

        // Kiểm tra xem món này đã có trong hóa đơn chưa
        OrderDetail existingDetail = null;
        for (OrderDetail d : order.getDetails()) {
            if (d.getProduct().getProductId() == productId) {
                existingDetail = d;
                break;
            }
        }

        if (existingDetail != null) {
            // Nếu đã có: Chỉ cập nhật số lượng
            int newQty = existingDetail.getQuantity() + quantity;
            existingDetail.setQuantity(newQty);
            // Lưu ý: Vì là RAM nên đối tượng trong List tự cập nhật,
            // nhưng nếu là SQL bạn sẽ cần gọi orderDAO.updateDetailQuantity(...)
            orderDAO.updateDetailQuantity(order.getOrderId(), productId, newQty);
        } else {
            // Nếu chưa có: Tạo mới và thêm vào
            Product product = productDAO.findById(productId);
            OrderDetail detail = new OrderDetail(product, quantity);
            orderDAO.addDetail(order.getOrderId(), detail);
        }
    }
    public void confirmPayment(int orderId, int tableId) {
        // 1. Đánh dấu hóa đơn đã thanh toán
        orderDAO.updatePaymentStatus(orderId, true);

        // 2. Đổi trạng thái bàn về "Trống" (false)
        tableDAO.updateStatus(tableId, false);

        // (Tùy chọn) 3. Bạn có thể thêm logic lưu log doanh thu ở đây
    }
}