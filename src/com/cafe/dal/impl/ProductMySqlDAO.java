package com.cafe.dal.impl;

import com.cafe.context.DbHelper;
import com.cafe.dal.IProductDAO;
import com.cafe.model.Category;
import com.cafe.model.Product;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductMySqlDAO implements IProductDAO {
    private DbHelper db = DbHelper.getInstance();
    private CategoryMySqlDAO categoryDAO = new CategoryMySqlDAO();

    @Override
    public List<Product> findByCategoryId(int categoryId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category_id = ?";
        ResultSet rs = db.executeQuery(sql, categoryId);
        try {
            while (rs != null && rs.next()) {
                products.add(mapProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> searchByName(String name) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE product_name LIKE ?";
        ResultSet rs = db.executeQuery(sql, "%" + name + "%");
        try {
            while (rs != null && rs.next()) {
                products.add(mapProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        ResultSet rs = db.executeQuery(sql);
        try {
            while (rs != null && rs.next()) {
                products.add(mapProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Product findById(Integer id) {
        String sql = "SELECT * FROM products WHERE product_id = ?";
        ResultSet rs = db.executeQuery(sql, id);
        try {
            if (rs != null && rs.next()) {
                return mapProduct(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(Product product) {
        if (product.getProductId() > 0) {
            String sql = "INSERT INTO products(product_id, product_name, price, category_id) VALUES (?, ?, ?, ?)";
            db.executeUpdate(sql, product.getProductId(), product.getProductName(), product.getPrice(), getCategoryId(product));
        } else {
            String sql = "INSERT INTO products(product_name, price, category_id) VALUES (?, ?, ?)";
            db.executeUpdate(sql, product.getProductName(), product.getPrice(), getCategoryId(product));
        }
    }

    @Override
    public void update(Product product) {
        String sql = "UPDATE products SET product_name = ?, price = ?, category_id = ? WHERE product_id = ?";
        db.executeUpdate(sql, product.getProductName(), product.getPrice(), getCategoryId(product), product.getProductId());
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM products WHERE product_id = ?";
        db.executeUpdate(sql, id);
    }

    private Product mapProduct(ResultSet rs) throws Exception {
        Category category = categoryDAO.findById(rs.getInt("category_id"));
        return new Product(
            rs.getInt("product_id"),
            rs.getString("product_name"),
            rs.getDouble("price"),
            category
        );
    }

    private int getCategoryId(Product product) {
        return product.getCategory() != null ? product.getCategory().getCategoryId() : 0;
    }
}
