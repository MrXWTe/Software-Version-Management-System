package cn.xuweiteng.springboot.service.impl;

import cn.xuweiteng.springboot.dao.UserDao;
import cn.xuweiteng.springboot.pojo.User;
import cn.xuweiteng.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("userService")
@Transactional(rollbackFor = Exception.class)
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
        List<User> list;
        try{
            list = userDao.selectAllUser();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return list;
    }


    /**
     * 根据用户ID查询单个用户
     * @param userId 待查询的用户ID
     * @return 用户
     */
    @Override
    public User selectUserById(Long userId) {
        User user;
        try{
            user = userDao.selectUserById(userId);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return user;
    }


    /**
     * 根据用户email和用户密码查询用户
     * @param userEmail 用户Email
     * @param userPassword 用户密码
     * @return 用户
     */
    @Override
    public User selectAdminByEmailAndPassword(String userEmail, String userPassword) {
        User user;
        try{
            user = userDao.selectAdminByEmailAndPassword(userEmail, userPassword);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return user;
    }


    /**
     * 根据ID删除用户
     * @param userId 删除用户ID
     * @return 改变的行号
     */
    @Override
    public int deleteUserById(Long userId) {
        int row;
        try{
            row = userDao.deleteUserById(userId);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return row;
    }


    /**
     * 添加用户
     * @param user 添加的用户
     * @return 改变的行数
     */
    @Override
    public int addUser(User user) {
        int row;
        try{
            row = userDao.addUser(user);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return row;
    }


    /**
     * 更新用户
     * @param user 更新的用户
     * @return 改变的行数
     */
    @Override
    public int updateUser(User user) {
        int row;
        try{
            row = userDao.updateUser(user);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return row;
    }


    /**
     * 更新用户密码
     * @param user 用户
     * @return 改变的行数
     */
    @Override
    public int updatePassword(User user) {
        int row;
        try{
            row = userDao.updatePassword(user);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return row;
    }
}
