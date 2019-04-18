package cn.xuweiteng.springboot.controller;

import cn.xuweiteng.springboot.pojo.Administrator;
import cn.xuweiteng.springboot.pojo.Software;
import cn.xuweiteng.springboot.pojo.SoftwareVersions;
import cn.xuweiteng.springboot.pojo.User;
import cn.xuweiteng.springboot.service.AdminService;
import cn.xuweiteng.springboot.service.SoftwareService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Controller
public class SoftwareController {

    private final SoftwareService softwareService;
    @Autowired
    public SoftwareController(SoftwareService softwareService){
        this.softwareService = softwareService;
    }


    // 日志对象，用于输出日志文件
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 跳转到展示软件列表页面
     * @param map 用于存放返回数据
     * @return string
     */
    @GetMapping("/softwareListPage")
    public String toSoftwareListPage(Map<String, Object> map,
                                     @RequestParam("currentPage") String currentPageString,
                                     HttpSession session) {
        // 当前页面
        int currentPage = Integer.parseInt(currentPageString);

        // 获取软件列表
        List<Software> softwareList = softwareService.selectSoftwaresByCurrentPage(currentPage);
        map.put("softwareList", softwareList);

        // 获取软件数量
        int totalNum = softwareService.selectCountOfSoftwares();
        int pageNum = totalNum % 4 == 0 ? totalNum / 4 : totalNum / 4 + 1;


        map.put("totalNum", totalNum);
        map.put("pageNum", pageNum);
        map.put("currentPage", currentPage);
        if((Integer)session.getAttribute("role") == 0){
            return "background-software";
        }else if((Integer)session.getAttribute("role") == 1){
            return "background-software-user";
        }
        return "error";
    }


    /**
     * 跳转到 增加软件界面
     * @return 增加软件页面
     */
    @GetMapping("addSoftwarePage")
    public String toAddSoftwarePage(){
        return "background-software-add";
    }


    /**
     * 增加软件操作
     * @param software 软件
     * @return 软件列表页面
     */
    @PostMapping("addSoftware")
    public String addSoftware(Software software,
                              Map<String, Object> map,
                              HttpSession session){
        int row = softwareService.addSoftware(software);
        if(row > 0){

            // 获取软件列表
            List<Software> softwareList = softwareService.selectSoftwaresByCurrentPage(1);
            map.put("softwareList", softwareList);

            // 获取软件数量
            int totalNum = softwareService.selectCountOfSoftwares();
            int pageNum = totalNum % 4 == 0 ? totalNum / 4 : totalNum / 4 + 1;


            map.put("totalNum", totalNum);
            map.put("pageNum", pageNum);
            map.put("currentPage", 1);
            if((Integer)session.getAttribute("role") == 0){
                return "background-software";
            }else if((Integer)session.getAttribute("role") == 1){
                return "background-software-user";
            }
            return "error";
        }else{
            return "error";
        }
    }


    /**
     * 跳转到 编辑软件信息界面
     * @return 编辑软件信息页面
     */
    @GetMapping("updateSoftwarePage/{softId}")
    public String toUpdateSoftwarePage(@PathVariable("softId") Long softId,
                                       Model model){
        List<Software> softwareList = softwareService.selectSoftwareById(softId);
        if(softwareList != null && softwareList.size()>0){
            model.addAttribute("software", softwareList.get(0));
        }else{
            return "error";
        }
        return "background-software-update";
    }


    /**
     * 编辑软件信息操作
     * @param software 软件对象
     * @param map 用于存储信息
     * @param session 用于存储信息
     * @param softId 编辑的软件ID
     * @return 软件列表页面
     */
    @PostMapping("/updateSoftware/{softId}")
    public String updateSoftware(Software software, Map<String, Object> map,
                                 HttpSession session,
                                 @PathVariable("softId") Long softId){
        software.setSoftId(softId);
        int row = softwareService.updateSoftware(software);
        if(row > 0){
            // 获取软件列表
            List<Software> softwareList = softwareService.selectSoftwaresByCurrentPage(1);
            map.put("softwareList", softwareList);

            // 获取软件数量
            int totalNum = softwareService.selectCountOfSoftwares();
            int pageNum = totalNum % 4 == 0 ? totalNum / 4 : totalNum / 4 + 1;

            map.put("totalNum", totalNum);
            map.put("pageNum", pageNum);
            map.put("currentPage", 1);
            if((Integer)session.getAttribute("role") == 0){
                return "background-software";
            }else if((Integer)session.getAttribute("role") == 1){
                return "background-software-user";
            }
            return "error";
        }
        return "error";
    }


