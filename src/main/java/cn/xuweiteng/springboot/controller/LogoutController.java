package cn.xuweiteng.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {
    
    /**
     * 退出登录操作
     * @param session 待清空的session
     * @return 登录页面
     */
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login.html";
    }
}
