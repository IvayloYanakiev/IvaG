package com.ivag.controller;

import com.ivag.config.Constants;
import com.ivag.service.SendEmailService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sendEmail")
public class SendEmailController {

    @Autowired
    private SendEmailService sendingEmailService;

    @PutMapping("/subscribeUser")
    public ResponseEntity subscribe(@RequestParam("email") String email) {
        Gson gson = new Gson();
        try {
            sendingEmailService.sendEmail(email);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(Constants.SUCCESS));
    }

    @PutMapping("/informUserForOrder")
    public ResponseEntity informUserForOrder(@RequestParam("email") String email, @RequestParam("addressId") Long addressId,
                                             @RequestParam("payingMethod") String payingMethod, @RequestParam("totalSum") Double totalSum,
                                             @RequestParam("shoppingCart") String shoppingCart) {
        Gson gson = new Gson();
        try {
            sendingEmailService.informUserForOrder(email, addressId, payingMethod,totalSum,shoppingCart);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(Constants.SUCCESS));
    }
}