    /**
     * 跳转到  软件(包括测试版本和发机版本)  详细信息界面
     * @param svId 版本ID
     * @param model 用于存储转发信息
     * @return 软件详细信息界面
     */
    @GetMapping("versionDetailPage/{svId}")
    public String toVersionDetail(@PathVariable("svId") Long svId,
                                      HttpSession session,
                                      Model model){
        List<SoftwareVersions> versions = softwareService.selectVersionDetailBySvId(svId);
        if(versions!=null && versions.size()>0){
            model.addAttribute("version", versions.get(0));

            List<Software> softwareList = softwareService.
                    selectSoftwareById(versions.get(0).getSoftVersionId());//为了获取软件名字
            model.addAttribute("softName", softwareList.get(0).getSoftName());

        }
        if((Integer)session.getAttribute("role") == 0){
            return "background-software-version-details-update";
        }else if((Integer)session.getAttribute("role") == 1){
            return "background-software-version-details-user";
        }
        return null;
    }


    /**
     * 更新软件 版本(包括测试版本和发机版本)  详细信息
     * @param svId 软件  测试版本ID
     * @param softwareVersions 软件  测试版本  信息
     * @param model 用于存储信息
     * @return 更新成功后返回当前页面
     */
    @PostMapping("/updateSoftwareDetail/{svId}")
    public String updateSoftwareDetail(@PathVariable("svId") Long svId,
                                          SoftwareVersions softwareVersions,
                                          Model model){
        softwareVersions.setSvId(svId);
        int row = softwareService.updateSoftwareDetail(softwareVersions);
        if(row > 0){
            List<SoftwareVersions> versions = softwareService.selectVersionDetailBySvId(svId);
            if(versions!=null && versions.size()>0){
                model.addAttribute("version", versions.get(0));

                List<Software> softwareList = softwareService.
                        selectSoftwareById(versions.get(0).getSoftVersionId());//为了获取软件名字
                model.addAttribute("softName", softwareList.get(0).getSoftName());
            }
            return "background-software-version-details-update";
        }else{
            return "error";
        }
    }


