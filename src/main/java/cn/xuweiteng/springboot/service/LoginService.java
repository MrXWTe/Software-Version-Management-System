package cn.xuweiteng.springboot.service;


import cn.xuweiteng.springboot.pojo.Administrator;

public interface LoginService {

    /**
     * 用于验证管理员登陆时是否存在该管理员
     * @param admin_email 管理员emial
     * @param admin_password 管理员密码
     * @return true:存在  false:不存在
     */
    boolean existAdmin(String admin_email, String admin_password);


    /**
     * 根据admin_email和admin_email查询管理员对象
     * @param admin_email 管理员emial
     * @param admin_email 管理员密码
     * @return 查询的Administrator对象
     */
    Administrator selectAdminByEmailAndPassword(String admin_email, String admin_password);
}
