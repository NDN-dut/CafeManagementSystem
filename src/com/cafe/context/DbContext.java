package com.cafe.context;

import java.util.ArrayList;
import java.util.List;

import com.cafe.model.CafeTable;
import com.cafe.model.Order;
import com.cafe.model.Product;

public class DbContext {
    private static DbContext instance;
    public List<Product> products = new ArrayList<>();
    public List<CafeTable> tables = new ArrayList<>();
    public List<Order> orders = new ArrayList<>();

    private DbContext() { 
        // Seed Data: Thêm sẵn vài món cafe để test
    }

    public static DbContext getInstance() {
        if (instance == null) instance = new DbContext();
        return instance;
    }
}
