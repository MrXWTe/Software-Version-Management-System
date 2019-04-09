package cn.xuweiteng.springboot.controller;

import cn.xuweiteng.springboot.pojo.Administrator;
import cn.xuweiteng.springboot.pojo.Software;
import cn.xuweiteng.springboot.pojo.User;
import cn.xuweiteng.springboot.service.AdminService;
import cn.xuweiteng.springboot.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 管理员页面控制
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    /**
     * 跳转到编辑管理员信息页面
     *
     * @return 编辑管理员信息页面
     */
    @GetMapping("/updateAdminPage")
    public String updateAdminPage(){
        return "background-admin-update";
    }


    /**
     * 编辑管理员信息操作
     * @param admin 编辑的管理员
     * @param id 编辑的管理员ID
     * @return 管理员信息页面
     */
    @PostMapping("/updateAdmin/{admin_id}")
    public String updateAdmin(Administrator admin,
                              @PathVariable("admin_id") Long id,
                              HttpSession session){
        admin.setAdmin_id(id);
        int row = adminService.updateAdmin(admin);
        if(row > 0){
            Administrator adminModified = adminService.selectAdminByEmailAndPassword(
                    admin.getAdmin_email(), admin.getAdmin_password());
            session.setAttribute("admin", adminModified);
            return "redirect:/background-admin.html";
        }else{
            return "error";
        }
    }


    /********************************用户操作************************************/

    /**
     * 跳转到用户列表页面
     *
     * @return 返回展示员工列表页面
     */
    @GetMapping("/background-admin-user")
    public String showUsers(Map<String, Object> map){
        List<User> userList = adminService.selectAllUser();
        map.put("userList", userList);
        return "/background-admin-user.html";
    }


    /**
     * 跳转到添加用户页面
     *
     * @return 返回添加用户页面
     */
    @GetMapping("/addUserPage")
    public String addUserPage(){
        return "background-admin-user-add";
    }


    /**
     * 添加用户操作
     *
     * @param user 自动映射的user
     * @return 返回用户列表页面
     */
    @PostMapping("/addUser")
    public String addUser(User user, Map<String, Object> map){
        int row = adminService.addUser(user);
        if(row > 0){
            List<User> userList = adminService.selectAllUser();
            map.put("userList", userList);
            return "/background-admin-user.html";
        }else{
            return "error";
        }
    }


    /**
     * 跳转到编辑用户页面
     * @return 编辑页面
     */
    @GetMapping("/updateUserPage/{userId}")
    public String updateUserPage(@PathVariable ("userId") Long userId, Model model){
        User user = adminService.selectUserById(userId);
        model.addAttribute("user", user);
        return "background-admin-user-update";
    }


    /**
     * 编辑用户操作
     * @param user
     * @param map
     * @return
     */
    @PostMapping("/updateUser/{userId}")
    public String updateUser(User user, @PathVariable("userId") Long userId, Map<String, Object> map){
        user.setUserId(userId);
        int row = adminService.updateUser(user);
        if(row > 0){
            List<User> userList = adminService.selectAllUser();
            map.put("userList", userList);
            return "/background-admin-user.html";
        }else{
            return "error";
        }
    }


    /**
     * 删除用户操作
     * @param userId
     * @param map
     * @return
     */
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId") String userId,
                             Map<String, Object> map){
        Long user_id = Long.parseLong(userId);
        int row = adminService.deleteUserById(user_id);
        if(row>0){
            List<User> userList = adminService.selectAllUser();
            map.put("userList", userList);
            return "/background-admin-user.html";
        }
        return "error";
    }



    /*******************************软件操作****************************************/

    /**
     * 跳转到展示软件列表页面
     *
     * @param map 用于存放返回数据
     * @return string
     */
    @GetMapping("/background-admin-software")
    public String showSoftwarePage(Map<String, Object> map,
                                   @RequestParam("currentPage") String currentPageString) {
        // 当前页面
        int currentPage = Integer.parseInt(currentPageString);

        // 获取软件列表
        List<Software> softwareList = adminService.selectSoftwaresByCurrentPage(currentPage);
        map.put("softwareList", softwareList);

        // 获取软件数量
        int totalNum = adminService.selectCountOfSoftwares();
        int pageNum = totalNum % 4 == 0 ? totalNum / 4 : totalNum / 4 + 1;


        map.put("totalNum", totalNum);
        map.put("pageNum", pageNum);
        map.put("currentPage", currentPage);
        return "/background-admin-software.html";
    }


}
