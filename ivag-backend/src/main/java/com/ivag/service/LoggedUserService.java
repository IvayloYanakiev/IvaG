package com.ivag.service;

import com.ivag.exception.UserException;
import com.ivag.model.User;

public interface LoggedUserService {
    void updateUserPersonalInfo(User user) throws UserException;
    void updateUserProfilePicture(Long userId,String pictureUrl) throws UserException;
}
