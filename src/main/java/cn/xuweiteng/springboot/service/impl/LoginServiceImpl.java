package cn.xuweiteng.springboot.service.impl;

import cn.xuweiteng.springboot.dao.LoginDao;
import cn.xuweiteng.springboot.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginDao loginDao;

    /**
     * 用于验证管理员登陆时是否存在该管理员
     * @param admin_email 管理员emial
     * @param admin_password 管理员密码
     * @return true:存在  false:不存在
     */
    @Override
    public boolean existAdmin(String admin_email, String admin_password) {
        return loginDao.existAdmin(admin_email, admin_password);
    }
}
