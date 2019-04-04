package cn.xuweiteng.springboot.service;

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
     * 查询所有员工
     * @return 员工列表
     */
    List<User> selectAllUser();


    /**
     * 根据ID删除用户
     * @param userId 删除用户ID
     * @return 改变的行号
     */
    int deleteUserById(Long userId);
}
