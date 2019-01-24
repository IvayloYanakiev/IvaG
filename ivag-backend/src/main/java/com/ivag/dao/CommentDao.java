package com.ivag.dao;

import com.ivag.exception.CommentException;
import com.ivag.model.Comment;

import java.util.Collection;


public interface CommentDao {
    Long addProductComment(Comment comment) throws CommentException;
    Collection<Comment> getAllComments(Long productId) throws CommentException;
    Comment getCommentById(Long commentId) throws CommentException;
}
