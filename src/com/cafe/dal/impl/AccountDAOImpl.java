package com.cafe.dal.impl;

import com.cafe.dal.IAccountDAO;
import com.cafe.model.Account;
import com.cafe.model.Role;
import com.cafe.context.DbContext;

import java.util.List;

public class AccountDAOImpl implements IAccountDAO {
    private DbContext dbContext;

    public AccountDAOImpl() {
        this.dbContext = DbContext.getInstance();
    }

    @Override
    public Account findByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }

        List<Account> accounts = dbContext.accounts;
        for (Account account : accounts) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }
        return null;
    }

    @Override
    public List<Account> findAll() {
        return dbContext.accounts;
    }

    @Override
    public Account findById(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }

        for (Account account : dbContext.accounts) {
            if (account.getAccountId() == id) {
                return account;
            }
        }
        return null;
    }

    @Override
    public void insert(Account account) {
        dbContext.accounts.add(account);
    }

    @Override
    public void update(Account account) {
      
        for (int i = 0; i < dbContext.accounts.size(); i++) {
            if (dbContext.accounts.get(i).getAccountId() == account.getAccountId()) {
                dbContext.accounts.set(i, account);
            }
        }
    }

    @Override
    public void delete(Integer id) {
        if (id == null || id <= 0) {
        }

        for (int i = 0; i < dbContext.accounts.size(); i++) {
            if (dbContext.accounts.get(i).getAccountId() == id) {
                dbContext.accounts.remove(i);
            }
        }
    }

    /**
     * Authenticate user by username and password.
     * @param username The username to authenticate
     * @param password The password to verify
     * @return The Account if authentication is successful, null otherwise
     */
    public Account authenticate(String username, String password) {
        Account account = findByUsername(username);
        if (account != null && account.verifyPassword(password)) {
            return account;
        }
        return null;
    }
}
