package com.ivag.service;

import com.ivag.exception.CommentException;
import com.ivag.model.Comment;


public interface CommentService {
     Comment addProductComment(Comment comment) throws CommentException;
}
