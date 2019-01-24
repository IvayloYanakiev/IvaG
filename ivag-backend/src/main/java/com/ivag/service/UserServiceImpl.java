package com.ivag.service;

import com.ivag.exception.UserException;
import com.ivag.config.Constants;
import com.ivag.config.ConstantsErrorMessages;
import com.ivag.config.SecureTokenGenerator;
import com.ivag.dao.CategoryDaoImpl;
import com.ivag.dao.UserDao;
import com.ivag.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;

@Service
@EnableScheduling
public class UserServiceImpl implements UserService {


    @Autowired
    UserDao userDao;

    private static final Logger logger = Logger.getLogger(CategoryDaoImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    SecureTokenGenerator secureTokenGenerator;

    @Override
    public User findUserById(Long id) throws UserException {
        return userDao.findUserById(id);
    }

    @Override
    public User registerUser(User data) throws UserException {
        String token = secureTokenGenerator.nextToken();
        User user = userDao.registerUser(data, token);
        sendActivationToken(data.getEmail(), token);

        return user;
    }

    private void sendActivationToken(String email, String token) throws UserException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setFrom(Constants.SENDER_EMAIL);
        mailMessage.setSubject(Constants.ACTIVATE_ACCOUNT);
        mailMessage.setText(Constants.LINK_FRONTEND_ACTIVATE_ACCOUNT + token);
        try {
            javaMailSender.send(mailMessage);
        } catch (MailException e) {
            throw new UserException(ConstantsErrorMessages.ERROR_SENDING_ACTIVATION_LINK);
        }
    }

    @Override
    public User findUserByEmail(String email) throws UserException {
        User user = userDao.findUserByEmail(email);
        if (user.getIsActivated() == 0) throw new UserException(ConstantsErrorMessages.ACCOUNT_IS_NOT_ACTIVATED);
        return user;
    }

    @Override
    public void checkDoesGivenUserExists(String email, String password) throws UserException {
        userDao.checkDoesGivenUserExists(email, password);
    }

    @Override
    public void checkDoesGivenUserExists(String email) throws UserException {
        userDao.checkDoesGivenUserExists(email);
    }

    @Override
    public void activateAccount(String token) throws UserException {
        userDao.activateAccount(token);
    }

    @Scheduled(fixedRate = 600000)
    private void checkForUnactivatedAccounts() {
        Thread thread = new Thread(() -> {
            try {
                HashSet<User> users = userDao.checkForUnactivatedAccounts();
                for (Iterator<User> iterator = users.iterator(); iterator.hasNext(); ) {
                    User user = iterator.next();
                    sendActivationToken(user.getEmail(), user.getToken());
                }
            } catch (UserException e) {
                logger.error(e.getMessage());
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

}
