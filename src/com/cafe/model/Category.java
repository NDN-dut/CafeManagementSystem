package com.cafe.model;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private int categoryId;
    private String categoryName;
    private List<Product> products; // Thêm List này

    public Category(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.products = new ArrayList<>();
    }

    public void addProduct(Product p) {
        this.products.add(p);
    }

    // Getters
    public int getCategoryId() { return categoryId; }
    public String getCategoryName() { return categoryName; }
    public List<Product> getProducts() { return products; }
}