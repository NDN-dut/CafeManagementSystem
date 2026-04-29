package com.cafe.bll;

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
}