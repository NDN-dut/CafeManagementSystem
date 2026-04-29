package com.cafe.bll;

import com.cafe.context.DbContext;
import com.cafe.model.Category;

public class CategoryBLL {
	private static CategoryBLL instance;
	public static CategoryBLL getInstance() {
		if (instance == null)
			instance = new CategoryBLL();
		return instance;
	}
	private CategoryBLL() {
	}
	
	public boolean add(String txt) {
		try {
			DbContext.getInstance().categories.add(new Category(DbContext.getInstance().categories.size() + 1, txt));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
