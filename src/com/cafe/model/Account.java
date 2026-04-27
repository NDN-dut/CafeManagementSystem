package com.cafe.model;

public class Account {
    private int accountId;
    private String username;
    private String password;
    private Role role;

    public Account(int accountId, String username, String password, Role role) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // GRASP: Tự xác thực mật khẩu
    public boolean verifyPassword(String input) {
        return this.password.equals(input);
    }

    // Getters and Setters
    public int getAccountId() { return accountId; }
    public String getUsername() { return username; }
    public Role getRole() { return role; }
    public void setPassword(String password) { this.password = password; }
}