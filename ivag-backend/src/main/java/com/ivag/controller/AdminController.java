package com.ivag.controller;

import com.ivag.config.Constants;
import com.ivag.config.ConstantsErrorMessages;
import com.ivag.config.IvagCloud;
import com.ivag.exception.ProductException;
import com.ivag.model.Product;
import com.ivag.model.User;
import com.ivag.service.AdminService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/admin")
@RestController
public class AdminController {


    @Autowired
    private IvagCloud myCloud;

    @Autowired
    private AdminService adminService;

    @Autowired
    private User session;

    @RequestMapping(value = {"/addProduct"}, method = RequestMethod.POST, consumes = "multipart/form-data")
    public ResponseEntity addProduct(
            @RequestParam("name") String name,
            @RequestParam("categoryId") Long category,
            @RequestParam("price") Double price,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("description") String description,
            @RequestParam("picture") MultipartFile picture,
            @RequestParam("discount") Integer discount) {

        Gson gson = new Gson();
        if (session.getType() == 1) {

            String fileName = picture.getOriginalFilename();
            if (!fileName.endsWith("jpg") && !fileName.endsWith("png") && !fileName.endsWith("jpeg"))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(ConstantsErrorMessages.INVALID_FILE_TYPE));
            try {
                File newFile = UserController.convertFromMultypartToFile(picture);
                Map uploadResult = myCloud.ivagCloud().uploader().upload(newFile, new HashMap<String, Object>());
                String url = (String) uploadResult.get("url");
                Product product = new Product(name, category, price, quantity, description, url, discount);
                adminService.addProduct(product);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(Constants.ERROR));
            } catch (ProductException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
            }
            return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(Constants.SUCCESS));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(ConstantsErrorMessages.ACCESS_DENIED));
    }

    @DeleteMapping("/removeProductById")
    public ResponseEntity removeProductById(@RequestParam("id") Long id) {
        Gson gson = new Gson();
        if (session.getType() == 1) {
            try {
                adminService.deleteProductById(id);
            } catch (ProductException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
            }
            return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(Constants.SUCCESS));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(ConstantsErrorMessages.ACCESS_DENIED));
    }

    @PutMapping("/updateProduct")
    public ResponseEntity updateProduct(@RequestParam("id") Long id,
                                        @RequestParam("name") String name,
                                        @RequestParam("categoryId") Long category,
                                        @RequestParam("price") Double price,
                                        @RequestParam("quantity") Integer quantity,
                                        @RequestParam("description") String description,
                                        @RequestParam("discount") Integer discount) {
        Gson gson = new Gson();
        if (session.getType() == 1) {
            try {
                Product product = new Product(id, name, category, price, quantity, description, discount);
                adminService.updateProductById(product);
            } catch (ProductException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
            }
            return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(Constants.SUCCESS));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(ConstantsErrorMessages.ACCESS_DENIED));
    }
}
