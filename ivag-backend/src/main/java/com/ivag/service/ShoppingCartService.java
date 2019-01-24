package com.ivag.service;

import com.ivag.exception.ProductException;
import com.ivag.model.Product;
import java.util.Collection;

public interface ShoppingCartService {
    Collection<Product> getProductsFromShoppingCart(String ids) throws ProductException;
}
