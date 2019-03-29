package cn.xuweiteng.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Login {

    @RequestMapping(value = "/")
    public String login(){
        return "/Login";
    }
}
