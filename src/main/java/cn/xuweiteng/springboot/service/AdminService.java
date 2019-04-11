package cn.xuweiteng.springboot.service;

import cn.xuweiteng.springboot.pojo.Administrator;

/**
 * 管理员操作的service
 */
public interface AdminService {


    /****************************管理员操作*******************************************/

    /**
     * 根据admin_email和admin_password查询管理员对象
     * @param admin_email 管理员 email
     * @param admin_password 管理员密码
     * @return Administrator对象
     */
    Administrator selectAdminByEmailAndPassword(String admin_email, String admin_password);


    /**
     * 更新管理员个人信息
     * @param admin 管理员
     * @return 改变的行数
     */
    int updateAdmin(Administrator admin);


}
