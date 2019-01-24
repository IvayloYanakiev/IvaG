package com.ivag.dao;

import com.ivag.exception.UserException;
import com.ivag.config.ConstantsErrorMessages;
import com.ivag.config.ConstantsSQL;
import com.ivag.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class LoggedUserDaoImpl implements LoggedUserDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final Logger logger = Logger.getLogger(LoggedUserDaoImpl.class);

    @Override
    public void updateUserPersonalInfo(User user) throws UserException {
        String updateUserPersonalInfo = ConstantsSQL.UPDATE_USER_PERSONAL_INFO;
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", user.getId());
        params.put("name", user.getName());
        params.put("email", user.getEmail());
        params.put("phone", user.getPhone());
        params.put("gender", user.getGender());
        try {
            jdbcTemplate.update(updateUserPersonalInfo, params);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new UserException(ConstantsErrorMessages.ERROR_UPDATING_USER, e);
        }
    }

    @Override
    public void updateUserProfilePicture(Long userId, String pictureUrl) throws UserException {
        String updateUserProfilePicture = ConstantsSQL.UPDATE_USER_PERSONAL_PICTURE;
        HashMap<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("profile_url", pictureUrl);
        try {
            jdbcTemplate.update(updateUserProfilePicture, params);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new UserException(ConstantsErrorMessages.ERROR_UPDATING_USER);
        }
    }
}
