package com.ivag.controller;

import com.ivag.exception.CommentException;
import com.ivag.model.Comment;
import com.ivag.service.CommentService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/addComment")
    public ResponseEntity orderProductsBy(@RequestParam("productId") Long productId, @RequestParam("userId") Long userId, String value, @RequestParam("stars") Integer stars) {
        Gson gson = new Gson();
        Comment comment = null;
        try {
            comment = new Comment(productId, userId, value, stars);
            comment = commentService.addProductComment(comment);
        } catch (CommentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        String json = gson.toJson(comment);
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }


}
