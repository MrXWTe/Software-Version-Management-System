package cn.xuweiteng.springboot.dao;

import cn.xuweiteng.springboot.pojo.Administrator;
import cn.xuweiteng.springboot.pojo.Software;
import cn.xuweiteng.springboot.pojo.SoftwareVersions;
import cn.xuweiteng.springboot.pojo.User;

import java.util.List;

/**
 * 主要是管理员操作的DAO
 */
public interface AdminDao {

    /****************************管理员操作*******************************************/

    /**
     * 根据admin_email和admin_email查询管理员对象
     * @param admin_email 管理员emial
     * @param admin_email 管理员密码
     * @return 查询的Administrator对象
     */
    Administrator selectAdminByEmailAndPassword(String admin_email, String admin_password);


    /**
     * 编辑管理员
     * @param admin 编辑的管理员
     * @return 改变的行数
     */
    int updateAdmin(Administrator admin);




    /****************************软件操作*******************************************/

}
