package com.cafe.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import com.cafe.bll.CategoryBLL;
import com.cafe.bll.OrderBLL;
import com.cafe.bll.ProductBLL;
import com.cafe.model.Category;
import com.cafe.model.Order;
import com.cafe.model.OrderDetail;
import com.cafe.model.Product;
import com.cafe.view.utils.ComboBoxItem;

public class OrderView extends JDialog {
    private OrderBLL orderBLL = new OrderBLL();
    private ProductBLL productBLL = new ProductBLL();
    private CategoryBLL categoryBLL = new CategoryBLL();

    private int currentTableId;
    
    // UI Components - Khu vực thêm món
    private JComboBox<ComboBoxItem> cbProduct;
    private JComboBox<ComboBoxItem> cbCategory;
    private JSpinner spinQuantity;
    private JButton btnAddProduct;

    // UI Components - Khu vực hóa đơn
    private JTable orderDetailTable;
    private DefaultTableModel orderModel;
    private JLabel lblTotal;
    private JButton btnCheckout;

    // Constructor nhận ID bàn từ TableManagerView truyền sang
    public OrderView(int tableId, String tableName) {
        this.currentTableId = tableId;
        
        setTitle("Đặt món - " + tableName);
        setSize(800, 500);
        setModal(true); // Chặn thao tác màn hình chính khi đang mở Order
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- KHU VỰC TRÁI: CHỌN MÓN ---
        // 1. KHỞI TẠO CÁC COMPONENT (Phải làm bước này ĐẦU TIÊN)
        cbCategory = new JComboBox<>();
        cbProduct = new JComboBox<>();
        spinQuantity = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
        btnAddProduct = new JButton("Thêm vào hóa đơn");
        btnAddProduct.setPreferredSize(new Dimension(0, 40));
        btnAddProduct.setFont(new Font("Arial", Font.BOLD, 13));

        // 2. THIẾT KẾ LAYOUT CHO PANEL TRÁI
        JPanel panelLeft = new JPanel(new GridBagLayout());
        panelLeft.setBorder(BorderFactory.createTitledBorder("Thêm món ăn/thức uống"));
        panelLeft.setPreferredSize(new Dimension(350, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.weightx = 1.0;

        // Lúc này cbCategory đã được "new", add thoải mái không sợ lỗi
        gbc.gridy = 0;
        panelLeft.add(new JLabel("Chọn danh mục:"), gbc);
        gbc.gridy = 1;
        panelLeft.add(cbCategory, gbc);

        gbc.gridy = 2;
        panelLeft.add(new JLabel("Chọn món:"), gbc);
        gbc.gridy = 3;
        panelLeft.add(cbProduct, gbc);

        gbc.gridy = 4;
        JPanel pnlQty = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlQty.add(new JLabel("Số lượng: "));
        pnlQty.add(spinQuantity);
        panelLeft.add(pnlQty, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(20, 10, 10, 10);
        panelLeft.add(btnAddProduct, gbc);

        // Đẩy các thành phần lên trên
        gbc.gridy = 6;
        gbc.weighty = 1.0;
        panelLeft.add(new JLabel(""), gbc);

        add(panelLeft, BorderLayout.WEST);

        // --- KHU VỰC PHẢI: CHI TIẾT HÓA ĐƠN ---
        JPanel panelRight = new JPanel(new BorderLayout());
        panelRight.setBorder(BorderFactory.createTitledBorder("Hóa đơn hiện tại"));

        String[] cols = {"Tên món", "Số lượng", "Đơn giá", "Thành tiền"};
        orderModel = new DefaultTableModel(cols, 0);
        orderDetailTable = new JTable(orderModel);
        panelRight.add(new JScrollPane(orderDetailTable), BorderLayout.CENTER);

        // Khu vực tổng tiền & Thanh toán
        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTotal = new JLabel("Tổng tiền: 0 VNĐ");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotal.setForeground(Color.RED);
        
        btnCheckout = new JButton("Thanh toán");
        btnCheckout.setBackground(new Color(46, 204, 113));
        btnCheckout.setForeground(Color.WHITE);

        panelBottom.add(lblTotal);
        panelBottom.add(btnCheckout);
        panelRight.add(panelBottom, BorderLayout.SOUTH);

        add(panelRight, BorderLayout.CENTER);

        // --- GẮN SỰ KIỆN (Listeners) ---
        setupListeners();

        initData(); // Gọi hàm này để đổ dữ liệu từ BLL
        refreshOrderDetail();
    }

    private void setupListeners() {
        // 1. Nút Thêm món
        // Khi thay đổi Category -> Cập nhật lại danh sách Product
        cbCategory.addActionListener(e -> {
            refreshProductComboBox();
        });

        // Nút thêm món (giữ nguyên logic cũ)
        btnAddProduct.addActionListener(e -> {
            ComboBoxItem selectedProd = (ComboBoxItem) cbProduct.getSelectedItem();
            if (selectedProd != null) {
                int productId = selectedProd.getValue();
                int qty = (int) spinQuantity.getValue();
                orderBLL.addProductToOrder(currentTableId, productId, qty);
                refreshOrderDetail();
            }
        });

        // 2. Nút Thanh toán
        btnCheckout.addActionListener(e -> {
            // Lấy hóa đơn hiện tại của bàn
            Order currentOrder = orderBLL.getOrCreateOrder(currentTableId);

            // Kiểm tra nếu bàn chưa có món nào
            if (currentOrder.getDetails().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bàn này chưa có món nào để thanh toán!");
                return;
            }

            double total = currentOrder.calculateTotal();

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Thanh toán cho " + currentOrder.getTable().getTableName() +
                            "\nTổng tiền: " + total + " VNĐ\nXác nhận thanh toán?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION);

            if(confirm == JOptionPane.YES_OPTION) {
                // Gọi BLL xử lý nghiệp vụ
                orderBLL.confirmPayment(currentOrder.getOrderId(), currentTableId);

                JOptionPane.showMessageDialog(this, "Thanh toán thành công!");
                this.dispose(); // Đóng cửa sổ Order
            }
        });
    }

    private void initData() {
        // 1. Load danh mục vào cbCategory
        List<Category> listCat = categoryBLL.getAllCategories();
        cbCategory.removeAllItems();
        for (Category c : listCat) {
            cbCategory.addItem(new ComboBoxItem(c.getCategoryId(), c.getCategoryName()));
        }

        // 2. Tự động kích hoạt việc load món ăn cho Category đầu tiên
        if (cbCategory.getItemCount() > 0) {
            refreshProductComboBox();
        }
    }

    private void refreshOrderDetail() {
        Order order = orderBLL.getOrCreateOrder(currentTableId);
        orderModel.setRowCount(0);
        for (OrderDetail d : order.getDetails()) {
            orderModel.addRow(new Object[]{
                d.getProduct().getProductName(),
                d.getQuantity(),
                d.getUnitPrice(),
                d.calculateSubTotal()
            });
        }
        lblTotal.setText("Tổng tiền: " + order.calculateTotal() + " VNĐ");
    }

    private void refreshProductComboBox() {
        // Lấy ID danh mục đang được chọn ở cbCategory
        ComboBoxItem selectedCat = (ComboBoxItem) cbCategory.getSelectedItem();
        if (selectedCat == null) return;

        int categoryId = selectedCat.getValue();

        // Gọi BLL lấy danh sách món thuộc categoryId này
        List<Product> listProd = categoryBLL.getProductsByCategoryId(categoryId);

        // Đổ vào cbProduct
        cbProduct.removeAllItems();
        for (Product p : listProd) {
            cbProduct.addItem(new ComboBoxItem(p.getProductId(), p.getProductName()));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OrderView(1, "Ban 1").setVisible(true));
    }
}