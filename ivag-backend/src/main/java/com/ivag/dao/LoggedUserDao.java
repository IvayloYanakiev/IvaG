package com.ivag.dao;


import com.ivag.exception.UserException;
import com.ivag.model.User;

public interface LoggedUserDao {
    void updateUserPersonalInfo(User user) throws UserException;

    void updateUserProfilePicture(Long userId, String pictureUrl) throws UserException;
}
