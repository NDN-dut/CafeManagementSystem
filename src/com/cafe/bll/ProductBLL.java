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
}