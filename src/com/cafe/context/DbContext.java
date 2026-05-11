package com.cafe.context;

import com.cafe.model.*;
import java.util.ArrayList;
import java.util.List;

import com.cafe.model.CafeTable;
import com.cafe.model.Category;
import com.cafe.model.Order;
import com.cafe.model.Product;

public class DbContext {
    private static DbContext instance;
    public List<CafeTable> tables = new ArrayList<>();
    public List<Category> categories = new ArrayList<>();
    public List<Product> products = new ArrayList<>();
    public List<Order> orders = new ArrayList<>();
    public List<Account> accounts = new ArrayList<>();

    private DbContext() {
        seedData();
    }

    public static DbContext getInstance() {
        if (instance == null) instance = new DbContext();
        return instance;
    }

    private void seedData() {
        // 0. Tạo Accounts
        accounts.add(new Account(1, "admin", "admin123", Role.ADMIN, true));
        accounts.add(new Account(2, "staff", "staff123", Role.STAFF, true));

        // 1. Tạo Category
        Category cafe = new Category(1, "Cà phê");
        Category tea = new Category(2, "Trà sữa");
        categories.add(cafe);
        categories.add(tea);

        // 2. Tạo Product và thêm vào Category tương ứng
        Product p1 = new Product(1, "Cà phê Đen", 20000, cafe);
        Product p2 = new Product(2, "Cà phê Sữa", 25000, cafe);
        cafe.addProduct(p1);
        cafe.addProduct(p2);

        Product p3 = new Product(3, "Trà sữa Trân châu", 35000, tea);
        tea.addProduct(p3);

        // 3. Tạo Bàn
        for (int i = 1; i <= 10; i++) {
            tables.add(new CafeTable(i, "Bàn " + i));
        }

        // Lưu vào list chung của context
        products.add(p1);
        products.add(p2);
        products.add(p3);
    }
}