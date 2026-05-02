# Login Module - Quick Reference

## Files Created/Modified

### New Files
1. **LoginView.java** - UI component for login form
2. **AccountDAOImpl.java** - DAO for account authentication
3. **LoginBLL.java** - Business logic for login operations
4. **App.java** - Application entry point
5. **LOGIN_MODULE_README.md** - Comprehensive documentation

### Modified Files
1. **DbContext.java** - Added default accounts seeding

---

## Quick Start

### 1. Compile
```bash
cd E:\btjava\CafeManagementSystem
javac -d bin src/com/cafe/**/*.java
```

### 2. Run
```bash
java -cp bin com.cafe.App
```

### 3. Test Login
- Username: `admin` / Password: `admin123`
- Username: `staff` / Password: `staff123`

---

## Component Responsibilities

| Component | Responsibility |
|-----------|-----------------|
| **LoginView** | Display login form, collect user input |
| **LoginBLL** | Validate inputs, manage login flow, handle errors |
| **AccountDAOImpl** | Query accounts, authenticate credentials |
| **DbContext** | Provide account data, manage application state |

---

## Key Methods

### LoginView
- `getUsername()` - Get entered username
- `getPassword()` - Get entered password
- `showError(msg)` - Display error dialog
- `clearFields()` - Clear form inputs

### AccountDAOImpl
- `authenticate(username, password)` - Validate credentials
- `findByUsername(username)` - Find account by username

### LoginBLL
- `handleLogin()` - Process login attempt
- `handleCancel()` - Exit application

---

## Default Credentials
```
Admin:  admin / admin123
Staff:  staff / staff123
```

---

## Integration Steps

To integrate with your main dashboard:

1. **Uncomment in LoginBLL.handleLogin()** (after success dialog):
   ```java
   if (account.getRole() == Role.ADMIN) {
       new AdminDashboard().setVisible(true);
   } else {
       new StaffDashboard().setVisible(true);
   }
   ```

2. **Remove** `System.exit(0)` from `handleCancel()` if you want cancel button to just hide login window

3. **Store logged-in user** in session/context for later use:
   ```java
   DbContext.getInstance().currentUser = account;
   ```

---

## Common Customizations

### Change Default Theme
Edit `LoginView` constructor to customize colors, fonts, button styling

### Add Remember Me Checkbox
```java
JCheckBox chkRememberMe = new JCheckBox("Remember me");
chkRememberMe.setBounds(150, 140, 200, 20);
add(chkRememberMe);
```

### Password Requirements
Add in `LoginBLL.handleLogin()`:
```java
if (password.length() < 6) {
    view.showError("Password must be at least 6 characters");
    return;
}
```

### Add Signup Option
```java
JButton btnSignup = new JButton("Sign Up");
btnSignup.addActionListener(e -> new SignupView().setVisible(true));
```

---

## Troubleshooting

### "Cannot find symbol" during compilation
- Ensure all source files are in correct packages
- Check imports in LoginBLL, LoginView

### Login always fails
- Verify DbContext.seedData() is being called
- Check console output for account data
- Verify username/password exactly match test credentials

### Window doesn't appear
- Run with console redirection: `java -cp bin com.cafe.App 2>&1`
- Check for exceptions in output

---

## Next Steps

1. ✅ Login module complete
2. ⏳ Create MainDashboard/AdminDashboard
3. ⏳ Implement session management
4. ⏳ Add password hashing (production)
5. ⏳ Connect to MySQL database
6. ⏳ Add user management UI

---

**Last Updated**: 2026-05-02
**Status**: Ready for Integration
