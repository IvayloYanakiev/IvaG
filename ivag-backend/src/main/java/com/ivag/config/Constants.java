package com.ivag.config;

import java.util.regex.Pattern;

public class Constants {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final String SUCCESS = "Success";
    public static final int PHONE_NUMBER_LENGTH = 10;
    public static final String ERROR = "Error";
    public static final String PHONE_NUMBER_PREFIX = "08";
    public static final int MIN_CITY_NAME_LENGTH = 3;
    public static final int MAX_CITY_NAME_LENGTH = 20;
    public static final int MAX_RECEIVER_NAME_LENGTH = 45;
    public static final int MAX_CATEGORY_NAME_LENGTH = 45;
    public static final int MAX_STREET_INFO_LENGTH = 50;
    public static final int MIN_PASSWORD_LENGTH = 5;
    public static final int MIN_EMAIL_LENGTH = 5;
    public static final int MAX_DISCOUNT_VALUE = 100;
    public static final int MAX_PRODUCT_DESCRIPTION_LENGTH = 1000;
    public static final int MAX_PRODUCT_NAME_LENGTH = 45;
    public static final int MAX_PRODUCT_PICTURE_URL_LENGTH = 100;
    public static final int MAX_USER_NAME_LENGTH = 45;
    public static final int MAX_USER_PASSWORD_LENGTH = 45;
    public static final int MAX_EMAIL_LENGTH = 45;
    public static final int MAX_USER_PROFILE_PICTURE_URL_LENGTH = 100;
    public static final int MAX_USER_GENDER_VALUE_LENGTH = 45;
    public static final String SENDER_EMAIL = "ivag.7377@gmail.com";
    public static final String EMAIL_SUBJECT_SUBSCRIPTION = "Subscription for eMAG.bg";
    public static final String EMAIL_SUBJECT_ORDER_INFORMATION = "Order information";
    public static final String EMAIL_TEXT_SUCCESSFUL_SUBSCRIPTION = "Congratulations!\nYou have successfully subscribed to eMAG.bg";
    public static final String EMAIL_TEXT_SUCCESSFUL_ORDER = "Your order was successful!\nOrder details:\n";
    public static final String CHOSEN_PAYING_METHOD ="\nMethod of paying: ";
    public static final String NO_PICTURE = "http://res.cloudinary.com/dxnmejm7r/image/upload/v1525705078/zx95yaxdahmoocm9fbt4.jpg";
    public static final int MAX_COMMENT_VALUE_LENGTH = 100;
    public static final int MAX_STARS_VALUE = 5;
    public static final String DATE_AND_TIME_FORMATTER =  " HH:mm dd-MM-yyyy ";
    public static final String FILE_PATH_NAME = "D:\\emagPictures\\";
    public static final String CHECK_YOUR_EMAIL_FOR_ACTIVATION_LINK = "Check your email for activation link";
    public static final String ACTIVATE_ACCOUNT = "Activate account";
    public static final String LINK_FRONTEND_ACTIVATE_ACCOUNT = "To activate your account please click on the link below: \n http://localhost:63342/ivag-frontend/app/index.html?_ijt=ov0j7am0hja4huk7t57pv0mj0u#/activateAccount/";
    public static final String ACTIVATION_SUCCESS = "Activation success";
    public static final String TOTAL_SUM_OF_ORDER_DOLARS = "\nTotal sum of your order is: $";
    public static final String TOTAL_SUM_OF_ORDER_BITCOINS = "\nTotal sum of your order is: Ƀ";

}
