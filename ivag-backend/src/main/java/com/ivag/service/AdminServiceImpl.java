package com.ivag.service;

import com.ivag.exception.ProductException;
import com.ivag.dao.AdminDao;
import com.ivag.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Override
    public void addProduct(Product product) throws ProductException {
        adminDao.addProduct(product);
    }

    @Override
    public void deleteProductById(Long id) throws ProductException {
        adminDao.deleteProductById(id);
    }

    @Override
    public void updateProductById(Product product) throws ProductException {
        adminDao.updateProduct(product);
    }
}
