package cn.xuweiteng.springboot.controller;

import cn.xuweiteng.springboot.pojo.Administrator;
import cn.xuweiteng.springboot.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

/*
    //在MyMvcConfig配置了资源访问映射，因此不需要该方法了
    @RequestMapping(value = {"/", "/login.html"})
    public String login(){
        return "login";
    }
*/

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService service){
        loginService = service;
    }

    @PostMapping(value = "/loginTest")
    public String loginVerification(@RequestParam("admin_email") String admin_email,
                                    @RequestParam("admin_password") String admin_password,
                                    Map<String, Object> map,
                                    HttpSession session){


        Administrator admin = loginService.selectAdminByEmailAndPassword(admin_email, admin_password);
        boolean flag = (admin != null);
        if(flag) {
            session.setAttribute("admin", admin);
            return "redirect:/background-admin.html";
        }
        else {
            map.put("errorMessage", "用户名密码错误");
            return "login";
        }
    }


    @GetMapping("/logout")
    public String logOut(HttpSession session){
        session.invalidate();
        return "login.html";
    }
}
