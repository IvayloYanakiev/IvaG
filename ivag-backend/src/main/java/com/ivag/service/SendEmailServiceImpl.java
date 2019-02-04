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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

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
            if(payingMethod.equalsIgnoreCase("Cryptocurrency")){
                try {
                    mailMessage.setText(Constants.EMAIL_TEXT_SUCCESSFUL_ORDER + address.toString() + Constants.CHOSEN_PAYING_METHOD + payingMethod + Constants.TOTAL_SUM_OF_ORDER_BITCOINS + getSumAsBitcoins(totalSum));
                } catch (Exception e) {
                    throw new EmailException(e.getMessage(),e);
                }
            }
            else mailMessage.setText(Constants.EMAIL_TEXT_SUCCESSFUL_ORDER + address.toString() + Constants.CHOSEN_PAYING_METHOD + payingMethod + Constants.TOTAL_SUM_OF_ORDER_DOLARS + totalSum);

            javaMailSender.send(mailMessage);
        } catch (MailException e) {
            throw new EmailException(e.getMessage(), e);
        } catch (AddressException e) {
            throw new EmailException(e.getMessage(), e);
        }
    }

    public Double getSumAsBitcoins(Double sum) throws JSONException {
        JSONObject data = getJSONfromURL("https://blockchain.info/charts/market-price?format=json");
        JSONArray data_array = data.getJSONArray("values");

        JSONObject price_point = (JSONObject) data_array.get(data_array.length() - 1);

        double price = price_point.optDouble("y");

        return sum/price;
    }

    public JSONObject getJSONfromURL(String URL) throws JSONException {
        try {
            URLConnection uc;
            URL url = new URL(URL);
            uc = url.openConnection();
            uc.setConnectTimeout(10000);
//            uc.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            uc.connect();

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(uc.getInputStream(),
                            Charset.forName("UTF-8")));

            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }

            String jsonText = (sb.toString());

            return new JSONObject(jsonText.toString());
        } catch (IOException ex) {
            return null;
        }
    }

}
