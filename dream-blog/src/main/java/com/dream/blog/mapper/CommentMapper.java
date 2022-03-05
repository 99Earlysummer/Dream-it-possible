package com.dream.blog.mapper;

import com.dream.blog.domain.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {
    List<Comment> getCommentList(@Param("uuid") String uuid);
    List<Comment> getChildComment(@Param("uuid") String uuid, @Param("pid") Integer pid);
    int saveComment(Comment submitItem);
    boolean updateComment(Comment submitItem);
    boolean deleteComment(@Param("commentId") int commentId);

}
