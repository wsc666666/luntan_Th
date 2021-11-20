package com.nowcoder.controller;

import com.nowcoder.dao.AnswerDAO;
import com.nowcoder.dao.MessageDAO;
import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.*;
import com.nowcoder.service.*;
import com.nowcoder.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.nowcoder.util.ToutiaoUtil.*;

/**
 * Created by hasse on 2020/4/1
 */
@Controller
public class NewsController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);
    @Resource
    AnswerDAO answerDAO;
    @Resource
    UserDAO userDAO;
    @Autowired
    NewsService newsService;
    @Resource
    MessageDAO messageDAO;

    @Autowired
    QiniuService qiniuService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    @RequestMapping(path = {"/news/{newsId}"}, method = {RequestMethod.GET})
    public String newsDetail(@PathVariable("newsId") int newsId, Model model) {
        News news = newsService.getById(newsId);
        if (news != null) {
            int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
            if (localUserId != 0) {
                model.addAttribute("like", likeService.getLikeStatus(localUserId, EntityType.ENTITY_NEWS, news.getId()));
            } else {
                model.addAttribute("like", 0);
            }
            // 评论
            List<Comment> comments = commentService.getCommentsByEntity(news.getId(), EntityType.ENTITY_NEWS);
            List<ViewObject> commentVOs = new ArrayList<ViewObject>();
            for (Comment comment : comments) {
                ViewObject vo = new ViewObject();
                vo.set("comment", comment);
                vo.set("user", userService.getUser(comment.getUserId()));
                commentVOs.add(vo);
            }
            model.addAttribute("comments", commentVOs);
        }
        model.addAttribute("news", news);
        model.addAttribute("owner", userService.getUser(news.getUserId()));
        if (hostHolder.getUser() != null) {
            User user = hostHolder.getUser();
            model.addAttribute("unReadNum",messageDAO.getConvesationUnreadCount1(user.getId()));}
        return "detail";
    }


    @RequestMapping(path = {"/addComment"}, method = {RequestMethod.POST})
    public String addComment(@RequestParam("newsId") int newsId,
                             @RequestParam("content") String content) {
        try {
            //content = HtmlUtils.htmlEscape(content);
            // 过滤content
            Comment comment = new Comment();
            comment.setUserId(hostHolder.getUser().getId());
            comment.setContent(content);
            comment.setEntityId(newsId);
            comment.setEntityType(EntityType.ENTITY_NEWS);
            comment.setCreatedDate(new Date());
            comment.setStatus(0);

            commentService.addComment(comment);
            // 更新news里的评论数量
            int count = commentService.getCommentCount(comment.getEntityId(), comment.getEntityType());
            newsService.updateCommentCount(comment.getEntityId(), count);
            // 怎么异步化
        } catch (Exception e) {
            logger.error("增加评论失败" + e.getMessage());
        }

        return "redirect:/news/" + String.valueOf(newsId);
    }

    @RequestMapping(path = {"/answer/addComment"}, method = {RequestMethod.POST})
    public String aaddComment(@RequestParam("answerId") int answerId,
                             @RequestParam("content") String content) {
        Comment comment = new Comment();
        try {
            //content = HtmlUtils.htmlEscape(content);
            // 过滤content

            comment.setUserId(hostHolder.getUser().getId());
            comment.setContent(content);
            comment.setEntityId(answerId);
            comment.setEntityType(EntityType.ENTITY_ANSWER);
            comment.setCreatedDate(new Date());
            comment.setStatus(0);

            commentService.addComment(comment);
            // 更新news里的评论数量
            int count = commentService.getCommentCount(comment.getEntityId(), comment.getEntityType());
            answerDAO.updateCommentCount(comment.getEntityId(), count);
            // 怎么异步化
        } catch (Exception e) {
            logger.error("增加评论失败" + e.getMessage());
        }
        return "redirect:/question/answer/" + String.valueOf(answerDAO.getById(comment.getEntityId()).getNewsId());
    }


    @RequestMapping(path = {"/image"}, method = {RequestMethod.GET})
    @ResponseBody
    public void getImage(@RequestParam("name") String imageName,
                         HttpServletResponse response,
                         HttpServletRequest request) {
        try {
            response.setContentType("image/jpeg");

            String os = System.getProperty("os.name");
            if(os.toLowerCase().startsWith("win")){
                StreamUtils.copy(new FileInputStream(new
                        File(IMAGE_DIR+ imageName)), response.getOutputStream());}
            else{ StreamUtils.copy(new FileInputStream(new
                    File(IMAGE_DIR2+ imageName)), response.getOutputStream());}
        } catch (Exception e) {
            logger.error("读取图片错误" + imageName + e.getMessage());
        }
    }

    @RequestMapping(path = {"/uploadImage/"}, method = {RequestMethod.POST})
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {
           String fileUrl = newsService.saveImage(file,request);
           // String fileUrl = qiniuService.saveImage(file);
            if (fileUrl == null) {
                return ToutiaoUtil.getJSONString(1, "上传图片失败");
            }
            return ToutiaoUtil.getJSONString(0, fileUrl);
        } catch (Exception e) {
            logger.error("上传图片失败" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "上传失败");
        }
    }
    @RequestMapping(path = {"/uploadImg/"}, method = {RequestMethod.POST})
    @ResponseBody
    public String uploadImg(@RequestParam("file") ArrayList<MultipartFile> file, HttpServletRequest request) {
        ArrayList<String> fileUrl =new ArrayList<>();
        try {
            for (MultipartFile f:file){
                fileUrl.add(newsService.saveImage(f,request));
            }

            // String fileUrl = qiniuService.saveImage(file);
            if (fileUrl == null) {
                return ToutiaoUtil.getJSONStringImg(1, fileUrl);
            }
            return ToutiaoUtil.getJSONStringImg(0, fileUrl);
        } catch (Exception e) {
            logger.error("上传图片失败" + e.getMessage());
            return ToutiaoUtil.getJSONStringImg(0, fileUrl);
        }
    }

    @RequestMapping(path = {"/user/addNews/"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addNews(@RequestParam("image") String image,
                          @RequestParam("title") String title,
                          @RequestParam("link") String link,
                          @RequestParam("type") String type) {
        try {
            //title = HtmlUtils.htmlEscape(title);
           // link = HtmlUtils.htmlEscape(link);
            News news = new News();
            logger.info("type:" + type);
            news.setCreatedDate(new Date());
            news.setTitle(title);
            news.setImage(image);
            news.setLink(link);
            news.setType(type);
            if (hostHolder.getUser() != null) {
                news.setUserId(hostHolder.getUser().getId());
            } else {
                // 设置一个匿名用户
                news.setUserId(3);
            }
            newsService.addNews(news);
            return ToutiaoUtil.getJSONString(0);
        } catch (Exception e) {
            logger.error("添加资讯失败" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "发布失败");
        }
    }
    @RequestMapping(path = {"/user/addAnswer/"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addAnswer(@RequestParam("content") String content,
                            @RequestParam("newsId") int newsId
                         ) {
        try {
            Answer answer = new Answer();
            answer.setCreateDate(new Date());
            answer.setEntity(content);
            answer.setNewsId(newsId);
            answer.setCommentCount("0");
            answer.setLikeCount("0");

            if (hostHolder.getUser() != null) {
                answer.setUserId(hostHolder.getUser().getId());
                hostHolder.getUser().setLv( hostHolder.getUser().getLv()+1);
            userDAO.updateLv(hostHolder.getUser());


            } else {
                // 设置一个匿名用户
                answer.setUserId(3);
            }

            answerDAO.addAnswer(answer);
            return ToutiaoUtil.getJSONString(0);
        } catch (Exception e) {
            logger.error("添加回答失败" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "回答失败");
        }
    }
}
