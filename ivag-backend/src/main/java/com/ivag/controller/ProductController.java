package com.ivag.controller;

import com.ivag.exception.ProductException;
import com.ivag.model.Product;
import com.ivag.service.ProductService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getAllProducts")
    public ResponseEntity getAllProducts() {
        Gson gson = new Gson();
        Collection<Product> products = null;
        try {
            products = productService.getAllProducts();
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(products));
    }

    @GetMapping("/getInnerCategoryProducts")
    public ResponseEntity getProductsByInnerCategoryId(@RequestParam("id") Long id) {
        Collection<Product> products = null;
        Gson gson = new Gson();
        try {
            products = productService.getProductsByInnerCategoryId(id);
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        String json = gson.toJson(products);
        return ResponseEntity.ok(json);
    }

    @GetMapping("/orderProductsByPrice")
    public ResponseEntity orderProductsByPrice(@RequestParam("orderIn") String orderIn) {
        Gson gson = new Gson();
        Collection<Product> products = null;
        try {
            products = productService.getAllProductsOrderedByPrice(orderIn);
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(products));
    }

    @GetMapping("/orderProductsByDiscount")
    public ResponseEntity orderProductsByDiscount(@RequestParam("orderIn") String orderIn) {
        Gson gson = new Gson();
        Collection<Product> products = null;
        try {
            products = productService.getAllProductsOrderedByDiscount(orderIn);
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(products));
    }

    @GetMapping("/orderProductsByName")
    public ResponseEntity orderProductsByName(@RequestParam("orderIn") String orderIn) {
        Gson gson = new Gson();
        Collection<Product> products = null;
        try {
            products = productService.getAllProductsOrderedByName(orderIn);
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(products));
    }

    @GetMapping("/getProductById")
    public ResponseEntity getProductById(@RequestParam("id") Long id) {
        Gson gson = new Gson();
        Product selectedProduct = null;
        try {
            selectedProduct = productService.getProductById(id);
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        String json = gson.toJson(selectedProduct);
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    @RequestMapping("/getProductsFilteredByName")
    public ResponseEntity getProductsFilteredByName(@RequestParam("searchInput") String searchInput) {
        Gson gson = new Gson();
        String json = null;

        try {
            Collection<Product> products = productService.getProductsFilteredByName(searchInput);
            json = gson.toJson(products);
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

}
