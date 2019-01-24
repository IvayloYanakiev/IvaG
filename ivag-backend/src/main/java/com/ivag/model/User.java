package com.ivag.model;

import com.ivag.exception.UserException;
import com.ivag.config.Constants;
import com.ivag.config.ConstantsErrorMessages;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@ComponentScan("com.ivag")
public class User {


    private Long id;
    private String name;
    private String password;
    private String email;
    private String pictureUrl;
    private String gender;
    private String phone;
    private Integer type;
    private Integer isActivated;
    private String token;

    public User() {
    }

    public User(String name, String password, String email) throws UserException {
        setName(name);
        setPassword(password);
        setEmail(email);
    }

    public User(Long id, String name, String email, String gender, String phone) throws UserException {
        setId(id);
        setName(name);
        setEmail(email);
        setGender(gender);
        setPhone(phone);
    }

    public User(Long id, String name, String email, String pictureUrl, String gender, String phone) throws UserException {
        setId(id);
        setName(name);
        setEmail(email);
        setPictureUrl(pictureUrl);
        setGender(gender);
        setPhone(phone);
    }

    public User(String name, String password, String email, String profileUrl) throws UserException {
        setName(name);
        setPassword(password);
        setEmail(email);
        setPictureUrl(profileUrl);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) throws UserException {
        if (id != null && id > -1)
            this.id = id;
        else throw new UserException(ConstantsErrorMessages.INVALID_ID);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws UserException {
        if (name != null && name.trim().length() > 0 && name.trim().length() <= Constants.MAX_USER_NAME_LENGTH) {
            this.name = name;
        } else throw new UserException(ConstantsErrorMessages.INVALID_NAME);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws UserException {
        if (password != null && password.trim().length() > Constants.MIN_PASSWORD_LENGTH && password.trim().length() <= Constants.MAX_USER_PASSWORD_LENGTH) {
            this.password = password;
        } else {
            throw new UserException(ConstantsErrorMessages.INVALID_PASSWORD);
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) throws UserException {
        if(token!=null && token.trim().length()>0)
        this.token = token;
        else throw new UserException(ConstantsErrorMessages.INVALID_TOKEN);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws UserException {
        if (email != null && email.trim().length() > Constants.MIN_EMAIL_LENGTH &&
                email.trim().length() <= Constants.MAX_EMAIL_LENGTH && validateEmail(email)) {
            this.email = email;
        } else throw new UserException(ConstantsErrorMessages.INVALID_EMAIL);

    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) throws UserException {
        if (type != null && (type == 0 || type == 1)) {
            this.type = type;
        } else {
            throw new UserException(ConstantsErrorMessages.INVALID_TYPE);
        }
    }

    public Integer getIsActivated() {
        return isActivated;
    }

    public void setIsActivated(Integer isActivated) throws UserException {
        if(isActivated!=null && (isActivated==0 || isActivated==1))
        this.isActivated = isActivated;
        else throw new UserException(ConstantsErrorMessages.INVALID_ACTIVATION_NUMBER);
    }

    public void setPictureUrl(String pictureUrl) throws UserException {
        if (pictureUrl != null && pictureUrl.trim().length() > 0 && pictureUrl.trim().length() <= Constants.MAX_USER_PROFILE_PICTURE_URL_LENGTH)
            this.pictureUrl = pictureUrl;
        else throw new UserException(ConstantsErrorMessages.INVALID_PICTURE_URL);
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) throws UserException {
        if (gender != null && gender.trim().length() > 0 && gender.trim().length() <= Constants.MAX_USER_GENDER_VALUE_LENGTH)
            this.gender = gender;
        else throw new UserException(ConstantsErrorMessages.INVALID_GENDER);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) throws UserException {
        if (phone != null && phone.trim().length() == Constants.PHONE_NUMBER_LENGTH && phone.startsWith(Constants.PHONE_NUMBER_PREFIX))
            this.phone = phone;
        else throw new UserException(ConstantsErrorMessages.INVALID_PHONE);
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public void copyValuesForSession(User user) throws UserException {
        this.setId(user.getId());
        this.setName(user.getName());
        this.setEmail(user.getEmail());
        this.setType(user.getType());
    }

    private boolean validateEmail(String emailStr) {
        Matcher matcher = Constants.VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
