package com.ivag.dao;

import com.ivag.exception.ProductException;
import com.ivag.config.ConstantsErrorMessages;
import com.ivag.config.ConstantsSQL;
import com.ivag.model.Product;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class AdminDaoImpl implements AdminDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final Logger logger = Logger.getLogger(AddressDaoImpl.class);

    @Override
    public void addProduct(Product product) throws ProductException {
        String addProduct = ConstantsSQL.INSERT_INTO_PRODUCTS;
        HashMap<String, Object> params = new HashMap<>();
        params.put("name", product.getName());
        params.put("picture_url", product.getPictureURL());
        params.put("price", product.getPrice());
        params.put("middle_type_id", product.getInnerCategoryId());
        params.put("quantity", product.getQuantity());
        params.put("description", product.getDescription());
        params.put("discount", product.getDiscount());
        try {
            jdbcTemplate.update(addProduct, params);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ProductException(ConstantsErrorMessages.ERROR_ADDING_PRODUCT, e);
        }
    }

    @Override
    public void deleteProductById(Long productId) throws ProductException {
        String deleteProductById = ConstantsSQL.DELETE_PRODUCT_BY_ID;
        HashMap<String, Object> params = new HashMap<>();
        params.put("productId", productId);
        try {
            jdbcTemplate.update(deleteProductById, params);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ProductException(ConstantsErrorMessages.UNSUCCESSFUL_PRODUCT_DELETING, e);
        }
    }

    @Override
    public void updateProduct(Product product) throws ProductException {
        String updateProduct = ConstantsSQL.UPDATE_PRODUCT_BY_ID;
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", product.getId());
        params.put("name", product.getName());
        params.put("categoryId", product.getInnerCategoryId());
        params.put("price", product.getPrice());
        params.put("quantity", product.getQuantity());
        params.put("description", product.getDescription());
        params.put("discount", product.getDiscount());
        try {
            jdbcTemplate.update(updateProduct, params);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ProductException(ConstantsErrorMessages.UNSUCCESSFUL_PRODUCT_UPDATING, e);
        }
    }
}