    /**
     * 下载  软件(包括测试版本和发机版本)
     * @param response 响应信息
     * @return null(当前页面)
     * @throws Exception 文件不存在异常
     */
    @GetMapping("/downloadSoftware/{svId}")
    public String downloadSoftware(@PathVariable("svId") Long svId,
                                   HttpServletResponse response,
                                   HttpSession session) throws Exception{

        //获取登陆者姓名，用于写入日志文件中
        String name = "";
        Administrator admin = (Administrator) session.getAttribute("admin");
        User user = (User) session.getAttribute("user");
        if(admin!=null) name = admin.getAdmin_name();
        else if(user != null) name = user.getUserName();


        //获取下载链接
        String link = softwareService.selectVersionDetailBySvId(svId).get(0).getSvLink();
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
            }
            catch (Exception e) {

            }
        }


        logger.info(name + " --- download the " + fileName + '\n');
        return null;
    }

    /******************************测试版本***************************************/

    /**
     * 跳转到显示指定ID的所有  测试版本  软件页面
     * @param id 指定的软件ID
     * @param model 用于存储信息
     * @return 显示版本列表页面
     */
    @GetMapping("/softwareBetaVersionListPage/{softId}")
    public String toSoftwareBetaVersionListPage(@PathVariable("softId") Long id, Model model,
                                                HttpSession session){
        List<SoftwareVersions> versionBetaList = softwareService.selectAllVersionBetaIdByFkId(id);
        List<Software> softwareList = softwareService.selectSoftwareById(id);//为了获取软件名字

        model.addAttribute("softId", id);//软件ID，显示对应ID的所有版本
        model.addAttribute("softName", softwareList.get(0).getSoftName());
        model.addAttribute("versionBetaList", versionBetaList);
        if((Integer)session.getAttribute("role") == 0){
            return "background-software-versions-beta";
        }else if((Integer)session.getAttribute("role") == 1){
            return "background-software-versions-beta-user";
        }
        return null;
    }


    /**
     * 跳转到增加软件  测试版本  页面
     * @return 增加软件  测试版本  页面
     */
    @GetMapping("/addBetaVersionPage/{softId}")
    public String toAddBetaVersionPage(@PathVariable("softId") Long softId, Model model){
        model.addAttribute("softId", softId);
        return "background-software-version-details-add";
    }


    /**
     * 增加软件  测试版本，上传文件
     * @param fileUpload 上传的软件对象
     * @param model 用于存储信息
     * @param svVersionId   测试版本  ID
     * @param svInfo   测试版本  信息
     * @param softId   测试版本  软件ID
     * @return
     */
    @PostMapping("addBetaVersion/{softId}")
    public String addBetaVersion(MultipartFile fileUpload, Model model,
                                 @RequestParam("svVersionId") String svVersionId,
                                 @RequestParam("svInfo") String svInfo,
                                 @PathVariable("softId") Long softId,
                                 HttpSession session){

        //获取管理员姓名，便于写入日志
        Administrator admin = (Administrator) session.getAttribute("admin");
        String name = admin.getAdmin_name();


        //获取文件名
        String fileName = fileUpload.getOriginalFilename();

        //指定本地文件夹存储文件
        String filePath = "E:/software/beta/";
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

        int row = softwareService.addVersionBeta(softwareVersion);

        if(row > 0){
            List<SoftwareVersions> versionBetaList = softwareService.selectAllVersionBetaIdByFkId(softId);
            List<Software> softwareList = softwareService.selectSoftwareById(softId);//为了获取软件名字

            model.addAttribute("softId", softId);//软件ID，显示对应ID的所有版本
            model.addAttribute("softName", softwareList.get(0).getSoftName());
            model.addAttribute("versionBetaList", versionBetaList);

            logger.info(name + " --- upload the " + fileName + "（beta）" + '\n');

            return "background-software-versions-beta";
        }else{
            return "error";
        }
    }



    /******************************发机版本***************************************/


    /**
     * 显示指定ID的所有  发机版本  软件
     * @param id 指定的软件ID
     * @param model 用于存储信息
     * @return 显示版本列表页面
     */
    @GetMapping("/softwareReleaseVersionListPage/{softId}")
    public String toSoftwareReleaseVersionListPage(@PathVariable("softId") Long id,
                                                   HttpSession session,
                                                   Model model){
        List<SoftwareVersions> versionReleaseList = softwareService.selectAllVersionReleaseIdByFkId(id);
        List<Software> softwareList = softwareService.selectSoftwareById(id);//为了获取软件名字

        model.addAttribute("softId", id);//软件ID，显示对应ID的所有版本
        model.addAttribute("softName", softwareList.get(0).getSoftName());
        model.addAttribute("versionReleaseList", versionReleaseList);
        if((Integer)session.getAttribute("role") == 0){
            return "background-software-versions-release";
        }else if((Integer)session.getAttribute("role") == 1){
            return "background-software-versions-release-user";
        }
        return null;
    }


    /**
     * 跳转到增加软件  测试版本  页面
     * @return 增加软件  测试版本  页面
     */
    @GetMapping("/addReleaseVersionPage/{softId}")
    public String toAddReleaseVersionPage(@PathVariable("softId") Long softId, Model model){
        model.addAttribute("softId", softId);
        return "background-software-releaseVersion-details-add";
    }


    /**
     * 增加软件  发机版本，上传文件
     * @param fileUpload 上传的软件对象
     * @param model 用于存储信息
     * @param svVersionId   发机版本  ID
     * @param svInfo   发机版本  信息
     * @param softId   发机版本  软件ID
     * @return
     */
    @PostMapping("addReleaseVersion/{softId}")
    public String addReleaseVersion(MultipartFile fileUpload, Model model,
                                    @RequestParam("svVersionId") String svVersionId,
                                    @RequestParam("svInfo") String svInfo,
                                    @PathVariable("softId") Long softId,
                                    HttpSession session){

        //获取管理员姓名，便于写入日志
        Administrator admin = (Administrator) session.getAttribute("admin");
        String name = admin.getAdmin_name();


        //获取文件名
        String fileName = fileUpload.getOriginalFilename();

        //指定本地文件夹存储文件
        String filePath = "E:/software/release/";
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

        int row = softwareService.addReleaseVersion(softwareVersion);

        if(row > 0){
            List<SoftwareVersions> releaseVersionList = softwareService.selectAllVersionReleaseIdByFkId(softId);
            List<Software> softwareList = softwareService.selectSoftwareById(softId);//为了获取软件名字

            model.addAttribute("softId", softId);//软件ID，显示对应ID的所有版本
            model.addAttribute("softName", softwareList.get(0).getSoftName());
            model.addAttribute("versionReleaseList", releaseVersionList);

            logger.info(name + " --- upload the " + fileName + "（release）" + '\n');

            return "background-software-versions-release";
        }else{
            return "error";
        }
    }

}
