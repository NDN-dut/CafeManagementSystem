package com.cafe.dal;

import com.cafe.model.Product;
import com.cafe.model.Category;
import java.util.List;

// Quản lý Thực đơn
public interface IProductDAO extends IBaseDAO<Product, Integer> {
    List<Product> findByCategoryId(int categoryId);
    List<Product> searchByName(String name);
}
