package com.cafe.dal;

import com.cafe.model.Product;
import com.cafe.model.Category;

import java.util.ArrayList;
import java.util.List;

// A. Quản lý Thực đơn (Thành viên 1)
public interface ICategoryDAO extends IBaseDAO<Category, Integer> {
	public List<Category> searchByName(String name);
}