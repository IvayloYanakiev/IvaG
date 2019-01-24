package com.ivag.dao;

import com.ivag.exception.CategoryException;

import java.util.Map;

public interface CategoryDao {
    Map getAllCategories() throws CategoryException;
}
