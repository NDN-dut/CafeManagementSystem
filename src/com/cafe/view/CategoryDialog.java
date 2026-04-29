package com.cafe.view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CategoryDialog extends JDialog {
    private JTextField txtCategoryName;
    private boolean isConfirmed = false;
    private String categoryName = "";

    public CategoryDialog(JFrame parent, String title, String currentName) {
        super(parent, title, true); // true: Bật chế độ Modal
        setSize(300, 150);
        setLocationRelativeTo(parent); // Hiển thị ở giữa form chính
        setLayout(null);

        JLabel lblName = new JLabel("Category Name:");
        lblName.setBounds(20, 20, 100, 25);
        add(lblName);

        txtCategoryName = new JTextField(currentName);
        txtCategoryName.setBounds(120, 20, 150, 25);
        add(txtCategoryName);

        JButton btnOk = new JButton("OK");
        btnOk.setBounds(50, 70, 80, 25);
        btnOk.addActionListener(e -> {
            categoryName = txtCategoryName.getText().trim();
            if (!categoryName.isEmpty()) {
                isConfirmed = true;
                dispose(); // Đóng form
            }
        });
        add(btnOk);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(150, 70, 80, 25);
        btnCancel.addActionListener(e -> dispose());
        add(btnCancel);
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
