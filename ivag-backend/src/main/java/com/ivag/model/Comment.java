package com.ivag.model;

import com.ivag.exception.CommentException;
import com.ivag.config.Constants;
import com.ivag.config.ConstantsErrorMessages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Comment {
    private Long id;
    private Long productId;
    private Long userId;
    private String userNames;
    private String value;
    private Integer stars;
    private String profileUrl;
    private String creatingDate;

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) throws CommentException {
        if (stars != null && stars >= 0 && stars <= Constants.MAX_STARS_VALUE)
            this.stars = stars;
        else throw new CommentException(ConstantsErrorMessages.INVALID_COMMENT_STARS);
    }

    public String getUserNames() {
        return userNames;
    }

    public void setUserNames(String userNames) throws CommentException {
        if (userNames != null && userNames.trim().length() > 0 && userNames.length() <= Constants.MAX_USER_NAME_LENGTH) {
            this.userNames = userNames;
        } else throw new CommentException(ConstantsErrorMessages.INVALID_USER_NAMES_OF_COMMENT);
    }

    public Comment(Long productId, Long userId, String value, Integer stars) throws CommentException {
        setProductId(productId);
        setUserId(userId);
        setValue(value);
        setStars(stars);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_AND_TIME_FORMATTER);
        String formatDateTime = now.format(formatter);
        this.creatingDate = formatDateTime;
    }

    public String getCreatingDate() {
        return creatingDate;
    }

    public Comment(Long id, Long productId, Long userId, String namesOfUser, String commentValue, Integer stars, String profileUrl,String creatingDate) throws CommentException {
        setId(id);
        setProductId(productId);
        setUserId(userId);
        setValue(commentValue);
        setUserNames(namesOfUser);
        setStars(stars);
        setProfileUrl(profileUrl);
        setCreatingDate(creatingDate);
    }

    public void setCreatingDate(String creatingDate) throws CommentException {
        if (creatingDate != null)
            this.creatingDate = creatingDate;
        else throw new CommentException(ConstantsErrorMessages.INVALID_CREATION_DATE);
    }

    public Comment(Long id, Long productId, Long userId, String userNames, String value, Integer stars) throws CommentException {
        setId(id);
        setProductId(productId);
        setUserId(userId);
        setValue(value);
        setUserNames(userNames);
        setStars(stars);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) throws CommentException {
        if (id != null && id > -1)
            this.id = id;
        else throw new CommentException(ConstantsErrorMessages.INVALID_COMMENT_ID);
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) throws CommentException {
        if (productId != null && productId > -1)
            this.productId = productId;
        else throw new CommentException(ConstantsErrorMessages.INVALID_PRODUCT_ID);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) throws CommentException {
        if (userId != null && userId > -1)
            this.userId = userId;
        else throw new CommentException(ConstantsErrorMessages.INVALID_USER_ID);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) throws CommentException {
        if (value != null && value.trim().length() > 0 && value.trim().length() <= Constants.MAX_COMMENT_VALUE_LENGTH) {
            this.value = value;
        } else throw new CommentException(ConstantsErrorMessages.INVALID_VALUE_OF_COMMENT);
    }
}
