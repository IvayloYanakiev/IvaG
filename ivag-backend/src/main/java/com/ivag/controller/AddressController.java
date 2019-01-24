package com.ivag.controller;

import com.ivag.config.Constants;
import com.ivag.exception.AddressException;
import com.ivag.model.Address;
import com.ivag.service.AddressService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping("/address")
@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/addAddress")
    public ResponseEntity addAddress(
            @RequestParam("userId") Long id,
            @RequestParam("receiverName") String receiverName,
            @RequestParam("receiverPhone") String receiverPhone,
            @RequestParam("city") String city,
            @RequestParam("street") String street,
            @RequestParam("floor") Integer floor) {
        Gson gson = new Gson();
        Address address=null;
        try {
            address = new Address(receiverName, receiverPhone, city, street, floor);
            address =  addressService.addAddress(id, address);
        } catch (AddressException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(address));
    }

    @GetMapping("/getAllAddresses")
    public ResponseEntity getAllAddresses(@RequestParam("userId") Long id) {
        Gson gson = new Gson();
        Collection<Address> addresses = null;
        try {
            addresses = addressService.getAllAddresses(id);
        } catch (AddressException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }

        String json = gson.toJson(addresses);
        return ResponseEntity.ok(json);
    }

    @PutMapping("/updateAddress")
    public ResponseEntity updateAddress(@RequestParam("addressId") Long addressId,
                                        @RequestParam("receiverName") String receiverName,
                                        @RequestParam("receiverPhone") String receiverPhone,
                                        @RequestParam("city") String city,
                                        @RequestParam("street") String street,
                                        @RequestParam("floor") Integer floor) {
        Gson gson = new Gson();
        try {
            Address address = new Address(addressId, receiverName, receiverPhone, city, street, floor);
            addressService.updateAddress(address);
        } catch (AddressException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(Constants.SUCCESS));
    }

    @GetMapping("/getAddress")
    public ResponseEntity getAddress(@RequestParam("addressId") Long addressId) {
        Gson gson = new Gson();
        Address address = null;
        try {
            address = addressService.getAddress(addressId);
        } catch (AddressException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        String json = gson.toJson(address);

        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAddress")
    public ResponseEntity deleteAddress(@RequestParam("addressId") Long addressId) {

        Gson gson = new Gson();
        try {
            addressService.deleteAddress(addressId);
        } catch (AddressException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(Constants.SUCCESS));
    }

}
