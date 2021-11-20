package com.nowcoder.service;

import com.nowcoder.controller.LoginController;
import com.nowcoder.dao.NewsDAO;
import com.nowcoder.model.News;
import com.nowcoder.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import static com.nowcoder.util.ToutiaoUtil.TOUTIAO_DOMAIN;
/**
 * Created by hasse on 2020/4/1
 */
@Service
public class NewsService {
    @Resource
    private NewsDAO newsDAO;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public List<News> getLatestNews(int userId, int offset, int limit) {
        return newsDAO.selectByUserIdAndOffset(userId, offset, limit);
    }

    public int addNews(News news) {
        newsDAO.addNews(news);
        return news.getId();
    }

    public News getById(int newsId) {
        return newsDAO.getById(newsId);
    }

    public String saveImage(MultipartFile file, HttpServletRequest request) throws IOException {
        int dotPos = file.getOriginalFilename().lastIndexOf(".");
        if (dotPos < 0) {
            return null;
        }
        String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
        if (!ToutiaoUtil.isFileAllowed(fileExt)) {
            return null;
        }

        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win")){
        Files.copy(file.getInputStream(), new File(ToutiaoUtil.IMAGE_DIR + fileName).toPath(),
               StandardCopyOption.REPLACE_EXISTING);}
        else{
            Files.copy(file.getInputStream(), new File(ToutiaoUtil.IMAGE_DIR2 + fileName).toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        }
        return TOUTIAO_DOMAIN + "image?name=" + fileName;
    }

    public int updateCommentCount(int id, int count) {
        return newsDAO.updateCommentCount(id, count);
    }

    public int updateLikeCount(int id, int count) {
        return newsDAO.updateLikeCount(id, count);
    }
}
