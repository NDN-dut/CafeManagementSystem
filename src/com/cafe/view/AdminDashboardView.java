package com.cafe.view;

import com.cafe.bll.LoginBLL;
import com.cafe.context.SessionManager;
import com.cafe.model.Account;

import javax.swing.*;
import java.awt.*;

public class AdminDashboardView extends JFrame {
    private static final long serialVersionUID = 1L;
    private final Account account;

    public AdminDashboardView(Account account) {
        this.account = account;
        initialize();
    }

    private void initialize() {
        setTitle("Admin Dashboard - Cafe Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 360);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitle = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(lblTitle, BorderLayout.NORTH);

        JPanel panelCenter = new JPanel(new GridLayout(3, 1, 12, 12));
        panelCenter.setBorder(BorderFactory.createEmptyBorder(15, 70, 15, 70));

        JButton btnCategory = createMenuButton("Quan ly danh muc");
        JButton btnProduct = createMenuButton("Quan ly san pham");
        JButton btnTableOrder = createMenuButton("Quan ly ban va dat mon");

        btnCategory.addActionListener(e -> openCategoryView());
        btnProduct.addActionListener(e -> openProductView());
        btnTableOrder.addActionListener(e -> openTableManagerView());

        panelCenter.add(btnCategory);
        panelCenter.add(btnProduct);
        panelCenter.add(btnTableOrder);
        add(panelCenter, BorderLayout.CENTER);

        JPanel panelBottom = new JPanel(new BorderLayout());
        panelBottom.setBorder(BorderFactory.createEmptyBorder(5, 20, 20, 20));

        JLabel lblUser = new JLabel("Logged in: " + account.getUsername() + " (" + account.getRole() + ")");
        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> logout());

        panelBottom.add(lblUser, BorderLayout.WEST);
        panelBottom.add(btnLogout, BorderLayout.EAST);
        add(panelBottom, BorderLayout.SOUTH);
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setFocusPainted(false);
        return button;
    }

    private void openCategoryView() {
        CategoryView categoryView = new CategoryView();
        categoryView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        categoryView.setVisible(true);
    }

    private void openProductView() {
        ProductView productView = new ProductView();
        productView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        productView.refresh();
        productView.setVisible(true);
    }

    private void openTableManagerView() {
        TableManagerView tableManagerView = new TableManagerView();
        tableManagerView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tableManagerView.setVisible(true);
    }

    private void logout() {
        SessionManager.getInstance().logout();
        dispose();
        LoginView loginView = new LoginView();
        new LoginBLL(loginView);
        loginView.setVisible(true);
    }
}
