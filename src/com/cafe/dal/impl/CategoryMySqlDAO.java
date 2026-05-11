package com.cafe.dal.impl;

import com.cafe.context.DbHelper;
import com.cafe.dal.ICategoryDAO;
import com.cafe.model.Category;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryMySqlDAO implements ICategoryDAO {
    private DbHelper db = DbHelper.getInstance();

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories";
        ResultSet rs = db.executeQuery(sql);
        try {
            while (rs != null && rs.next()) {
                categories.add(mapCategory(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Category findById(Integer id) {
        String sql = "SELECT * FROM categories WHERE category_id = ?";
        ResultSet rs = db.executeQuery(sql, id);
        try {
            if (rs != null && rs.next()) {
                return mapCategory(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(Category category) {
        if (category.getCategoryId() > 0) {
            String sql = "INSERT INTO categories(category_id, category_name) VALUES (?, ?)";
            db.executeUpdate(sql, category.getCategoryId(), category.getCategoryName());
        } else {
            String sql = "INSERT INTO categories(category_name) VALUES (?)";
            db.executeUpdate(sql, category.getCategoryName());
        }
    }

    @Override
    public void update(Category category) {
        String sql = "UPDATE categories SET category_name = ? WHERE category_id = ?";
        db.executeUpdate(sql, category.getCategoryName(), category.getCategoryId());
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM categories WHERE category_id = ?";
        db.executeUpdate(sql, id);
    }

    @Override
    public List<Category> searchByName(String name) {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories WHERE category_name LIKE ?";
        ResultSet rs = db.executeQuery(sql, "%" + name + "%");
        try {
            while (rs != null && rs.next()) {
                categories.add(mapCategory(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

    private Category mapCategory(ResultSet rs) throws Exception {
        return new Category(
            rs.getInt("category_id"),
            rs.getString("category_name")
        );
    }
}
