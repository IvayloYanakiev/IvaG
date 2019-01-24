package com.ivag.dao;

import com.ivag.exception.UserException;
import com.ivag.config.ConstantsErrorMessages;
import com.ivag.config.ConstantsSQL;
import com.ivag.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Override
    public User findUserById(Long id) throws UserException {
        String getUserById = ConstantsSQL.FIND_USER_BY_ID;

        HashMap<String, Object> userParams = new HashMap<>();
        userParams.put("id", id);
        User userById = null;
        try {
            userById = jdbcTemplate.query(getUserById, userParams, new ResultSetExtractor<User>() {

                @Override
                public User extractData(ResultSet rs) throws SQLException {
                    User userById = new User();
                    if (rs.next()) {
                        try {
                            userById.setId(id);
                            userById.setName(rs.getString("name"));
                            userById.setEmail(rs.getString("email"));
                            if (rs.getString("phone") != null) {
                                userById.setPhone(rs.getString("phone"));
                            }
                            if (rs.getString("gender") != null) {
                                userById.setGender(rs.getString("gender"));
                            }
                            if (rs.getString("profile_url") != null) {
                                userById.setPictureUrl(rs.getString("profile_url"));
                            }
                            userById.setType(rs.getInt("isAdmin"));
                        } catch (UserException e) {
                            logger.error(e.getMessage());
                            throw new SQLException(e.getMessage());
                        }
                    }
                    return userById;
                }
            });
        } catch (Exception e) {
            throw new UserException(e.getMessage(), e);
        }
        return userById;
    }

    @Override
    public User findUserByEmail(String email) throws UserException {
        checkDoesGivenUserExists(email);
        String getUserByEmail = ConstantsSQL.FIND_USER_BY_EMAIL;

        HashMap<String, Object> userParams = new HashMap<>();
        userParams.put("email", email);
        User user = null;
        try {
            user = jdbcTemplate.query(getUserByEmail, userParams, new ResultSetExtractor<User>() {

                @Override
                public User extractData(ResultSet rs) throws SQLException {
                    User user = new User();
                    if (rs.next()) {
                        try {
                            user.setId(rs.getLong("id"));
                            user.setName(rs.getString("name"));
                            user.setEmail(rs.getString("email"));
                            user.setType(rs.getInt("isAdmin"));
                            user.setIsActivated(rs.getInt("isActivated"));
                        } catch (UserException e) {
                            logger.error(e.getMessage());
                            throw new SQLException(e.getMessage());
                        }
                    }
                    return user;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new UserException(e.getMessage(), e);
        }
        return user;
    }

    @Override
    public User registerUser(User user, String token) throws UserException {
        String registerUser = ConstantsSQL.ADD_USER;
        HashMap<String, Object> userParams = new HashMap<>();
        userParams.put("name", user.getName());
        userParams.put("email", user.getEmail());
        userParams.put("password", user.getPassword());
        userParams.put("profileUrl", user.getPictureUrl());
        userParams.put("token", token);
        boolean checker = false;
        try {
            checkDoesGivenUserExists(user.getEmail());
            checker = true;
        } catch (UserException e) {
            logger.error(e.getMessage());
            try {
                jdbcTemplate.update(registerUser, userParams);
            } catch (Exception ex) {
                logger.error(e.getMessage());
                throw new UserException(ex.getMessage(), e);
            }
        }
        if (checker) throw new UserException(ConstantsErrorMessages.USER_ALREADY_EXISTS);
        return user;
    }

    public void checkDoesGivenUserExists(String email) throws UserException {
        String checkForUserRequest = ConstantsSQL.FIND_USER_BY_EMAIL;

        HashMap<String, Object> userParams = new HashMap<>();
        userParams.put("email", email);
        Boolean checkForUser = checkForUser(checkForUserRequest, userParams);
        if (!checkForUser) throw new UserException(ConstantsErrorMessages.NO_SUCH_USER);
    }

    @Override
    public void activateAccount(String token) throws UserException {
        String activateAccount = ConstantsSQL.ACTIVATE_REGISTERED_ACCOUNT;
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", token);
        try {
            jdbcTemplate.update(activateAccount, params);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new UserException(ConstantsErrorMessages.PROBLEM_ACTIVATING_ACCOUNT);
        }
    }

    @Override
    public HashSet<User> checkForUnactivatedAccounts() throws UserException {
        String getUnactivatedUsers = ConstantsSQL.SELECT_FROM_USERS_WHERE_IS_ACTIVATED_0;
        HashSet<User> users;
        try {
            users = jdbcTemplate.query(getUnactivatedUsers, new ResultSetExtractor<HashSet<User>>() {

                @Override
                public HashSet<User> extractData(ResultSet rs) throws SQLException {
                    HashSet<User> myUsers = new HashSet<>();

                    while (rs.next()) {
                        try {
                            User user = new User();
                            user.setEmail(rs.getString("email"));
                            user.setToken(rs.getString("token"));
                            myUsers.add(user);
                        } catch (UserException e) {
                            logger.error(e.getMessage());
                            throw new SQLException(e.getMessage());
                        }
                    }
                    return myUsers;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new UserException(e.getMessage(), e);
        }
        return users;
    }

    public void checkDoesGivenUserExists(String email, String password) throws UserException {
        String checkForUserRequest = ConstantsSQL.SELECT_USER_BY_EMAIL_AND_PASS;

        HashMap<String, Object> userParams = new HashMap<>();
        userParams.put("email", email);
        userParams.put("password", password);
        Boolean checkForUser = checkForUser(checkForUserRequest, userParams);
        if (!checkForUser) {
            logger.error(ConstantsErrorMessages.WRONG_USERNAME_OR_PASSWORD);
            throw new UserException(ConstantsErrorMessages.WRONG_USERNAME_OR_PASSWORD);
        }
    }

    private Boolean checkForUser(String checkForUserRequest, HashMap<String, Object> userParams) throws UserException {
        Boolean checkForUser;
        try {
            checkForUser = jdbcTemplate.query(checkForUserRequest, userParams, new ResultSetExtractor<Boolean>() {

                @Override
                public Boolean extractData(ResultSet rs) throws SQLException {
                    return rs.next();
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new UserException(e.getMessage(), e);
        }
        return checkForUser;
    }

}
