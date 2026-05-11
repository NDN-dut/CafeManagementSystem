package com.cafe.bll;

import java.util.List;

import com.cafe.dal.impl.AccountMySqlDAO;
import com.cafe.model.Account;

public class AccountBLL {
	private AccountMySqlDAO accountMySqlDAO;
	
	public AccountBLL() {
		accountMySqlDAO = new AccountMySqlDAO();
	}
	
	public List<Account> getAllAccounts() {
		return accountMySqlDAO.findAll();
	}
	
	public boolean add(Account ac) {
		try {
			accountMySqlDAO.insert(ac);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean update(Account ac) {
		try {
			accountMySqlDAO.update(ac);
			return true;
		} catch (Exception e) {
			return false;
		}
	} 
	
	public boolean addUpdate(Account ac) {
		if (checkAccountById(ac.getAccountId())) {
			return add(ac);
		} else {
			return update(ac);
		}
	}
	
	public boolean checkAccountById(int id) {
		boolean check = true;	//add
		if (findById(id) != null) {
			System.out.println("Upadate k");
			check = false;
		}
		return check;
	}
	
	public boolean delete(List<Integer> li) {
		try {
			for (var i : li) {
				accountMySqlDAO.delete(i);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public Account findByUsername(String username) {
		try {
			return accountMySqlDAO.findByUsername(username);
		} catch (Exception e) {
			return null;
		}
	}
	
	public Account findById(int id) {
		try {
			return accountMySqlDAO.findById(id);
		} catch (Exception e) {
			return null;
		}
	}
	
	public Account authenticate(String username, String password) {
		try {
			return accountMySqlDAO.authenticate(username, password);
		} catch (Exception e) {
			return null;
		}
	}
}
