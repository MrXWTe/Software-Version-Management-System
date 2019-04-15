package cn.xuweiteng.springboot.controller;

import cn.xuweiteng.springboot.pojo.Administrator;
import cn.xuweiteng.springboot.pojo.Software;
import cn.xuweiteng.springboot.pojo.SoftwareVersions;
import cn.xuweiteng.springboot.pojo.User;
import cn.xuweiteng.springboot.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {


    private final AdminService adminService;
    @Autowired
    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }


    /**
     * 跳转到更新管理员信息页面
     * @return 更新管理员信息页面
     */
    @PostMapping("/updateAdminPage")
    public String toUpdateAdminPage(){
        return "background-admin-update";
    }


    /**
     * 更新管理员信息操作
     * @param admin 管理员
     * @param id 管理员ID
     * @return 管理员信息页面
     */
    @PostMapping("/updateAdmin/{admin_id}")
    public String updateAdmin(@PathVariable("admin_id") Long id,
                              Administrator admin,
                              HttpSession session){
        //自动匹配的数据只有admin_name以及admin_email，因此需要添加admin_id，在update时有条件可循
        admin.setAdmin_id(id);

        //row>0表示更新成功
        int row = adminService.updateAdmin(admin);
        if(row > 0){
            //因为管理员信息已经更改，我们需要重新查询管理员信息，并更新session信息
//            Administrator adminModified = adminService.selectAdminByEmailAndPassword(
//                    admin.getAdmin_email(), admin.getAdmin_password());
            //session.setAttribute("admin", adminModified);
            return "redirect:/background-admin.html";
        }else{
            return "error";
        }
    }

}
