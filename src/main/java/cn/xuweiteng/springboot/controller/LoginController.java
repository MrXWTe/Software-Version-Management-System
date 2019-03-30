package cn.xuweiteng.springboot.controller;

import cn.xuweiteng.springboot.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

    @RequestMapping(value = "/")
    public String login(){
        return "/login.html";
    }

    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/loginTest")
    public String loginVerification(@RequestParam("admin_email") String admin_email,
                                    @RequestParam("admin_password") String admin_password,
                                    Map<String, Object> map,
                                    HttpSession session){

        boolean flag = loginService.existAdmin(admin_email, admin_password);
        if(flag) {
            session.setAttribute("admin_email", admin_email);
            return "/success";
        }
        else {
            map.put("errorMessage", "用户名密码错误");
            return "login";
        }
    }
}
