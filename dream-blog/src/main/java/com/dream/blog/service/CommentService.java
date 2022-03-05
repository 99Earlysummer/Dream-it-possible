package com.dream.blog.service;

import com.dream.blog.domain.entity.Comment;

import java.util.List;


public interface CommentService {
    List<Comment> getCommentList(String uuid);
    List<Comment> getChildComment(String uuid, Integer pid);
    int saveComment(Comment submitItem);
    boolean updateComment(Comment submitItem);
    boolean deleteComment(int CommentId);

}
