package com.ivag.model;

import com.ivag.exception.CategoryException;
import com.ivag.config.Constants;
import com.ivag.config.ConstantsErrorMessages;

import java.util.LinkedHashSet;

public class Category {
    private Long id;
    private String name;
    private LinkedHashSet<Category> innerCategories;

    public Category(Long id, String name) throws CategoryException {
        setId(id);
        setName(name);
        this.innerCategories = new LinkedHashSet<>();
    }

    public void setName(String name) throws CategoryException {
        if (name != null && name.trim().length() > 0 && name.trim().length() <= Constants.MAX_CATEGORY_NAME_LENGTH) {
            this.name = name;
        } else throw new CategoryException(ConstantsErrorMessages.INVALID_CATEGORY_NAME);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) throws CategoryException {
        if(id != null && id > -1){
            this.id = id;
        } else {
            throw new CategoryException(ConstantsErrorMessages.INVALID_CATEGORY_ID);
        }
    }

    public String getName() {
        return name;
    }

    public void addCategory(Category category) throws CategoryException {
        if(category!=null){
            innerCategories.add(category);
        }
        else throw new CategoryException(ConstantsErrorMessages.INVALID_INNER_CATEGORY);
    }

}
