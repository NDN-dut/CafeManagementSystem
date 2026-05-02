# Login Module Documentation

## Overview
This document describes the Login module for the Cafe Management System, implemented following the MVC (Model-View-Controller) architecture pattern.

## Architecture

### Components

#### 1. **LoginView.java** (`src/com/cafe/view/`)
- **Purpose**: Provides the user interface for authentication
- **Type**: JFrame-based dialog
- **Features**:
  - Username text field (plain text)
  - Password field (masked/hidden)
  - Login button (green, styled)
  - Cancel button (red, styled)
  - Error and success message dialogs

**Key Methods**:
- `getUsername()`: Returns trimmed username input
- `getPassword()`: Returns password as String (converted from JPasswordField)
- `showError(String message)`: Displays error dialog
- `showSuccess(String message)`: Displays success dialog
- `clearFields()`: Clears both username and password fields
- `getLoginButton()` / `getCancelButton()`: Returns button references for event binding

#### 2. **AccountDAOImpl.java** (`src/com/cafe/dal/impl/`)
- **Purpose**: Data Access Object for Account operations
- **Implements**: `IAccountDAO` interface
- **Type**: In-memory DAO (uses DbContext)
- **Features**:
  - Finds accounts by username
  - Authenticates user credentials
  - CRUD operations for accounts

**Key Method**:
```java
public Account authenticate(String username, String password)
```
- Queries the database for account by username
- Verifies password using `account.verifyPassword()`
- Returns `Account` object if credentials match, `null` otherwise

**Implementation Details**:
- Uses `DbContext.getInstance()` for data access
- Supports all standard DAO operations (CRUD)
- Thread-safe for in-memory storage

#### 3. **LoginBLL.java** (`src/com/cafe/bll/`)
- **Purpose**: Business Logic Layer for login operations
- **Type**: Event controller and validator
- **Features**:
  - Validates empty fields
  - Handles authentication logic
  - Shows appropriate error messages
  - Displays user role in console on success
  - Closes login window after successful authentication

**Workflow**:
1. User enters username and password
2. Clicks "Login" button (or presses Enter)
3. `handleLogin()` validates inputs
4. `AccountDAOImpl.authenticate()` is called
5. On success: prints user info and closes window
6. On failure: shows error dialog and clears fields
7. "Cancel" button exits the application

#### 4. **App.java** (`src/com/cafe/`)
- **Purpose**: Application entry point
- **Functionality**:
  - Initializes DbContext (loads seed data with default accounts)
  - Creates and displays LoginView
  - Binds LoginBLL to LoginView
  - Prints default credentials to console

## Database Schema

### Accounts Table (In-Memory)
```
accounts (DbContext.accounts)
├── id: int (Primary Key)
├── username: String (unique)
├── password: String (plain text - can be hashed in production)
├── role: Role enum (ADMIN or STAFF)
```

## Default Test Credentials

The system comes with two pre-configured accounts for testing:

| Username | Password   | Role  |
|----------|-----------|-------|
| admin    | admin123  | ADMIN |
| staff    | staff123  | STAFF |

These credentials are automatically seeded in `DbContext.seedData()`.

## Usage

### Running the Application

```bash
cd E:\btjava\CafeManagementSystem
javac -d bin src/com/cafe/view/LoginView.java \
              src/com/cafe/dal/impl/AccountDAOImpl.java \
              src/com/cafe/bll/LoginBLL.java \
              src/com/cafe/App.java \
              src/com/cafe/model/*.java \
              src/com/cafe/context/*.java

java -cp bin com.cafe.App
```

### Login Flow

1. **Launch**: Run `java -cp bin com.cafe.App`
2. **Enter Credentials**: Type username and password in the login form
3. **Click Login**: 
   - If credentials are valid → Success dialog appears, then window closes
   - If credentials are invalid → Error message shown, fields are cleared
4. **Console Output**: User information is printed to console upon successful login

**Example Console Output**:
```
Cafe Management System - Login
Default credentials for testing:
  Admin: username=admin, password=admin123
  Staff: username=staff, password=staff123
Login successful!
User: admin
Role: ADMIN
```

## Validation

The module includes the following validation:

✅ **Empty Field Validation**:
- Checks if username field is empty
- Checks if password field is empty
- Shows appropriate error messages

✅ **Credential Validation**:
- Verifies username exists in database
- Verifies password matches stored password
- Shows "Invalid username or password" if either fails

✅ **UI Validation**:
- Password field is masked (JPasswordField)
- Fields are cleared on failed login
- Enter key automatically triggers login

## Security Considerations

⚠️ **Current Implementation** (for development/testing):
- Passwords are stored in plain text
- Using in-memory storage (DbContext)
- No encryption or hashing

🔒 **Production Recommendations**:
1. **Password Hashing**: Use bcrypt, Argon2, or PBKDF2
   ```java
   password = BCrypt.hashpw(password, BCrypt.gensalt());
   ```

2. **Database**: Migrate from in-memory to MySQL/PostgreSQL with JDBC/Hibernate

3. **Session Management**: Implement JWT tokens or server sessions

4. **Password Policy**: Enforce minimum requirements (length, complexity)

5. **Rate Limiting**: Prevent brute-force attacks

6. **Logging**: Audit login attempts

7. **SSL/TLS**: Use HTTPS for production

## Integration with Dashboard

After successful login, the next step is to open the main dashboard based on user role:

```java
// Uncomment in LoginBLL.handleLogin()
if (account.getRole() == Role.ADMIN) {
    new AdminDashboard().setVisible(true);
} else {
    new StaffDashboard().setVisible(true);
}
```

## File Structure

```
src/com/cafe/
├── view/
│   └── LoginView.java
├── bll/
│   └── LoginBLL.java
├── dal/
│   ├── IAccountDAO.java (existing)
│   └── impl/
│       └── AccountDAOImpl.java
├── model/
│   ├── Account.java (existing)
│   └── Role.java (existing)
├── context/
│   └── DbContext.java (updated with accounts seeding)
└── App.java
```

## Error Handling

The module gracefully handles:
- Empty username field
- Empty password field
- Invalid credentials
- Missing user account
- Application exit on cancel

All error messages are displayed in user-friendly dialogs.

## Future Enhancements

1. **Remember Me**: Add checkbox to save username
2. **Forgot Password**: Recovery mechanism
3. **Account Lockout**: Lock after N failed attempts
4. **Multi-Factor Authentication**: Add OTP/2FA
5. **Social Login**: Google, Facebook OAuth integration
6. **Password Strength Meter**: Real-time validation feedback
7. **Session Timeout**: Auto-logout after inactivity
8. **Login History**: Track login attempts and timestamps

## Testing

To test the login module:

1. **Valid Admin Login**:
   - Username: `admin`
   - Password: `admin123`
   - Expected: Success message, role printed as "ADMIN"

2. **Valid Staff Login**:
   - Username: `staff`
   - Password: `staff123`
   - Expected: Success message, role printed as "STAFF"

3. **Invalid Credentials**:
   - Username: `admin`
   - Password: `wrongpassword`
   - Expected: Error dialog "Invalid username or password"

4. **Empty Fields**:
   - Leave fields blank and click Login
   - Expected: Error dialog "Please enter a username/password"

5. **Cancel Button**:
   - Click Cancel
   - Expected: Application exits cleanly

---

**Created for**: Cafe Management System
**Architecture**: MVC
**Framework**: Java Swing
**Storage**: In-Memory (DbContext)
