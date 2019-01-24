package com.ivag.service;

import com.ivag.exception.ProductException;
import com.ivag.dao.ShoppingCartDao;
import com.ivag.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    ShoppingCartDao shoppingCartDao;

    @Override
    public Collection<Product> getProductsFromShoppingCart(String ids) throws ProductException {
        ids = ids.replace("[", "");
        ids = ids.replace("]", "");
        String[] idsProducts = ids.split(",");
        HashMap<Long, Integer> products = new HashMap<>(); //how many for given product id
        ArrayList<Long> idsInList = new ArrayList<>();
        for (int i = 0; i < idsProducts.length; i++) {
            if(idsProducts[i].length()==0) continue;
            Long productId = Long.parseLong(idsProducts[i]);
            idsInList.add(productId);
            if (products.containsKey(productId)) {
                products.put(productId, (products.get(productId) + 1));
            } else products.put(productId, 1);
        }
        Collection<Product> retrievedProducts = shoppingCartDao.getProductsFromShoppingCart(idsInList);
        Iterator<Product> it = retrievedProducts.iterator();
        while (it.hasNext()) {
            Product product = it.next();
            Long productId = product.getId();
            Integer howMany = products.get(productId);
            product.setQuantity(howMany);
        }
        return retrievedProducts;
    }
}
