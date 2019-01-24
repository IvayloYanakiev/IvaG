package com.ivag.dao;


import com.ivag.exception.UserException;
import com.ivag.model.User;

import java.util.HashSet;


public interface UserDao {
    User findUserById(Long id) throws UserException;
    User findUserByEmail(String email) throws UserException;
    User registerUser(User data,String token) throws UserException;
    void checkDoesGivenUserExists(String email, String password) throws UserException;
    void checkDoesGivenUserExists(String email) throws UserException;

    void activateAccount(String token) throws UserException;

    HashSet<User> checkForUnactivatedAccounts() throws UserException;
}
