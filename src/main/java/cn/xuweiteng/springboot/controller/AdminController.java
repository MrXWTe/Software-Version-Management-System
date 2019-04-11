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
    public String updateUser(User user, @PathVariable("userId") Long userId,
                             Map<String, Object> map){
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


    /**
     * 显示指定ID的所有  测试版本  软件
     * @param id 指定的软件ID
     * @param model 用于存储信息
     * @return 显示版本列表页面
     */
    @GetMapping("/softwareVersionBetaList/{softId}")
    public String showSoftWareVersionBetaList(@PathVariable("softId") Long id, Model model){
        List<SoftwareVersions> versionBetaList = adminService.selectAllVersionBetaIdByFkId(id);
        List<Software> softwareList = adminService.selectSoftwareById(id);//为了获取软件名字

        model.addAttribute("softId", id);//软件ID，显示对应ID的所有版本
        model.addAttribute("softName", softwareList.get(0).getSoftName());
        model.addAttribute("versionBetaList", versionBetaList);
        return "background-admin-software-versions-beta";
    }


    /**
     * 显示指定ID的所有  发机版本  软件
     * @param id 指定的软件ID
     * @param model 用于存储信息
     * @return 显示版本列表页面
     */
    @GetMapping("/softwareVersionReleaseList/{softId}")
    public String showSoftWareVersionReleaseList(@PathVariable("softId") Long id, Model model){
        List<SoftwareVersions> versionReleaseList = adminService.selectAllVersionReleaseIdByFkId(id);
        List<Software> softwareList = adminService.selectSoftwareById(id);//为了获取软件名字

        model.addAttribute("softId", id);//软件ID，显示对应ID的所有版本
        model.addAttribute("softName", softwareList.get(0).getSoftName());
        model.addAttribute("versionReleaseList", versionReleaseList);
        return "background-admin-software-versions-release";
    }


    /**
     * 下载软件
     * @param response 响应信息
     * @return null(当前页面)
     * @throws Exception 文件不存在异常
     */
    @GetMapping("/downloadBetaVersionSoftware/{svId}")
    public String downLoadBetaVersion(@PathVariable("svId") Long svId,
                                      HttpServletResponse response) throws Exception{

        String link = adminService.selectVersionBetaBySvId(svId).get(0).getSvLink();
        File file = new File(link);
        String fileName = file.getName();
        if(file.exists()){
            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename="
                            + URLEncoder.encode(fileName, "UTF-8"));

            byte[] buffer = new byte[1024];
            try(BufferedInputStream bis =
                        new BufferedInputStream(new FileInputStream(file));
                OutputStream os = response.getOutputStream()){
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                System.out.println("Download the song successfully!");
            }
            catch (Exception e) {
                System.out.println("Download the song failed!");
            }
        }
        return null;
    }


    /**
     * 跳转到软件详细信息界面
     * @param svId 版本ID
     * @param model 用于存储转发信息
     * @return 软件详细信息界面
     */
    @GetMapping("showVersionDetail/{svId}")
    public String showVersionDetail(@PathVariable("svId") Long svId, Model model){
        List<SoftwareVersions> versions = adminService.selectVersionBetaBySvId(svId);
        if(versions!=null && versions.size()>0){
            String info = versions.get(0).getSvInfo();
            String[] infos = info.split(";");
            StringBuffer sb = new StringBuffer();
            for(String information : infos){
                sb.append(information);
                sb.append(";");
                sb.append("\r\n");
            }
            versions.get(0).setSvInfo(sb.toString());
            model.addAttribute("version", versions.get(0));

            List<Software> softwareList = adminService.
                    selectSoftwareById(versions.get(0).getSoftVersionId());//为了获取软件名字
            model.addAttribute("softName", softwareList.get(0).getSoftName());

        }
        return "background-admin-software-version-details-update";
    }


    /**
     * 更新软件版本详细信息
     * @param svId 软件版本ID
     * @param softwareVersions 软件版本信息
     * @param model 用于存储信息
     * @return 更新成功后返回当前页面
     */
    @PostMapping("/updateSoftwareDetails/{svId}")
    public String updateSoftwareDetails(@PathVariable("svId") Long svId,
                                        SoftwareVersions softwareVersions, Model model){
        softwareVersions.setSvId(svId);
        int row = adminService.updateVersionBeta(softwareVersions);
        if(row > 0){
            List<SoftwareVersions> versions = adminService.selectVersionBetaBySvId(svId);
            if(versions!=null && versions.size()>0){
                model.addAttribute("version", versions.get(0));

                List<Software> softwareList = adminService.
                        selectSoftwareById(versions.get(0).getSoftVersionId());//为了获取软件名字
                model.addAttribute("softName", softwareList.get(0).getSoftName());
            }
            return "background-admin-software-version-details-update";
        }else{
            return "error";
        }
    }


    /**
     * 跳转到增加软件版本页面
     * @return 增加软件版本页面
     */
    @GetMapping("/addVersionBetaPage/{softId}")
    public String addVersionPage(@PathVariable("softId") Long softId, Model model){
        model.addAttribute("softId", softId);
        return "background-admin-software-version-details-add";
    }


    @PostMapping("addVersionBeta/{softId}")
    public String addVersionBeta(MultipartFile fileUpload, Model model,
                             @RequestParam("svVersionId") String svVersionId,
                             @RequestParam("svInfo") String svInfo,
                             @PathVariable("softId") Long softId){
        //获取文件名
        String fileName = fileUpload.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));

        //指定本地文件夹存储文件
        String filePath = "E:/";
        try {
            //将文件保存到指定路径中
            fileUpload.transferTo(new File(filePath+fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        SoftwareVersions softwareVersion = new SoftwareVersions();
        softwareVersion.setSvVersionId(svVersionId);
        softwareVersion.setSvInfo(svInfo);
        softwareVersion.setSvLink(filePath + fileName);
        softwareVersion.setSoftVersionId(softId);

        int row = adminService.addVersionBeta(softwareVersion);

        if(row > 0){
            List<SoftwareVersions> versionBetaList = adminService.selectAllVersionBetaIdByFkId(softId);
            List<Software> softwareList = adminService.selectSoftwareById(softId);//为了获取软件名字

            model.addAttribute("softId", softId);//软件ID，显示对应ID的所有版本
            model.addAttribute("softName", softwareList.get(0).getSoftName());
            model.addAttribute("versionBetaList", versionBetaList);
            return "background-admin-software-versions-beta";
        }else{
            return "error";
        }
    }
}
