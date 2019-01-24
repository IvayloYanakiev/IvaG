package com.ivag.dao;

import com.ivag.exception.CommentException;
import com.ivag.exception.ProductException;
import com.ivag.config.ConstantsSQL;
import com.ivag.model.Comment;
import com.ivag.model.Product;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private  static final Logger logger = Logger.getLogger(ProductDaoImpl.class);

    @Override
    public Collection<Product> getProductsByInnerCategoryId(Long id) throws ProductException {
        String getProducts = ConstantsSQL.GET_ALL_PRODUCTS_BY_INNER_CATEGORY_ID;
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        LinkedHashSet<Product> products = getProducts(getProducts, params);

        return products;
    }

    private LinkedHashSet<Product> getProducts(String getProducts, HashMap<String, Object> params) throws ProductException {
        try {
            return jdbcTemplate.query(getProducts, params, new ResultSetExtractor<LinkedHashSet<Product>>() {
                @Override
                public LinkedHashSet<Product> extractData(ResultSet rs) throws SQLException {
                    LinkedHashSet<Product> myProducts = new LinkedHashSet<>();

                    while (rs.next()) {
                        addProductFromResultToHashSet(rs, myProducts);
                    }
                    return myProducts;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ProductException(e.getMessage(), e);
        }
    }

    private void addProductFromResultToHashSet(ResultSet rs, HashSet<Product> myProducts) throws SQLException {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        String pictureUrl = rs.getString("picture_url");
        Double price = rs.getDouble("price");
        Long middleTypeId = rs.getLong("middle_type_id");
        Integer quantity = rs.getInt("quantity");
        String description = rs.getString("description");
        Integer discount = rs.getInt("discount");
        try {
            Product product = new Product(id, name, pictureUrl, price, middleTypeId, quantity, description, discount);
            myProducts.add(product);
        } catch (ProductException e) {
            logger.error(e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }

    public Collection<Product> getAllProducts() throws ProductException {
        String getProducts = ConstantsSQL.GET_ALL_PRODUCTS;
        LinkedHashSet<Product> products = getProducts(getProducts);
        return products;
    }

    @Override
    public Product getProductById(Long id) throws ProductException {
        String getProductById = ConstantsSQL.GET_PRODUCT_BY_ID;
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        Product productById = null;
        try {
            productById = jdbcTemplate.query(getProductById, params, new ResultSetExtractor<Product>() {
                @Override
                public Product extractData(ResultSet rs) throws SQLException {
                    Product selectedProduct = new Product();
                    while (rs.next()) {
                        try {
                            Long commentId = rs.getLong("comment_id");
                            if (commentId != 0) {
                                if (selectedProduct.getCommentsSize() == 0) {
                                    setProductProperties(rs, selectedProduct);
                                }
                                selectedProduct.addComment(
                                        new Comment(
                                                commentId,
                                                rs.getLong("user_id"),
                                                rs.getLong("product_id"),
                                                rs.getString("uname"), rs.getString("value"),
                                                rs.getInt("stars"), rs.getString("profile_url"),
                                                rs.getString("creation_date")
                                        ));
                            } else {
                                setProductProperties(rs, selectedProduct);
                            }
                        } catch (ProductException | CommentException e) {
                            logger.error(e.getMessage());
                            throw new SQLException(e.getMessage());
                        }
                    }
                    return selectedProduct;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ProductException(e.getMessage(), e);
        }
        return productById;
    }

    private void setProductProperties(ResultSet rs, Product selectedProduct) throws ProductException, SQLException {
        selectedProduct.setId(rs.getLong("product_id"));
        selectedProduct.setName(rs.getString("name"));
        selectedProduct.setPictureURL(rs.getString("picture_url"));
        selectedProduct.setPrice(rs.getDouble("price"));
        selectedProduct.setQuantity(rs.getInt("quantity"));
        selectedProduct.setDescription(rs.getString("description"));
        selectedProduct.setDiscount(rs.getInt("discount"));
    }

    @Override
    public Collection<Product> getAllProductsOrderedByPrice(String orderIn) throws ProductException {
        String getProducts = ConstantsSQL.ORDER_PRODUCTS_BY_PRICE;
        if (orderIn.equals("asc")) {
            getProducts += " asc";
        } else {
            getProducts += " desc";
        }
        LinkedHashSet<Product> products = getProducts(getProducts);
        return products;
    }

    @Override
    public Collection<Product> getProductsFilteredByName(String searchInput) throws ProductException {
        searchInput = "%" + searchInput.trim() + "%";
        String getProducts = ConstantsSQL.GET_PRODUCTS_FILTERED_BY_NAME;
        HashMap<String, Object> params = new HashMap<>();
        params.put("searchInput", searchInput);
        LinkedHashSet<Product> products = getProducts(getProducts, params);
        return products;
    }

    public Collection<Product> getAllProductsOrderedByDiscount(String orderIn) throws ProductException {
        String getProducts = ConstantsSQL.ORDER_PRODUCTS_BY_DISCOUNT;
        if (orderIn.equals("asc")) {
            getProducts += " asc";
        } else {
            getProducts += " desc";
        }
        LinkedHashSet<Product> products = getProducts(getProducts);
        return products;
    }

    @Override
    public Collection<Product> getAllProductsOrderedByName(String orderIn) throws ProductException {
        String getProducts = ConstantsSQL.ORDER_PRODUCTS_BY_NAME;
        if (orderIn.equals("asc")) {
            getProducts += " asc";
        } else {
            getProducts += " desc";
        }
        LinkedHashSet<Product> products = getProducts(getProducts);
        return products;
    }

    private LinkedHashSet<Product> getProducts(String getProducts) throws ProductException {
        try {
            return jdbcTemplate.query(getProducts, new ResultSetExtractor<LinkedHashSet<Product>>() {

                @Override
                public LinkedHashSet<Product> extractData(ResultSet rs) throws SQLException {
                    LinkedHashSet<Product> myProducts = new LinkedHashSet<>();

                    while (rs.next()) {
                        addProductFromResultToHashSet(rs, myProducts);
                    }
                    return myProducts;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ProductException(e.getMessage(), e);
        }
    }
}

