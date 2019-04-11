package cn.xuweiteng.springboot.controller;

import cn.xuweiteng.springboot.pojo.User;
import cn.xuweiteng.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {


    private final UserService userService;

    @Autowired
    public EmployeeController(UserService userService){
        this.userService = userService;
    }


    /**
     * 跳转到用户列表页面
     * @return 员工列表页面
     */
    @GetMapping("/userPage")
    public String toUserPage(Map<String, Object> map){
        List<User> userList = userService.selectAllUser();
        map.put("userList", userList);
        return "background-user";
    }


    /**
     * 跳转到添加用户页面
     * @return 添加用户页面
     */
    @GetMapping("/addUserPage")
    public String toAddUserPage(){
        return "background-user-add";
    }


    /**
     * 添加用户操作
     * @param user 自动映射的user
     * @return 返回用户列表页面
     */
    @PostMapping("/addUser")
    public String addUser(User user, Map<String, Object> map){
        int row = userService.addUser(user);
        if(row > 0){
            List<User> userList = userService.selectAllUser();
            map.put("userList", userList);
            return "background-user";
        }else{
            return "error";
        }
    }


    /**
     * 跳转到更新用户信息页面
     * @return 编辑页面
     */
    @GetMapping("/updateUserPage/{userId}")
    public String updateUserPage(@PathVariable("userId") Long userId, Model model){
        User user = userService.selectUserById(userId);
        model.addAttribute("user", user);
        return "background-user-update";
    }


    /**
     * 更新用户操作
     * @param user 员工对象
     * @param map 用于存储信息
     * @return
     */
    @PostMapping("/updateUser/{userId}")
    public String updateUser(User user, @PathVariable("userId") Long userId,
                             Map<String, Object> map){
        user.setUserId(userId);
        int row = userService.updateUser(user);
        if(row > 0){
            List<User> userList = userService.selectAllUser();
            map.put("userList", userList);
            return "background-user";
        }else{
            return "error";
        }
    }


    /**
     * 删除用户操作
     * @param userId 员工ID
     * @param map 用于存储信息
     * @return
     */
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId") String userId,
                             Map<String, Object> map){
        Long user_id = Long.parseLong(userId);
        int row = userService.deleteUserById(user_id);
        if(row>0){
            List<User> userList = userService.selectAllUser();
            map.put("userList", userList);
            return "background-user";
        }
        return "error";
    }

}
