package com.ivag.service;

import com.ivag.exception.ProductException;
import com.ivag.model.Product;

import java.util.Collection;

public interface ProductService {
    Collection<Product> getProductsByInnerCategoryId(Long id) throws ProductException;
    Collection<Product> getAllProducts() throws ProductException;
    Product getProductById(Long id) throws ProductException;
    Collection<Product> getProductsFilteredByName(String searchInput) throws ProductException;
    Collection<Product> getAllProductsOrderedByPrice(String orderIn) throws ProductException;
    Collection<Product> getAllProductsOrderedByDiscount(String orderIn) throws ProductException;
    Collection<Product> getAllProductsOrderedByName(String orderIn) throws ProductException;
}
