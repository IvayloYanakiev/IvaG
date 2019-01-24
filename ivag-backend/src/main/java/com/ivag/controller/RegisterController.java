package com.ivag.controller;

import com.ivag.config.Constants;
import com.ivag.config.ConstantsErrorMessages;
import com.ivag.config.IvagCloud;
import com.ivag.exception.UserException;
import com.ivag.model.User;
import com.ivag.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/register")
@RestController
public class RegisterController {

    @Autowired
   private UserService userService;
    @Autowired
    private IvagCloud myCloud;

    @PostMapping("/createUser")
    public ResponseEntity register(@RequestParam("name") String name,
                                   @RequestParam("email") String email,
                                   @RequestParam("password") String password,
                                   @RequestParam("confirmPassword") String confirmPassword) {
        Gson gson = new Gson();
        try {
            User user = new User(name, password, email, Constants.NO_PICTURE);
            if (confirmPassword != null && confirmPassword.trim().length() > 0 && confirmPassword.trim().length() <= Constants.MAX_USER_PASSWORD_LENGTH) {
                if (user.getPassword().equals(confirmPassword)) {
                    userService.registerUser(user);
                } else
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(ConstantsErrorMessages.PASSWORDS_NOT_THE_SAME));
            } else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(ConstantsErrorMessages.CHECK_YOUR_PASSWORD));

        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(Constants.CHECK_YOUR_EMAIL_FOR_ACTIVATION_LINK));
    }

    @PostMapping("/activateAccount")
    public ResponseEntity register(@RequestParam("token") String token) {
        Gson gson = new Gson();
        try {
            userService.activateAccount(token);
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(Constants.ACTIVATION_SUCCESS));
    }


}

