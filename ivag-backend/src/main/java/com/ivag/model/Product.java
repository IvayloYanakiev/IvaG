package com.ivag.model;

import com.ivag.exception.ProductException;
import com.ivag.config.Constants;
import com.ivag.config.ConstantsErrorMessages;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

public class Product {
    private Long id;
    private String name;
    private String pictureURL;
    private Double price;
    private Long innerCategoryId;
    private Integer quantity;
    private String description;
    private Integer discount;
    private Collection<Comment> comments;

    public Product(){
        comments= new LinkedHashSet<>();
    }

    public void addComment(Comment comment){
        if(comment!=null){
            comments.add(comment);
        }
    }

    public Collection<Comment> getComments() {
        return Collections.unmodifiableCollection(comments);
    }

    public int getCommentsSize(){
        if(comments!=null){
            return this.comments.size();
        }
       return 0;
    }

    public Product(Long id, String name, String pictureURL, Double price, Long innerCategoryId, Integer quantity, String description, Integer discount) throws ProductException {
        setId(id);
        setName(name);
        setInnerCategoryId(innerCategoryId);
        setPrice(price);
        setQuantity(quantity);
        setDescription(description);
        setPictureURL(pictureURL);
        setDiscount(discount);
        comments= new LinkedHashSet<>();
    }

    public Product(String name, Long innerCategoryId, Double price, Integer quantity,String description, String pictureURL, Integer discount) throws ProductException {
        setName(name);
        setInnerCategoryId(innerCategoryId);
        setPrice(price);
        setQuantity(quantity);
        setDescription(description);
        setPictureURL(pictureURL);
        setDiscount(discount);
        comments= new LinkedHashSet<>();
    }

    public Product(Long id, String name, Long category, Double price, Integer quantity, String description, Integer discount) throws ProductException {
        setId(id);
        setName(name);
        setInnerCategoryId(category);
        setPrice(price);
        setQuantity(quantity);
        setDescription(description);
        setDiscount(discount);
        comments= new LinkedHashSet<>();
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) throws ProductException {
        if(discount != null && discount >= 0 && discount <= Constants.MAX_DISCOUNT_VALUE) {
            this.discount = discount;
        } else {
            throw new ProductException(ConstantsErrorMessages.INVALID_PRODUCT_DISCOUNT);
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws ProductException {
        if (description != null && description.trim().length() > 0 && description.trim().length() <= Constants.MAX_PRODUCT_DESCRIPTION_LENGTH) {
            this.description = description;
        } else {
            throw new ProductException(ConstantsErrorMessages.INVALID_PRODUCT_DESCRIPTION);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) throws ProductException {
        if (id != null && id > -1) {
            this.id = id;
        } else {
            throw new ProductException(ConstantsErrorMessages.INVALID_PRODUCT_ID);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws ProductException {
        if (name != null && name.trim().length() > 0 && name.trim().length() <= Constants.MAX_PRODUCT_NAME_LENGTH) {
            this.name = name;
        } else {
            throw new ProductException(ConstantsErrorMessages.INVALID_PRODUCT_NAME);
        }
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) throws ProductException {
        if (pictureURL != null && pictureURL.trim().length() > 0 && pictureURL.trim().length() <= Constants.MAX_PRODUCT_PICTURE_URL_LENGTH) {
            this.pictureURL = pictureURL;
        } else {
            throw new ProductException(ConstantsErrorMessages.INVALID_PRODUCT_PICTURE_URL);
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(Double price) throws ProductException {
        if (price != null && price > 0) {
            this.price = price;
        } else {
            throw new ProductException(ConstantsErrorMessages.INVALID_PRODUCT_PRICE);
        }
    }

    public Long getInnerCategoryId() {
        return innerCategoryId;
    }

    public void setInnerCategoryId(Long innerCategoryId) throws ProductException {
        if (innerCategoryId != null && innerCategoryId > -1) {
            this.innerCategoryId = innerCategoryId;
        } else {
            throw new ProductException(ConstantsErrorMessages.INVALID_INNER_CATEGORY_ID);
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) throws ProductException {
        if (quantity != null && quantity >= 0) {
            this.quantity = quantity;
        } else {
            throw new ProductException(ConstantsErrorMessages.INVALID_PRODUCT_QUANTITY);
        }
    }

}
