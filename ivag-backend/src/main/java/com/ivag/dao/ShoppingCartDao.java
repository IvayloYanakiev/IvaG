package com.ivag.dao;

import com.ivag.exception.ProductException;
import com.ivag.model.Product;

import java.util.ArrayList;
import java.util.Collection;

public interface ShoppingCartDao {
    Collection<Product> getProductsFromShoppingCart(ArrayList<Long> ids) throws ProductException;
}
