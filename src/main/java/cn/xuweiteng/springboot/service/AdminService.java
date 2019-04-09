package cn.xuweiteng.springboot.service;

import cn.xuweiteng.springboot.pojo.Administrator;
import cn.xuweiteng.springboot.pojo.Software;
import cn.xuweiteng.springboot.pojo.User;

import java.util.List;

/**
 * 管理员操作的service
 */
public interface AdminService {


    /**
     * 查询所有软件
     * @return 软件集合
     */
    List<Software> selectAllSoftwares();


    /**
     * 查询软件数量
     * @return 软件总数
     */
    int selectCountOfSoftwares();


    /**
     * 根据当前页面查询软件列表
     * @param currentPage 当前页面
     * @return 当前页面需要显示的软件列表
     */
    List<Software> selectSoftwaresByCurrentPage(int currentPage);


    /**
     * 查询所有用户
     * @return 员工列表
     */
    List<User> selectAllUser();


    /**
     * 根据用户ID查询单个用户
     * @param userId 待查询的用户ID
     * @return 用户
     */
    User selectUserById(Long userId);


    /**
     * 添加用户
     * @param user 添加的用户
     * @return 改变的行数
     */
    int addUser(User user);




    /**
     * 根据ID删除用户
     * @param userId 删除用户ID
     * @return 改变的行号
     */
    int deleteUserById(Long userId);


    /**
     * 更新用户
     * @param user 更新的用户
     * @return 改变的行数
     */
    int updateUser(User user);


    /**
     * 更新管理员
     * @param admin 更新的管理员
     * @return 改变的行数
     */
    int updateAdmin(Administrator admin);


    /**
     * 根据admin_email和admin_email查询管理员对象
     * @param admin_email 管理员emial
     * @param admin_email 管理员密码
     * @return 查询的Administrator对象
     */
    Administrator selectAdminByEmailAndPassword(String admin_email, String admin_password);

}
