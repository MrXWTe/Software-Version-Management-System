package cn.xuweiteng.springboot.service;

import cn.xuweiteng.springboot.pojo.User;

import java.util.List;


public interface UserService {
    /**
     * 查询所有员工
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
     * 根据用户email和用户密码查询用户
     * @param userEmail 用户Email
     * @param userPassword 用户密码
     * @return 用户
     */
    User selectAdminByEmailAndPassword(String userEmail, String userPassword);


    /**
     * 根据ID删除用户
     * @param userId 删除用户ID
     * @return 改变的行号
     */
    int deleteUserById(Long userId);


    /**
     * 添加用户
     * @param user 添加的用户
     * @return 改变的行数
     */
    int addUser(User user);



    /**
     * 更新用户
     * @param user 更新的用户
     * @return 改变的行数
     */
    int updateUser(User user);

}
