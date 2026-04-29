package com.cafe.model;

public class Product {
    private int productId;
    private String productName;
    private double price;
    private Category category; // Tham chiếu đối tượng

    public Product(int productId, String productName, double price, Category category) {
        this.productId = productId;
        this.productName = productName;
        //this.price = price;
        this.setPrice(price);
        this.category = category;
    }

    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public double getPrice() { return price; }
    public Category getCategory() { return category; }
    
    public void setPrice(double price) {
    	if (this.price > 0)
    		this.price = price;
    	else 
    		this.price = 0;
    }
}