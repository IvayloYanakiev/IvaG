package com.ivag.service;

import com.ivag.exception.CategoryException;
import com.ivag.model.Category;

import java.util.Map;

public interface CategoryService {
     Map<Long, Category> getAllCategories() throws CategoryException;
}
