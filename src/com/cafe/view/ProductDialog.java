package com.cafe.view;

import javax.swing.*;

import com.cafe.bll.CategoryBLL;
import com.cafe.model.Category;
import com.cafe.model.Product;

import java.util.List;

public class ProductDialog extends JDialog {
    private JTextField txtProductName;
    private JTextField txtPrice;
    private JComboBox<Category> cbCategory;
    private boolean isConfirmed = false;
    private Product resultProduct; // Sản phẩm trả về sau khi điền form

    public ProductDialog(JFrame parent, String title, Product currentProduct) {
        super(parent, title, true); // Modal Dialog
        setSize(350, 250);
        setLocationRelativeTo(parent);
        setLayout(null);

        JLabel lblName = new JLabel("Tên món:");
        lblName.setBounds(20, 20, 80, 25);
        add(lblName);

        // Nếu là update thì điền sẵn tên, nếu là add thì để trống
        txtProductName = new JTextField(currentProduct != null ? currentProduct.getProductName() : "");
        txtProductName.setBounds(110, 20, 180, 25);
        add(txtProductName);

        JLabel lblPrice = new JLabel("Giá tiền:");
        lblPrice.setBounds(20, 60, 80, 25);
        add(lblPrice);

        txtPrice = new JTextField(currentProduct != null ? String.valueOf(currentProduct.getPrice()) : "");
        txtPrice.setBounds(110, 60, 180, 25);
        add(txtPrice);

        JLabel lblCat = new JLabel("Danh mục:");
        lblCat.setBounds(20, 100, 80, 25);
        add(lblCat);

        cbCategory = new JComboBox<>();
        cbCategory.setBounds(110, 100, 180, 25);
        add(cbCategory);

        // --- Đổ dữ liệu từ CategoryBLL vào JComboBox ---
        CategoryBLL catBLL = new CategoryBLL();
        List<Category> categories = catBLL.getAllCategories();
        for (Category c : categories) {
            cbCategory.addItem(c);
        }

        // Nếu là update, set giá trị mặc định cho JComboBox đúng với category của sản phẩm
        if (currentProduct != null && currentProduct.getCategory() != null) {
            for (int i = 0; i < cbCategory.getItemCount(); i++) {
                if (cbCategory.getItemAt(i).getCategoryId() == currentProduct.getCategory().getCategoryId()) {
                    cbCategory.setSelectedIndex(i);
                    break;
                }
            }
        }

        JButton btnOk = new JButton("OK");
        btnOk.setBounds(60, 160, 80, 25);
        btnOk.addActionListener(e -> {
            try {
                String name = txtProductName.getText().trim();
                double price = Double.parseDouble(txtPrice.getText().trim());
                Category selectedCat = (Category) cbCategory.getSelectedItem();

                if (name.isEmpty() || selectedCat == null) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin!");
                    return;
                }

                // ID = 0 nếu thêm mới, DAO sẽ tự động tính lại ID
                int id = currentProduct != null ? currentProduct.getProductId() : 0;
                
                // Đóng gói thành object để trả về cho View chính
                resultProduct = new Product(id, name, price, selectedCat);
                isConfirmed = true;
                dispose(); // Đóng form
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Giá tiền phải là số hợp lệ!");
            }
        });
        add(btnOk);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(180, 160, 80, 25);
        btnCancel.addActionListener(e -> dispose());
        add(btnCancel);
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public Product getResultProduct() {
        return resultProduct;
    }
}