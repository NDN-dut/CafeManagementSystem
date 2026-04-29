package com.cafe.dal.impl;

import com.cafe.dal.ICategoryDAO;
import com.cafe.model.Category;
import com.cafe.context.DbContext;
<<<<<<< HEAD

import java.util.ArrayList;
=======
>>>>>>> a515068ebb5ee8dee012983ccc2523e5a5a7e892
import java.util.List;

public class CategoryRAMDAO implements ICategoryDAO {
    private DbContext context = DbContext.getInstance();

    @Override
    public List<Category> findAll() {
        return context.categories;
    }

    @Override
    public Category findById(Integer id) {
        for (var c : context.categories) {
            if (c.getCategoryId() == id) {
                return c;
            }
        }
        return null;
    }

    @Override
    public void insert(Category entity) {
<<<<<<< HEAD
    	entity.setCategoryId(context.categories.size() + 1);
=======
>>>>>>> a515068ebb5ee8dee012983ccc2523e5a5a7e892
        context.categories.add(entity);
    }

    @Override
    public void update(Category entity) {
        for (int i = 0; i < context.categories.size(); i++) {
            if (context.categories.get(i).getCategoryId() == entity.getCategoryId()) {
                context.categories.set(i, entity);
                break;
            }
        }
    }

    @Override
    public void delete(Integer id) {
        for (int i = 0; i < context.categories.size(); i++) {
            if (context.categories.get(i).getCategoryId() == id) {
                context.categories.remove(i);
                break;
            }
        }
    }
<<<<<<< HEAD

	@Override
	public List<Category> searchByName(String name) {
		List<Category> li = new ArrayList<Category>();
		for (Category c : context.categories) {
			if (c.getCategoryName().contains(name)) {
				li.add(c);
			}
		}
		return li;
	}
    
=======
>>>>>>> a515068ebb5ee8dee012983ccc2523e5a5a7e892
}
