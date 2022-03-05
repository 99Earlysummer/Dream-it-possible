package com.dream.blog.controller;

import com.dream.blog.domain.entity.Comment;
import com.dream.blog.service.CommentService;
import com.dream.common.utils.DateUtils;
import com.dream.common.utils.R;
import com.dream.common.utils.ip.IpUtils;
import com.dream.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private ISysUserService userService;
    @RequestMapping("/getCommentlist")
    @ResponseBody
    public R getCommentlist(String uuid) {
        List<Comment> commentList = commentService.getCommentList(uuid);
        List<HashMap<String, Object>> maps = new ArrayList<HashMap<String, Object>>();
        for (Comment item : commentList) {
            HashMap<String, Object> res = new HashMap<String, Object>();
            res.put("id", item.getId().toString());
            res.put("uuid", item.getUuid());
            res.put("useruuid", item.getUseruuid());
            res.put("touseruuid", item.getTouseruuid());
            res.put("content", item.getContent());
            res.put("emojishow", false);
            String usernickname="";
            String useravatar="";
            String tousernickname="";
            String touseravatar="";
            /*if(item.getUserItem()!=null){
                usernickname=item.getUserItem().getNickname();
                useravatar=item.getUserItem().getAvatar();
            }
            if(item.getToUserItem()!=null){
                tousernickname=item.getToUserItem().getNickname();
                touseravatar=item.getToUserItem().getAvatar();
            }*/
            res.put("createtime", DateUtils.parseDate(item.getCreatetime()));
            res.put("usernickname", usernickname);
            res.put("tousernickname", tousernickname);
            res.put("useravatar", useravatar);
            res.put("touseravatar", touseravatar);
            List<HashMap<String, Object>> replyList=getChildComment(item.getUuid(),item.getId());
            res.put("replylist", replyList);
            maps.add(res);
        }
        return R.success().put("data", maps);
    }
    public List<HashMap<String, Object>> getChildComment(String uuid,Integer pid) {
        List<HashMap<String, Object>> maps = new ArrayList<HashMap<String, Object>>();
        if(pid==0) return maps;
        List<Comment> commentList = commentService.getChildComment(uuid,pid);
        for (Comment item : commentList) {
            HashMap<String, Object> res = new HashMap<String, Object>();
            res.put("id", item.getId().toString());
            res.put("uuid", item.getUuid());
            res.put("useruuid", item.getUseruuid());
            res.put("touseruuid", item.getTouseruuid());
            res.put("content", item.getContent());
            res.put("isshownew", false);
            res.put("emojishow", false);
            String usernickname="";
            String useravatar="";
            String tousernickname="";
            String touseravatar="";
           /* if(item.getUserItem()!=null){
                usernickname=item.getUserItem().getNickname();
                useravatar=item.getUserItem().getAvatar();
            }
            if(item.getToUserItem()!=null){
                tousernickname=item.getToUserItem().getNickname();
                touseravatar=item.getToUserItem().getAvatar();
            }*/
            res.put("createtime", DateUtils.parseDate(item.getCreatetime()));
            res.put("usernickname", usernickname);
            res.put("tousernickname", tousernickname);
            res.put("useravatar", useravatar);
            res.put("touseravatar", touseravatar);
            maps.add(res);
        }
        return maps;
    }
    @RequestMapping("/addComment")
    @ResponseBody
    public R addComment(HttpServletRequest request, Comment submitItem) {
       try{
           submitItem.setUserip(IpUtils.getIpAddr(request));
           submitItem.setCreatetime(DateUtils.getNowDate());
           commentService.saveComment(submitItem);
           int commentid = submitItem.getId();
           if (commentid > 0) {
               return R.success().put("msg", "操作成功");
           }
       }
       catch (Exception ex){
           return R.error().put("msg",ex.getMessage());
       }
        return R.error().put("msg","服务器错误");
    }
    @RequestMapping("/deleteComment")
    @ResponseBody
    public R deleteComment(Integer commentId) {
       try {
           commentService.deleteComment(commentId);
           return R.success().put("msg", "操作成功");
       }
       catch (Exception ex){
           return R.error().put("msg", ex.getMessage());
       }
    }
}
