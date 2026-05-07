package com.cafe.dal.impl;

import com.cafe.context.DbHelper;
import com.cafe.dal.IAccountDAO;
import com.cafe.model.Account;
import com.cafe.model.Role;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AccountMySqlDAO implements IAccountDAO {
    private DbHelper db = DbHelper.getInstance();

    @Override
    public Account findByUsername(String username) {
        String sql = "SELECT * FROM accounts WHERE username = ?";
        ResultSet rs = db.executeQuery(sql, username);
        try {
            if (rs != null && rs.next()) {
                return mapAccount(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts";
        ResultSet rs = db.executeQuery(sql);
        try {
            while (rs != null && rs.next()) {
                accounts.add(mapAccount(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Account findById(Integer id) {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";
        ResultSet rs = db.executeQuery(sql, id);
        try {
            if (rs != null && rs.next()) {
                return mapAccount(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(Account account) {
        if (account.getAccountId() > 0) {
            String sql = "INSERT INTO accounts(account_id, username, password, role) VALUES (?, ?, ?, ?)";
            db.executeUpdate(sql, account.getAccountId(), account.getUsername(), account.getPassword(), account.getRole().name());
        } else {
            String sql = "INSERT INTO accounts(username, password, role) VALUES (?, ?, ?)";
            db.executeUpdate(sql, account.getUsername(), account.getPassword(), account.getRole().name());
        }
    }

    @Override
    public void update(Account account) {
        String sql = "UPDATE accounts SET username = ?, password = ?, role = ? WHERE account_id = ?";
        db.executeUpdate(sql, account.getUsername(), account.getPassword(), account.getRole().name(), account.getAccountId());
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM accounts WHERE account_id = ?";
        db.executeUpdate(sql, id);
    }

    public Account authenticate(String username, String password) {
        Account account = findByUsername(username);
        if (account != null && account.verifyPassword(password)) {
            return account;
        }
        return null;
    }

    private Account mapAccount(ResultSet rs) throws Exception {
        return new Account(
            rs.getInt("account_id"),
            rs.getString("username"),
            rs.getString("password"),
            Role.valueOf(rs.getString("role"))
        );
    }
}
