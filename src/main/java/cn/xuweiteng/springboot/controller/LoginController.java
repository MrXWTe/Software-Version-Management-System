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

    private final LoginService loginService;
    @Autowired
    public LoginController(LoginService loginService){
        this.loginService = loginService;
    }


    /**
     * 管理员登录操作
     * @param admin_email 管理员email
     * @param admin_password 管理员密码
     * @param map 登录错误后需要此map存储信息
     * @param session 登录成功需要向该session 存入信息
     * @return 后台页面
     */
    @PostMapping(value = "/loginTest")
    public String loginVerification(@RequestParam("admin_email") String admin_email,
                                    @RequestParam("admin_password") String admin_password,
                                    Map<String, Object> map,
                                    HttpSession session){
        Administrator admin = loginService.selectAdminByEmailAndPassword(
                admin_email, admin_password);
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


    /**
     * 退出登录操作
     * @param session 清空的session
     * @return 登录页面
     */
    @GetMapping("/logout")
    public String logOut(HttpSession session){
        session.invalidate();
        return "login.html";
    }
}
