package cn.xuweiteng.springboot.service.impl;

import cn.xuweiteng.springboot.dao.AdminDao;
import cn.xuweiteng.springboot.pojo.Administrator;
import cn.xuweiteng.springboot.pojo.Software;
import cn.xuweiteng.springboot.pojo.SoftwareVersions;
import cn.xuweiteng.springboot.pojo.User;
import cn.xuweiteng.springboot.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    /*********************************管理员操作***************************************/

    /**
     * 根据admin_email和admin_password查询管理员对象
     * @param admin_email 管理员 email
     * @param admin_password 管理员密码
     * @return Administrator对象
     */
    @Override
    public Administrator selectAdminByEmailAndPassword(String admin_email, String admin_password) {
        return adminDao.selectAdminByEmailAndPassword(admin_email, admin_password);
    }

    /**
     * 更新管理员个人信息
     * @param admin 管理员
     * @return 改变的行数
     */
    @Override
    public int updateAdmin(Administrator admin) {
        return adminDao.updateAdmin(admin);
    }


    /*********************************软件操作***************************************/

    }
