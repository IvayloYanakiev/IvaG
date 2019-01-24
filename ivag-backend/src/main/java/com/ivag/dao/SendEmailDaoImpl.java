package com.ivag.dao;

import com.ivag.exception.EmailException;
import com.ivag.config.ConstantsErrorMessages;
import com.ivag.config.ConstantsSQL;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class SendEmailDaoImpl implements SendEmailDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final Logger logger = Logger.getLogger(SendEmailDaoImpl.class);

    @Override
    public void sendEmail(String email) throws EmailException {
        String updateSubscribeStatus = ConstantsSQL.UPDATE_USER_SUBSCRIBE_STATUS;
        HashMap<String, Object> params = new HashMap<>();
        params.put("email", email);
        try {
            jdbcTemplate.update(updateSubscribeStatus, params);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new EmailException(ConstantsErrorMessages.NO_SUCH_EMAIL);
        }
    }

}
