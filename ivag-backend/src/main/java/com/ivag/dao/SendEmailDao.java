package com.ivag.dao;

import com.ivag.exception.EmailException;

public interface SendEmailDao {
    void sendEmail(String email) throws EmailException;
}
