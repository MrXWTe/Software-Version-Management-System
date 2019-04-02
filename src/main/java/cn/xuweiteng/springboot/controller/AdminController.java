package cn.xuweiteng.springboot.controller;

import cn.xuweiteng.springboot.dao.AdminDao;
import cn.xuweiteng.springboot.pojo.Software;
import cn.xuweiteng.springboot.service.impl.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/background-admin-software")
    public String showSoftwarePage(Map<String, List<Software> > map){
        List<Software> softwareList = adminService.selectAllSoftwares();
        map.put("softwareList", softwareList);
        return "/background-admin-software.html";
    }
}
