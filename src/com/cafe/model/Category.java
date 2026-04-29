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

    // Getters
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int id) { this.categoryId = id; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    
    public void addProduct(Product product) {
    	if (products != null && !products.contains(product)) {
    		products.add(product);
    	}
    }
    
    public void removeProduct(Product product) {
    	products.remove(product);
    }

	public List<Product> getProducts() {
		return products;
	}
	
	@Override
    public String toString() {
        return this.categoryName; // Để JComboBox hiển thị đúng tên danh mục
    }
}