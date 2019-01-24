package com.ivag.service;

import com.ivag.exception.UserException;
import com.ivag.model.User;

public interface UserService {
     User findUserById(Long id) throws UserException;
     User registerUser(User data) throws UserException;
     User findUserByEmail(String email) throws UserException ;
     void checkDoesGivenUserExists(String email,String password) throws  UserException;
     void checkDoesGivenUserExists(String email) throws  UserException;

     void activateAccount(String token) throws UserException;
}
