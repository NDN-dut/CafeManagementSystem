package com.cafe.dal.impl;

import com.cafe.dal.IProductDAO;
import com.cafe.model.Product;
import com.cafe.context.DbContext;

import java.util.ArrayList;
import java.util.List;

public class ProductRAMDAO implements IProductDAO {
    private DbContext context = DbContext.getInstance();

    @Override
    public List<Product> findByCategoryId(int categoryId) {
        throw new RuntimeException("findByCategoryId not implemented");
    }

    @Override
    public List<Product> searchByName(String name) {
        List<Product> li = new ArrayList<>();
        for (var p : context.products) {
<<<<<<< HEAD
            if (p.getProductName().contains(name))
=======
            if (p.getProductName().equals(name))
>>>>>>> a515068ebb5ee8dee012983ccc2523e5a5a7e892
                li.add(p);
        }
        return li;
    }

    @Override
    public List<Product> findAll() {
        return context.products;
    }

    @Override
    public Product findById(Integer id) {
        for (var p : context.products) {
            if (p.getProductId() == id)
                return p;
        }
        return null;
    }

    @Override
    public void insert(Product entity) {
        context.products.add(entity);
    }

    @Override
    public void update(Product entity) {
        for (int i = 0; i < context.products.size(); i++) {
            if (context.products.get(i).getProductId() == entity.getProductId()) {
                context.products.set(i, entity);
                break;
            }
        }
    }

    @Override
    public void delete(Integer id) {
        for (int i = 0; i < context.products.size(); i++) {
            if (context.products.get(i).getProductId() == id) {
                context.products.remove(i);
                break;
            }
        }
    }
}
