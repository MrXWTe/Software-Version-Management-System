package cn.xuweiteng.springboot.service.impl;

import cn.xuweiteng.springboot.dao.LoginDao;
import cn.xuweiteng.springboot.pojo.Administrator;
import cn.xuweiteng.springboot.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("loginService")
@Transactional(rollbackFor = Exception.class)
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginDao loginDao;

    /**
     * 用于验证管理员登陆时是否存在该管理员
     *
     * @param admin_email    管理员emial
     * @param admin_password 管理员密码
     * @return true:存在  false:不存在
     */
    @Override
    public boolean existAdmin(String admin_email, String admin_password) {
        boolean flag;
        try{
            flag = loginDao.existAdmin(admin_email, admin_password);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return flag;
    }


    /**
     * 根据admin_email和admin_email查询管理员对象
     *
     * @param admin_email 管理员emial
     * @param admin_email 管理员密码
     * @return 查询的Administrator对象
     */
    @Override
    public Administrator selectAdminByEmailAndPassword(String admin_email, String admin_password) {
        Administrator admin;
        try{
            admin = loginDao.selectAdminByEmailAndPassword(admin_email, admin_password);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return admin;
    }
}
