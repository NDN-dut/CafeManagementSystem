package com.cafe.bll;

import com.cafe.dal.impl.AccountDAOImpl;
import com.cafe.model.Account;
import com.cafe.model.Role;
import com.cafe.view.AdminDashboardView;
import com.cafe.view.LoginView;
import com.cafe.view.StaffDashboardView;

public class LoginBLL {
    private LoginView view;
    private AccountDAOImpl accountDAO;

    public LoginBLL(LoginView view) {
        this.view = view;
        this.accountDAO = new AccountDAOImpl();
        initializeEventHandlers();
    }

    private void initializeEventHandlers() {
        // Login button action
        view.getLoginButton().addActionListener(e -> handleLogin());

        // Cancel button action
        view.getCancelButton().addActionListener(e -> handleCancel());

        // Allow login on Enter key press
        view.getRootPane().setDefaultButton(view.getLoginButton());
    }

    private void handleLogin() {
        String username = view.getUsername();
        String password = view.getPassword();

        // Validate empty fields
        if (username.isEmpty()) {
            view.showError("Please enter a username");
            return;
        }

        if (password.isEmpty()) {
            view.showError("Please enter a password");
            return;
        }

        // Authenticate user
        Account account = accountDAO.authenticate(username, password);

        if (account == null) {
            view.showError("Invalid username or password");
            view.clearFields();
            return;
        }

        // Login successful
        view.setLoginSuccessful(true);
        System.out.println("Login successful!");
        System.out.println("User: " + account.getUsername());
        System.out.println("Role: " + account.getRole());

        view.showSuccess("Login successful! Welcome " + account.getUsername() + "!");

        // Close login window and open main screen
        view.dispose();
        if (account.getRole() == Role.ADMIN) {
            new AdminDashboardView(account).setVisible(true);
        } else {
            new StaffDashboardView(account).setVisible(true);
        }
    }

    private void handleCancel() {
        view.dispose();
        System.exit(0);
    }
}
