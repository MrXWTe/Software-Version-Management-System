package cn.xuweiteng.springboot.service.impl;

import cn.xuweiteng.springboot.dao.UserDao;
import cn.xuweiteng.springboot.pojo.User;
import cn.xuweiteng.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao){
        this.userDao = userDao;
    }

    /**
     * 查询所有员工
     * @return 员工列表
     */
    @Override
    public List<User> selectAllUser() {
        return userDao.selectAllUser();
    }


    /**
     * 根据用户ID查询单个用户
     * @param userId 待查询的用户ID
     * @return 用户
     */
    @Override
    public User selectUserById(Long userId) {
        return userDao.selectUserById(userId);
    }


    /**
     * 根据ID删除用户
     * @param userId 删除用户ID
     * @return 改变的行号
     */
    @Override
    public int deleteUserById(Long userId) {
        return userDao.deleteUserById(userId);
    }


    /**
     * 添加用户
     * @param user 添加的用户
     * @return 改变的行数
     */
    @Override
    public int addUser(User user) {
        return userDao.addUser(user);
    }


    /**
     * 更新用户
     * @param user 更新的用户
     * @return 改变的行数
     */
    @Override
    public int updateUser(User user) {
        return userDao.updateUser(user);
    }



}
