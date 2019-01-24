package com.ivag.service;

import com.ivag.exception.CategoryException;
import com.ivag.dao.CategoryDao;
import com.ivag.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Override
    public Map<Long, Category> getAllCategories() throws CategoryException {
        return categoryDao.getAllCategories();
    }
}
