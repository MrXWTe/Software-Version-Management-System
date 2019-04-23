package cn.xuweiteng.springboot.controller;

import cn.xuweiteng.springboot.pojo.Administrator;
import cn.xuweiteng.springboot.pojo.User;
import cn.xuweiteng.springboot.service.AdminService;
import cn.xuweiteng.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final AdminService adminService;
    private final UserService userService;

    @Autowired
    public LoginController(AdminService adminService, UserService userService){
        this.adminService = adminService;
        this.userService = userService;
    }


    /**
     * 管理员登录操作
     * @param admin_email 管理员email
     * @param admin_password 管理员密码
     * @param map 登录错误后需要此map存储提示信息
     * @param session 登录成功需要向该session 存入成功登录信息
     * @return 后台页面
     */
    @PostMapping(value = "/adminLogin")
    public String adminLogin(@RequestParam("admin_email") String admin_email,
                             @RequestParam("admin_password") String admin_password,
                             Map<String, Object> map,
                             HttpSession session)
    {

        // 判断用户是否已经登录
        Administrator adminLogged = (Administrator) session.getAttribute("admin");
        if (adminLogged != null) {
            // 用户已经登录
            map.put("adminErrorMessage", "用户已经登录，检查用户名是否正确");
            return "login.html";
        }

        // 判断用户名密码是否正确
        Administrator admin = adminService.selectAdminByEmailAndPassword(admin_email, admin_password);
        boolean flag = (admin != null);
        if(flag) {
            // 正确
            session.setAttribute("admin", admin);
            session.setAttribute("role", 0);
            return "redirect:/background-admin.html";
        }
        else {
            // 不正确
            map.put("adminErrorMessage", "用户名密码错误");
            return "login.html";
        }
    }


    /**
     * 员工登录操作
     * @param user_email 用户 email
     * @param user_password 用户密码
     * @param map 用于存储信息
     * @param session 用于存储信息
     * @return 管理员信息页
     */
    @PostMapping(value = "/employeeLogin")
    public String employeeLogin(@RequestParam("employeeEmail") String user_email,
                                @RequestParam("employeePassword") String user_password,
                                Map<String, Object> map,
                                HttpSession session)
    {
        // 判断用户是否登录
        User userLogged = (User)session.getAttribute("user");
        if(userLogged != null){
            // 已经登录
            map.put("employeeErrorMessage", "用户已经登录，检查用户名是否正确");
            return "login.html";
        }

        // 判断用户名密码是否正确
        User user = userService.selectAdminByEmailAndPassword(user_email, user_password);
        boolean flag = (user != null);
        // 正确
        if(flag){
            session.setAttribute("user", user);
            session.setAttribute("role", 1);
            return "redirect:/background-user-info.html";
        }
        // 不正确
        else {
            map.put("employeeErrorMessage", "用户名密码错误");
            return "login.html";
        }
    }
}
