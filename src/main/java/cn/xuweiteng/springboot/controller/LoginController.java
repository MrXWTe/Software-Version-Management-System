package cn.xuweiteng.springboot.controller;

import cn.xuweiteng.springboot.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @RequestMapping(value = "/")
    public String login(){
        return "/Login";
    }

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/loginTest", method = RequestMethod.POST)
    public String loginVerification(@RequestParam("admin_email") String admin_email,
                                    @RequestParam("admin_password") String admin_password){

        boolean flag = loginService.existAdmin(admin_email, admin_password);
        if(flag)
            return "success";
        else
            return "error";
    }
}
