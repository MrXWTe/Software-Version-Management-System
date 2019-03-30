package cn.xuweiteng.springboot.service;


public interface LoginService {

    /**
     * 用于验证管理员登陆时是否存在该管理员
     * @param admin_email 管理员emial
     * @param admin_password 管理员密码
     * @return true:存在  false:不存在
     */
    boolean existAdmin(String admin_email, String admin_password);
}
