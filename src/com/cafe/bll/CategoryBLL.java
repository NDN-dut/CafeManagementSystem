package com.cafe.bll;

import com.cafe.dal.ICategoryDAO;
import com.cafe.dal.IProductDAO;
import com.cafe.dal.impl.CategoryMySqlDAO;
import com.cafe.dal.impl.ProductMySqlDAO;
import com.cafe.model.Category;
import com.cafe.model.Product;

import java.util.List;

public class CategoryBLL {
    private ICategoryDAO categoryDAO;
    private IProductDAO productDAO;

    public CategoryBLL() {
        this(new CategoryMySqlDAO(), new ProductMySqlDAO());
    }

    public CategoryBLL(ICategoryDAO categoryDAO, IProductDAO productDAO) {
        this.categoryDAO = categoryDAO;
        this.productDAO = productDAO;
    }

    public List<Category> getAllCategories() {
        return categoryDAO.findAll();
    }

    public List<Product> getProductsByCategoryId(int categoryId) {
        return productDAO.findByCategoryId(categoryId);
    }

    public boolean add(String txt) {
        try {
            categoryDAO.insert(new Category(0, txt));
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
