package com.ivag.service;

import com.ivag.exception.AddressException;
import com.ivag.exception.EmailException;
import com.ivag.config.Constants;
import com.ivag.dao.AddressDao;
import com.ivag.dao.SendEmailDao;
import com.ivag.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailServiceImpl implements SendEmailService {

    @Autowired
    AddressDao addressDao;

    @Autowired
    SendEmailDao sendEmailDao;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String email) throws EmailException {
        sendEmailDao.sendEmail(email);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setFrom(Constants.SENDER_EMAIL);
        mailMessage.setSubject(Constants.EMAIL_SUBJECT_SUBSCRIPTION);
        mailMessage.setText(Constants.EMAIL_TEXT_SUCCESSFUL_SUBSCRIPTION);
        try {
            javaMailSender.send(mailMessage);
        } catch (MailException e) {
            throw new EmailException(e.getMessage(), e);
        }
    }

    @Override
    public void informUserForOrder(String email, Long addressId, String payingMethod, Double totalSum, String shoppingCart) throws EmailException {
        try {
            Address address = addressDao.getAddress(addressId);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setFrom(Constants.SENDER_EMAIL);
            mailMessage.setSubject(Constants.EMAIL_SUBJECT_ORDER_INFORMATION);
            mailMessage.setText(Constants.EMAIL_TEXT_SUCCESSFUL_ORDER + address.toString() + Constants.CHOSEN_PAYING_METHOD + payingMethod + Constants.TOTAL_SUM_OF_ORDER + totalSum);

            javaMailSender.send(mailMessage);
        } catch (MailException e) {
            throw new EmailException(e.getMessage(), e);
        } catch (AddressException e) {
            throw new EmailException(e.getMessage(), e);
        }
    }

}
