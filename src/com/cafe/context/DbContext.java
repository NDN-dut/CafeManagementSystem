package com.cafe.context;

import java.util.ArrayList;
import java.util.List;

import com.cafe.model.CafeTable;
import com.cafe.model.Category;
import com.cafe.model.Order;
import com.cafe.model.Product;

public class DbContext {
    private static DbContext instance;
    public List<Product> products = new ArrayList<>();
    public List<CafeTable> tables = new ArrayList<>();
    public List<Order> orders = new ArrayList<>();
    public List<Category> categories = new ArrayList<Category>();

    private DbContext() { 
        // Seed Data: Thêm sẵn vài món cafe để test
    	Category cCoffee = new Category(1, "Coffee");
    	Category cMilkTea = new Category(2, "Milk Tea");
    	Category cSnack = new Category(3, "Snack");
    	categories.add(cCoffee);
    	categories.add(cMilkTea);
    	categories.add(cSnack);
    	
    	products.add(new Product(1, "Black Coffee", 15000, cCoffee));
    	products.add(new Product(2, "Milk Coffee", 17000, cCoffee));
    	products.add(new Product(3, "Macchiato", 20000, cCoffee));
    	
    	products.add(new Product(4, "Tapioca Pudding Milk Tea", 20000, cMilkTea));
    	products.add(new Product(5, "Strawberry Milk Tea", 20000, cMilkTea));
    	products.add(new Product(6, "Full Topping Milk Tea", 25000, cMilkTea));
    	
    	products.add(new Product(7, "Yaourt", 10000, cSnack));
    	products.add(new Product(8, "Cream", 10000, cSnack));
    	
    	tables.add(new CafeTable(1, "Table 1"));
    	tables.add(new CafeTable(2, "Table 2"));
    	tables.add(new CafeTable(3, "Table 3"));	
    }

    public static DbContext getInstance() {
        if (instance == null) instance = new DbContext();
        return instance;
    }
}
