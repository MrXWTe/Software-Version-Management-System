package cn.xuweiteng.springboot.service.impl;

import cn.xuweiteng.springboot.dao.AdminDao;
import cn.xuweiteng.springboot.pojo.Administrator;
import cn.xuweiteng.springboot.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("adminService")
@Transactional(rollbackFor = Exception.class)
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;


    /**
     * 根据admin_email和admin_password查询管理员对象
     *
     * @param admin_email    管理员 email
     * @param admin_password 管理员密码
     * @return Administrator对象
     */
    @Override
    public Administrator selectAdminByEmailAndPassword(String admin_email, String admin_password) {
        Administrator admin;
        try{
            admin = adminDao.selectAdminByEmailAndPassword(admin_email, admin_password);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return admin;
    }


    /**
     * 更新管理员个人信息
     *
     * @param admin 管理员
     * @return 改变的行数
     */
    @Override
    public int updateAdmin(Administrator admin) {
        int row;
        try{
            row = adminDao.updateAdmin(admin);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return row;
    }
}
