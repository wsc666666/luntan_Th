package com.nowcoder.controller;

import com.nowcoder.dao.AnswerDAO;
import com.nowcoder.dao.CommentDAO;
import com.nowcoder.dao.NewsDAO;
import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.*;
import com.nowcoder.service.LikeService;
import com.nowcoder.service.NewsService;
import com.nowcoder.service.UserService;
import com.nowcoder.util.MailSender;
import com.nowcoder.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nowcoder on 2016/7/2.
 */
@Controller
public class HomeController {
    @Resource
    private CommentDAO commentDAO;
    @Resource
    private UserDAO userDAO;
    @Resource
    private NewsDAO newsDAO;
    @Resource
    private AnswerDAO answerDAO;
    @Autowired
    NewsService newsService;

    @Autowired
    UserService userService;

    @Autowired
    LikeService likeService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    MailSender mailSender;
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private List<ViewObject> getNews(int userId, int offset, int limit) {
        List<News> newsList = newsService.getLatestNews(userId, offset, limit);

        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
        List<ViewObject> vos = new ArrayList<>();
        for (News news : newsList) {
            ViewObject vo = new ViewObject();
            int length=news.getLink().length();
            vo.set("length",length);
            news.setLink(userService.getUser(news.getUserId()).getZhName()+":"+news.getLink());
            vo.set("news", news);
            vo.set("user", userService.getUser(news.getUserId()));
            if (localUserId != 0) {
                vo.set("like", likeService.getLikeStatus(localUserId, EntityType.ENTITY_NEWS, news.getId()));
            } else {
                vo.set("like", 0);
            }
            News news2=(News) vo.get("news");

           System.out.println(news2.getTitle());
            vos.add(vo);
        }

        return vos;
    }

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model,
                        @RequestParam(value = "pop", defaultValue = "0") int pop) {
        User user=null;
        model.addAttribute("vos", getNews(0, 0, 10));

        if (hostHolder.getUser() != null) {
            user = hostHolder.getUser();
            pop=0;}
            model.addAttribute("user", user);

        model.addAttribute("pop",pop);
        return "home";
    }

    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId) {
        model.addAttribute("vos", getNews(userId, 0, 10));
        return "home";
    }
    @RequestMapping(path = {"/regist"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model) {
        return "login";
    }



    @RequestMapping(path = {"/question/answer/{newsId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String qsAnswer(Model model,@PathVariable("newsId") int newsId) {
        List<Answer> answers=answerDAO.selectByNewsIdAndOffset(newsId,0,1);
        List<ViewObject> vos = new ArrayList<>();
        for (Answer answer : answers) {
            ViewObject v = new ViewObject();
            User user=userDAO.selectById(answer.getUserId());
            v.set("owner",user);
            int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
            if (localUserId != 0) {
                v.set("like", likeService.getLikeStatus(localUserId, EntityType.ENTITY_ANSWER, answer.getId()));
            } else {
               v.set("like", 0);}
            List<Comment> comments = commentDAO.selectByEntity(answer.getId(), EntityType.ENTITY_ANSWER);
            List<ViewObject> commentVOs = new ArrayList<ViewObject>();
            for (Comment comment : comments) {
                ViewObject vo = new ViewObject();
                vo.set("comment", comment);
                vo.set("user", userService.getUser(comment.getUserId()));
                commentVOs.add(vo);
            }


            int answer_count=userDAO.countById(user.getId());

            answer.setCommentCount(String.valueOf(commentDAO.getCommentCount(answer.getId(),EntityType.ENTITY_ANSWER)));
            v.set("comments", commentVOs);
            v.set("answer",answer);
            v.set("answer_count",answer_count);
            vos.add(v);
        }
        logger.warn("vos::::::::::::",vos);
        News news=newsDAO.getById(newsId);
        String[] type=news.getType().split(",");
        int length=news.getLink().length();
        news.setAnCount(answerDAO.countById(news.getId()));
        model.addAttribute("vos",vos);
        model.addAttribute("news", news);
        model.addAttribute("type", type);
        model.addAttribute("length", length);

        return "question";
    }


    @RequestMapping(path = {"/user/regist"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String regsit(Model model, @RequestParam("email") String username,
                      @RequestParam("pwd") String password,
                         @RequestParam("username1") String username1,
                         @RequestParam("jc") String jc,
                      @RequestParam(value = "rember", defaultValue = "0") int rememberme,
                      HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.register(username1,username, password,jc);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (rememberme > 0) {
                    cookie.setMaxAge(3600 * 24 * 30);
                }
                response.addCookie(cookie);
                return ToutiaoUtil.getJSONString(0, "注册成功");
            } else {
                return ToutiaoUtil.getJSONString(1, map);
            }

        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "注册异常");
        }
    }

//    @RequestMapping(path = {"/head}"}, method = {RequestMethod.GET, RequestMethod.POST})
//    public String head(Model model) {
//        User user=new User("hi_boy");
//        user.setId(9527);
//        System.out.println("hi");
//        model.addAttribute("user", user);
//        return "header";
//    }


}
