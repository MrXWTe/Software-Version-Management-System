package cn.xuweiteng.springboot.dao.impl;

import cn.xuweiteng.springboot.dao.UserDao;
import cn.xuweiteng.springboot.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    /**
     * 查询所有员工
     * @return 员工列表
     */
    public List<User> selectAllUser(){
        String sql = "select * from tb_user";
        List<User> userList = jdbcTemplate.query(sql,  new BeanPropertyRowMapper<>(User.class));
        return userList;
    }


    /**
     * 根据用户ID查询单个用户
     * @param userId 待查询的用户ID
     * @return 用户
     */
    @Override
    public User selectUserById(Long userId) {
        String sql = "select user_id, user_name, user_email, user_enroll_date, user_status from tb_user where user_id = ?";
        List<User> userList = jdbcTemplate.query(sql, new Object[] {userId},
                new BeanPropertyRowMapper<>(User.class));

        if(userList != null && userList.size()>0)
            return userList.get(0);
        else{
            return null;
        }
    }


    /**
     * 根据用户email和用户密码查询用户
     * @param userEmail 用户Email
     * @param userPassword 用户密码
     * @return 用户
     */
    @Override
    public User selectAdminByEmailAndPassword(String userEmail, String userPassword) {
        String sql = "select * from tb_user where user_email=? and user_password=?";
        List<User> userList =  jdbcTemplate.query(sql, new Object[] {userEmail, userPassword},
                new BeanPropertyRowMapper<>(User.class));
        if(userList != null && userList.size()>0){
            return userList.get(0);
        }else{
            return null;
        }
    }


    /**
     * 根据ID删除用户
     * @param userId 删除用户ID
     * @return 改变的行号
     */
    @Override
    public int deleteUserById(Long userId){
        String sql = "delete from tb_user where user_id=?";
        return jdbcTemplate.update(sql, userId);
    }


    /**
     * 添加用户
     * @param user 添加的用户
     * @return 改变的行数
     */
    @Override
    public int addUser(User user) {
        String sql = "insert into tb_user (user_name, user_email, user_password,user_enroll_date, user_status) values (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, new Object[] {user.getUserName(), user.getUserEmail(),
                "123456",  user.getUserEnrollDate(), user.isUserStatus()});
    }


    /**
     * 更新用户
     * @param user 更新的用户
     * @return 改变的行数
     */
    @Override
    public int updateUser(User user) {
        String sql = "update tb_user set user_name=?, user_email=?, user_enroll_date=?, user_status=?" +
                " where user_id=?";
        return jdbcTemplate.update(sql, new Object[] {user.getUserName(), user.getUserEmail(),
                user.getUserEnrollDate(), user.isUserStatus(), user.getUserId()});
    }

}
