package com.ivag.service;

import com.ivag.exception.CommentException;
import com.ivag.dao.CommentDao;
import com.ivag.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public Comment addProductComment(Comment comment) throws CommentException {
        Long commentId = commentDao.addProductComment(comment);
        Comment returnedComment = commentDao.getCommentById(commentId);
        return returnedComment;
    }
}
