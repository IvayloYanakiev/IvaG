package com.ivag.controller;

import com.ivag.service.CategoryService;
import com.ivag.exception.CategoryException;
import com.ivag.model.Category;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/category")

public class CategoryController {

    @Autowired
   private CategoryService categoryService;

   @GetMapping("/getAllCategories")
    public ResponseEntity getAllCategories(){

       Gson gson = new Gson();
       String json = null;
       try {
           Map<Long, Category> categories = categoryService.getAllCategories();
           json = gson.toJson(categories);
       } catch (CategoryException e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
       }
       return new ResponseEntity<>(json, HttpStatus.OK);
   }
}
