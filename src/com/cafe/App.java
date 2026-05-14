package com.cafe;

import com.cafe.bll.LoginBLL;
import com.cafe.context.DbContext;
import com.cafe.view.LoginView;

public class App {
    public static void main(String[] args) {
        // Create and display login view
        LoginView loginView = new LoginView();
        new LoginBLL(loginView);
        loginView.setVisible(true);

        System.out.println("Cafe Management System - Login");
        System.out.println("Default credentials for testing:");
        System.out.println("  Admin: username=admin, password=admin123");
        System.out.println("  Staff: username=staff, password=staff123");
    }
}
