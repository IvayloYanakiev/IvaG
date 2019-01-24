package com.ivag.controller;

import com.ivag.config.Constants;
import com.ivag.config.ConstantsErrorMessages;
import com.ivag.config.IvagCloud;
import com.ivag.exception.UserException;
import com.ivag.model.User;
import com.ivag.service.LoggedUserService;
import com.ivag.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IvagCloud myCloud;

    @Autowired
    private UserService userService;

    @Autowired
    private LoggedUserService loggedUserService;

    @GetMapping(value = "/getUserPersonalData")
    public ResponseEntity getUserInfo(@RequestParam("id") Long id) {
        Gson gson = new Gson();
        String json = null;
        try {
            User user = userService.findUserById(id);
            json = gson.toJson(user);
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PutMapping("/updateUserPersonalData")
    public ResponseEntity updateUserPersonalData
            (@RequestParam("id") Long id, @RequestParam("name") String name, @RequestParam("email") String email,
             @RequestParam("gender") String gender, @RequestParam("phone") String phone) {
        Gson gson = new Gson();
        User user = null;
        try {
            user = new User(id, name, email, gender, phone);
            loggedUserService.updateUserPersonalInfo(user);
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(Constants.SUCCESS));
    }

    @RequestMapping(value = {"/updateUserProfilePicture"}, method = RequestMethod.POST, consumes = "multipart/form-data")
    public ResponseEntity updateUserProfilePicture(@RequestParam("id") Long id, @RequestParam("picture") MultipartFile picture) {
        Gson gson = new Gson();
        String json = null;

        try {
            String mimetype = picture.getOriginalFilename().split("\\.")[1];
            String type = mimetype.split("/")[0];
            if (!type.equals("jpg") && !type.equals("png") && !type.equals("jpeg"))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(ConstantsErrorMessages.INVALID_IMAGE_TYPE));
            File newFile = convertFromMultypartToFile(picture);
            Map uploadResult = myCloud.ivagCloud().uploader().upload(newFile, new HashMap<String, Object>());
            String url = (String) uploadResult.get("url");
            loggedUserService.updateUserProfilePicture(id, url);
            json = gson.toJson(url);
        } catch (IOException | UserException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    public static final File convertFromMultypartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Constants.FILE_PATH_NAME + file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

}
