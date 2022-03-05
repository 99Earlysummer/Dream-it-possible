package com.dream.blog.service.impl;

import com.dream.blog.domain.entity.Comment;
import com.dream.blog.mapper.CommentMapper;
import com.dream.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public List<Comment> getCommentList(String uuid) {
        return commentMapper.getCommentList(uuid);
    }

    @Override
    public List<Comment> getChildComment(String uuid, Integer pid) {
        return commentMapper.getChildComment(uuid,pid);
    }


    @Override
    public int saveComment(Comment submitItem) {
        return commentMapper.saveComment(submitItem);
    }

    @Override
    public boolean updateComment(Comment submitItem) {
        return commentMapper.updateComment(submitItem);
    }

    @Override
    public boolean deleteComment(int CommentId) {
        return commentMapper.deleteComment(CommentId);
    }
}
