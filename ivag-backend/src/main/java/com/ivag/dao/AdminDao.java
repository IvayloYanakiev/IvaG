package com.ivag.dao;

import com.ivag.exception.ProductException;
import com.ivag.model.Product;

public interface AdminDao {
    void addProduct(Product product) throws ProductException;
    void deleteProductById(Long id) throws ProductException;
    void updateProduct(Product product) throws ProductException;
}
