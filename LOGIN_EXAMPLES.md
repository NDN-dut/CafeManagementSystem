# Login Module Examples

This file contains code examples showing how to use the Login module components.

## Example 1: Manual Authentication Without UI

```java
AccountDAOImpl accountDAO = new AccountDAOImpl();

Account user = accountDAO.authenticate("admin", "admin123");

if (user != null) {
    System.out.println("✓ Login successful for: " + user.getUsername());
    System.out.println("  Role: " + user.getRole());
} else {
    System.out.println("✗ Authentication failed");
}
```

## Example 2: Find User by Username

```java
AccountDAOImpl accountDAO = new AccountDAOImpl();
Account user = accountDAO.findByUsername("staff");

if (user != null) {
    System.out.println("Found user: " + user.getUsername());
    System.out.println("ID: " + user.getAccountId());
    System.out.println("Role: " + user.getRole());
}
```

## Example 3: Get All Users

```java
AccountDAOImpl accountDAO = new AccountDAOImpl();

accountDAO.getAll().forEach(account -> {
    System.out.println("ID: " + account.getAccountId() + 
                     " | Username: " + account.getUsername() + 
                     " | Role: " + account.getRole());
});
```

## Example 4: Add New User

```java
AccountDAOImpl accountDAO = new AccountDAOImpl();
DbContext dbContext = DbContext.getInstance();

int nextId = dbContext.accounts.size() + 1;
Account newUser = new Account(nextId, "manager", "manager123", Role.ADMIN);

if (accountDAO.add(newUser)) {
    System.out.println("✓ User added successfully");
}
```

## Example 5: Update User Password

```java
AccountDAOImpl accountDAO = new AccountDAOImpl();

Account user = accountDAO.findByUsername("staff");
if (user != null) {
    user.setPassword("newpassword123");
    accountDAO.update(user);
    
    // Verify new password
    Account verifyUser = accountDAO.authenticate("staff", "newpassword123");
    if (verifyUser != null) {
        System.out.println("✓ New password verified");
    }
}
```

## Example 6: Delete User

```java
AccountDAOImpl accountDAO = new AccountDAOImpl();

if (accountDAO.delete(99)) {
    System.out.println("✓ User deleted");
}
```

## Example 7: Validate Credentials Before Login

```java
String username = "admin";
String password = "admin123";

if (username.trim().isEmpty()) {
    System.out.println("✗ Username cannot be empty");
    return;
}

if (password.isEmpty()) {
    System.out.println("✗ Password cannot be empty");
    return;
}

AccountDAOImpl accountDAO = new AccountDAOImpl();
Account user = accountDAO.authenticate(username, password);

if (user != null) {
    System.out.println("✓ Login successful!");
    openDashboard(user);
} else {
    System.out.println("✗ Invalid username or password");
}
```

## Example 8: Open Dashboard Based on Role

```java
private static void openDashboard(Account user) {
    if (user.getRole() == Role.ADMIN) {
        System.out.println("Opening ADMIN Dashboard...");
        new AdminDashboard().setVisible(true);
    } else if (user.getRole() == Role.STAFF) {
        System.out.println("Opening STAFF Dashboard...");
        new StaffDashboard().setVisible(true);
    }
}
```

## Example 9: Check if User is Admin

```java
AccountDAOImpl accountDAO = new AccountDAOImpl();
Account user = accountDAO.findByUsername("admin");

if (user != null) {
    boolean isAdmin = user.getRole() == Role.ADMIN;
    System.out.println("User is admin: " + isAdmin);
}
```

## Example 10: List Users by Role

```java
AccountDAOImpl accountDAO = new AccountDAOImpl();

System.out.println("--- ADMIN Users ---");
accountDAO.getAll().stream()
    .filter(account -> account.getRole() == Role.ADMIN)
    .forEach(account -> System.out.println("  • " + account.getUsername()));

System.out.println("--- STAFF Users ---");
accountDAO.getAll().stream()
    .filter(account -> account.getRole() == Role.STAFF)
    .forEach(account -> System.out.println("  • " + account.getUsername()));
```

---

## Integration Example: Complete Login Flow

Here's a complete example showing how to integrate the login module:

```java
public class LoginIntegrationExample {
    public static void main(String[] args) {
        // 1. Create login view
        LoginView loginView = new LoginView();
        
        // 2. Bind controller
        LoginBLL loginController = new LoginBLL(loginView);
        
        // 3. Display login window
        loginView.setVisible(true);
        
        // 4. User logs in...
        // 5. Controller automatically opens dashboard
        // 6. Application continues with logged-in user
    }
}
```

---

## Common Use Cases

### Use Case 1: Get Currently Logged-In User

```java
// After successful login, store in context
Account currentUser = account; // from LoginBLL
DbContext.getInstance().currentUser = currentUser;

// Later, retrieve it:
Account user = DbContext.getInstance().currentUser;
System.out.println("Currently logged in as: " + user.getUsername());
```

### Use Case 2: Verify User Has Permission

```java
public boolean hasAdminPrivileges(Account user) {
    return user.getRole() == Role.ADMIN;
}

if (hasAdminPrivileges(currentUser)) {
    // Show admin-only features
}
```

### Use Case 3: Change Password

```java
public void changePassword(Account user, String oldPassword, String newPassword) {
    AccountDAOImpl accountDAO = new AccountDAOImpl();
    
    // Verify old password
    if (!user.verifyPassword(oldPassword)) {
        throw new SecurityException("Old password is incorrect");
    }
    
    // Set new password and update
    user.setPassword(newPassword);
    accountDAO.update(user);
}
```

### Use Case 4: Account Lockout After Failed Attempts

```java
public class LoginAttemptTracker {
    private Map<String, Integer> failedAttempts = new HashMap<>();
    private static final int MAX_ATTEMPTS = 3;
    
    public boolean isAccountLocked(String username) {
        return failedAttempts.getOrDefault(username, 0) >= MAX_ATTEMPTS;
    }
    
    public void recordFailedAttempt(String username) {
        failedAttempts.put(username, failedAttempts.getOrDefault(username, 0) + 1);
    }
    
    public void resetFailedAttempts(String username) {
        failedAttempts.put(username, 0);
    }
}
```

---

**Last Updated**: 2026-05-02
