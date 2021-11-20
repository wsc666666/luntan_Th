package com.nowcoder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by hasse on 2020/4/1
 */
@Controller
public class SettingController {
    @RequestMapping("/setting")
    @ResponseBody
    public String setting() {
        return "Setting:OK";
    }
}
