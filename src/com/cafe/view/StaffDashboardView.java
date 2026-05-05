package com.cafe.view;

import com.cafe.bll.LoginBLL;
import com.cafe.model.Account;

import javax.swing.*;
import java.awt.*;

public class StaffDashboardView extends JFrame {
    private static final long serialVersionUID = 1L;
    private final Account account;

    public StaffDashboardView(Account account) {
        this.account = account;
        initialize();
    }

    private void initialize() {
        setTitle("Staff Dashboard - Cafe Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 280);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitle = new JLabel("Staff Dashboard", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(25, 10, 10, 10));
        add(lblTitle, BorderLayout.NORTH);

        JPanel panelCenter = new JPanel(new GridLayout(1, 1, 12, 12));
        panelCenter.setBorder(BorderFactory.createEmptyBorder(25, 80, 25, 80));

        JButton btnTableOrder = new JButton("Quan ly ban va dat mon");
        btnTableOrder.setFont(new Font("Arial", Font.BOLD, 15));
        btnTableOrder.setFocusPainted(false);
        btnTableOrder.addActionListener(e -> openTableManagerView());
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

    private void openTableManagerView() {
        TableManagerView tableManagerView = new TableManagerView();
        tableManagerView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tableManagerView.setVisible(true);
    }

    private void logout() {
        dispose();
        LoginView loginView = new LoginView();
        new LoginBLL(loginView);
        loginView.setVisible(true);
    }
}
