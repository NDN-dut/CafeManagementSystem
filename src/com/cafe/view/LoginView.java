package com.cafe.view;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private boolean isLoginSuccessful = false;

    public LoginView() {
        setTitle("Cafe Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        JLabel lblTitle = new JLabel("Login");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBounds(150, 20, 100, 40);
        add(lblTitle);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 12));
        lblUsername.setBounds(50, 80, 80, 25);
        add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(150, 80, 200, 25);
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 12));
        add(txtUsername);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 12));
        lblPassword.setBounds(50, 120, 80, 25);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 120, 200, 25);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 12));
        add(txtPassword);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(100, 170, 100, 35);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 12));
        btnLogin.setBackground(new Color(76, 175, 80));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        add(btnLogin);

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(220, 170, 100, 35);
        btnCancel.setFont(new Font("Arial", Font.BOLD, 12));
        btnCancel.setBackground(new Color(244, 67, 54));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFocusPainted(false);
        add(btnCancel);
    }

    public String getUsername() {
        return txtUsername.getText().trim();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword());
    }

    public JButton getLoginButton() {
        return btnLogin;
    }

    public JButton getCancelButton() {
        return btnCancel;
    }

    public boolean isLoginSuccessful() {
        return isLoginSuccessful;
    }

    public void setLoginSuccessful(boolean successful) {
        this.isLoginSuccessful = successful;
    }

    public void clearFields() {
        txtUsername.setText("");
        txtPassword.setText("");
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Login Failed",
            JOptionPane.ERROR_MESSAGE
        );
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}
