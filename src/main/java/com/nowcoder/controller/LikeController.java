package com.nowcoder.controller;

import com.nowcoder.async.EventModel;
import com.nowcoder.async.EventProducer;
import com.nowcoder.async.EventType;
import com.nowcoder.dao.AnswerDAO;
import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.*;
import com.nowcoder.service.LikeService;
import com.nowcoder.service.NewsService;
import com.nowcoder.util.ToutiaoUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by hasse on 2020/4/1
 */
@Controller
public class LikeController {
    @Resource
    UserDAO userDAO;
    @Resource
    AnswerDAO answerDAO;
    @Autowired
    LikeService likeService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    NewsService newsService;

    @Autowired
    EventProducer eventProducer;
//问题踩赞
    @RequestMapping(path = {"/like"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String like(@Param("newId") int newsId) {
        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_NEWS, newsId);
        // 更新喜欢数
        News news = newsService.getById(newsId);
        newsService.updateLikeCount(newsId, (int) likeCount);

        eventProducer.fireEvent(new EventModel(EventType.LIKE)
                .setActorId(hostHolder.getUser().getId()).setEntityId(newsId)
                .setEntityType(EntityType.ENTITY_NEWS).setEntityOwnerId(news.getUserId()));

        return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String dislike(@Param("newId") int newsId) {
        long likeCount = likeService.disLike(hostHolder.getUser().getId(), EntityType.ENTITY_NEWS, newsId);
        // 更新喜欢数
        newsService.updateLikeCount(newsId, (int) likeCount);
        return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
    }
//回答踩赞
    @RequestMapping(path = {"/alike"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String alike(@Param("answerId") int answerId) {
        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_ANSWER, answerId);
        // 更新喜欢数
        Answer answer=answerDAO.getById(answerId);
        answerDAO.updateLikeCount(answerId, (int) likeCount);
        eventProducer.fireEvent(new EventModel(EventType.LIKE)
                .setActorId(hostHolder.getUser().getId()).setEntityId(answerId)
                .setEntityType(EntityType.ENTITY_ANSWER).setEntityOwnerId(answer.getUserId()));

        return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/adislike"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String adislike(@Param("answerId") int answerId) {


        long likeCount = likeService.disLike(hostHolder.getUser().getId(), EntityType.ENTITY_ANSWER, answerId);
        // 更新喜欢数
        answerDAO.updateLikeCount(answerId, (int) likeCount);
        return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
    }


//问题收藏
@RequestMapping(path = {"/star"}, method = {RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public String starLike(@Param("newsId") int newsId,@Param("starType") int starType) {
    User user;

    if (starType==0){
    if (hostHolder.getUser() != null) {
       user = hostHolder.getUser();
       if(user.getStar()!=null)
    user.setStar(user.getStar()+","+newsId);
       else { user.setStar(""+newsId);}
    userDAO.updateStar(user);

    }}
     if (starType==1) {
        if (hostHolder.getUser() != null) {
        user = hostHolder.getUser();
        if(user.getStar2()!=null)
        { user.setStar2(user.getStar2()+","+newsId);

        }
        else { user.setStar2(""+newsId);}


        userDAO.updateStar2(user);

    }}
    if (starType==2) {
        if (hostHolder.getUser() != null) {
            user = hostHolder.getUser();
            if(user.getStar3()!=null)
            { user.setStar3(user.getStar3()+","+newsId);

            }
            else { user.setStar3(""+newsId);}


            userDAO.updateStar3(user);

        }}
    return ToutiaoUtil.getJSONString(0, "ok");
}

    @RequestMapping(path = {"/disStar"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String starDislike(@RequestParam("newsId") int newsId) {


        long likeCount = likeService.disStar(hostHolder.getUser().getId(), EntityType.ENTITY_NEWS, newsId);
        // 更新喜欢数
       // answerDAO.updateLikeCount(newsId, (int) likeCount);
        return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
    }

    //回答收藏
    //回答踩赞
    /**
    @RequestMapping(path = {"/alike"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String alike(@Param("answerId") int answerId) {
        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_ANSWER, answerId);
        // 更新喜欢数
        Answer answer=answerDAO.getById(answerId);
        answerDAO.updateLikeCount(answerId, (int) likeCount);
        eventProducer.fireEvent(new EventModel(EventType.LIKE)
                .setActorId(hostHolder.getUser().getId()).setEntityId(answerId)
                .setEntityType(EntityType.ENTITY_ANSWER).setEntityOwnerId(answer.getUserId()));

        return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/adislike"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String adislike(@Param("answerId") int answerId) {


        long likeCount = likeService.disLike(hostHolder.getUser().getId(), EntityType.ENTITY_ANSWER, answerId);
        // 更新喜欢数
        answerDAO.updateLikeCount(answerId, (int) likeCount);
        return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
    }**/

}
