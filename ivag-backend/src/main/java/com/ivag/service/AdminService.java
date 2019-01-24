package com.ivag.service;

import com.ivag.exception.ProductException;
import com.ivag.model.Product;

public interface AdminService {
    void addProduct(Product product) throws ProductException;
    void deleteProductById(Long id) throws ProductException;
    void updateProductById(Product product) throws ProductException;
}
