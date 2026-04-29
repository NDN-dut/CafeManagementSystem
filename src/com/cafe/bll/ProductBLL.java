package com.cafe.bll;

import com.cafe.dal.IProductDAO;
import com.cafe.dal.impl.ProductRAMDAO;
import com.cafe.model.Product;
import java.util.List;

public class ProductBLL {
    // Khởi tạo đối tượng DAO (Dùng RAM implementation)
    private IProductDAO productDAO = new ProductRAMDAO();

    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    public List<Product> getProductsByCategory(int categoryId) {
        return productDAO.findByCategoryId(categoryId);
    }

    public Product getProductById(int id) {
        return productDAO.findById(id);
    }
<<<<<<< HEAD
    
    public boolean add(Product p) {
        try {
            productDAO.insert(p);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean update(Product p) {
        try {
            productDAO.update(p);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean delete(int id) {
        try {
            productDAO.delete(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Product> searchByName(String name) {
    	try {
    		return productDAO.searchByName(name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}  
    }
=======
>>>>>>> a515068ebb5ee8dee012983ccc2523e5a5a7e892
}