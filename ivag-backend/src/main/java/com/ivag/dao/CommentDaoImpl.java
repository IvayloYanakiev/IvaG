package com.ivag.dao;

import com.ivag.exception.CommentException;
import com.ivag.config.ConstantsErrorMessages;
import com.ivag.config.ConstantsSQL;
import com.ivag.model.Comment;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;

@Repository

public class CommentDaoImpl implements CommentDao {


    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final Logger logger = Logger.getLogger(CommentDaoImpl.class);

    @Override
    public Long addProductComment(Comment comment) throws CommentException {
        String insertComment = ConstantsSQL.INSERT_INTO_COMMENTS;
        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.getJdbcOperations().update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(insertComment, Statement.RETURN_GENERATED_KEYS);
                    statement.setLong(1, comment.getUserId());
                    statement.setLong(2, comment.getProductId());
                    statement.setString(3, comment.getValue());
                    statement.setInt(4, comment.getStars());
                    statement.setString(5, comment.getCreatingDate());
                    return statement;
                }
            }, holder);

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CommentException(ConstantsErrorMessages.ADDING_COMMENT_FAILED, e);
        }
        long commentId = holder.getKey().longValue();
        return commentId;
    }

    public Comment getCommentById(Long commentId) throws CommentException {
        String getComment = ConstantsSQL.GET_COMMENT;
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", commentId);
        Comment currentComment = null;
        try{
        currentComment = jdbcTemplate.query(getComment, params, new ResultSetExtractor<Comment>() {

            @Override
            public Comment extractData(ResultSet rs) throws SQLException {

                Comment comment = null;
                if (rs.next()) {
                    Long id = rs.getLong("id");
                    Long userId = rs.getLong("user_id");
                    Long productId = rs.getLong("product_id");
                    String commentValue = rs.getString("value");
                    Integer stars = rs.getInt("stars");
                    String namesOfUser = rs.getString("name");
                    String profileUrl = rs.getString("profile_url");
                    String creationDate = rs.getString("creation_date");
                    try {
                        comment = new Comment(id, productId, userId, namesOfUser, commentValue, stars, profileUrl, creationDate);
                    } catch (CommentException e) {
                        logger.error(e.getMessage());
                        throw new SQLException(e.getMessage());
                    }
                }
                return comment;
            }
        });}catch (Exception e){
            throw new CommentException(e.getMessage(),e);
        }
        return currentComment;
    }

    @Override
    public Collection<Comment> getAllComments(Long productId) throws CommentException {
        String getAllComments = ConstantsSQL.GET_ALL_COMMENTS;
        HashMap<String, Object> params = new HashMap<>();
        params.put("product_id", productId);
        Collection<Comment> comments = null;
        try {
            comments = jdbcTemplate.query(getAllComments, params, new ResultSetExtractor<Collection<Comment>>() {

                @Override
                public Collection<Comment> extractData(ResultSet rs) throws SQLException {
                    Collection<Comment> myComments = new LinkedHashSet<>();

                    while (rs.next()) {
                        Long id = rs.getLong("id");
                        Long userId = rs.getLong("user_id");
                        Long productId = rs.getLong("product_id");
                        String commentValue = rs.getString("value");
                        Integer stars = rs.getInt("stars");
                        String namesOfUser = rs.getString("name");
                        String profileUrl = rs.getString("profile_url");
                        String creationDate = rs.getString("creation_date");
                        try {
                            Comment comment = new Comment(id, productId, userId, namesOfUser, commentValue, stars, profileUrl, creationDate);
                            myComments.add(comment);
                        } catch (CommentException e) {
                            logger.error(e.getMessage());
                            throw new SQLException(e.getMessage());
                        }
                    }
                    return myComments;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CommentException(ConstantsErrorMessages.PROBLEM_GETTING_COMMENTS, e);
        }
        return comments;
    }

}
