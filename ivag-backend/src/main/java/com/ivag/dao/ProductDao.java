package com.ivag.dao;

import com.ivag.exception.ProductException;
import com.ivag.model.Product;

import java.util.Collection;

public interface ProductDao {
    Collection getAllProducts() throws ProductException;
    Collection<Product> getProductsByInnerCategoryId(Long id) throws ProductException;
    Product getProductById(Long id) throws ProductException;
    Collection<Product> getProductsFilteredByName(String searchInput) throws ProductException;
    Collection<Product> getAllProductsOrderedByPrice(String orderIn) throws ProductException;
    Collection<Product> getAllProductsOrderedByDiscount(String orderIn) throws ProductException;
    Collection<Product> getAllProductsOrderedByName(String orderIn) throws ProductException;
}
