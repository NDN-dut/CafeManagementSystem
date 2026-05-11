package com.cafe.model;

public class Account {
    private int accountId;
    private String username;
    private String password;
    private Role role;
    private boolean status;
    
    public Account(int accountId, String username, String password, Role role) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = true;
    }

    public Account(int accountId, String username, String password, Role role, boolean status) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    // GRASP: Tự xác thực mật khẩu
    public boolean verifyPassword(String input) {
        return this.password.equals(input);
    }

    // Getters and Setters
    public int getAccountId() { return accountId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }
    public void setPassword(String password) { this.password = password; }

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
    
}
