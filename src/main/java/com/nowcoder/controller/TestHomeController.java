package com.nowcoder.controller;

import com.nowcoder.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * Created by hasse on 2020/4/1
 */
@Controller
public class TestHomeController {
    @RequestMapping("/footer")
    public String hello() {

        return "footer";
    }

    @RequestMapping(path = {"/header"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String head(Model model) {
        User user=null ;//
        // = new User("hi_boy");
        //user.setId(9527);

        model.addAttribute("user", user);
        return "header";
    }
}
