package com.cafe.bll;

<<<<<<< HEAD
import com.cafe.context.DbContext;
=======
>>>>>>> a515068ebb5ee8dee012983ccc2523e5a5a7e892
import com.cafe.dal.ICategoryDAO;
import com.cafe.dal.impl.CategoryRAMDAO;
import com.cafe.model.Category;
import com.cafe.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CategoryBLL {
    private ICategoryDAO categoryDAO = new CategoryRAMDAO();

    public List<Category> getAllCategories() {
        return categoryDAO.findAll();
    }

    public List<Product> getProductsByCategoryId(int categoryId) {
        // Tìm Category trong Database (RAM)
        Category cat = categoryDAO.findById(categoryId);
        if (cat != null) {
            return cat.getProducts(); // Trả về danh sách món của riêng category đó
        }
        return new ArrayList<>();
    }
<<<<<<< HEAD
    
	public boolean add(String txt) {
		try {
			categoryDAO.insert(new Category(-1, txt));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean update(int id, String newName) {
	    try {
	        categoryDAO.update(new Category(id, newName));
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean delete(int id) {
		try {
			categoryDAO.delete(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Category> searchByName(String name) {
		try {
			return categoryDAO.searchByName(name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
=======
}
>>>>>>> a515068ebb5ee8dee012983ccc2523e5a5a7e892
