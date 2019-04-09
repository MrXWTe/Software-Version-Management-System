package cn.xuweiteng.springboot.dao.impl;

import cn.xuweiteng.springboot.dao.AdminDao;
import cn.xuweiteng.springboot.pojo.Administrator;
import cn.xuweiteng.springboot.pojo.Software;
import cn.xuweiteng.springboot.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 管理员DAO的实现类
 */
@Repository("adminDao")
public class AdminDaoImpl implements AdminDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * 查询所有软件
     * @return 软件集合
     */
    @Override
    public List<Software> selectAllSoftwares() {
        List<Software> softwareList = jdbcTemplate.query("select soft_name as softName, " +
                "soft_info as softInfo, soft_author as softAuthor, soft_last_modified_date as " +
                "softLastModifiedDate from tb_software",
                new BeanPropertyRowMapper<>(Software.class));
        return softwareList;
    }


    /**
     * 查询软件数量
     * @return 软件总数
     */
    public int selectCountOfSoftwares(){
        return jdbcTemplate.queryForObject("select count(*) from tb_software", Integer.class);
    }


    /**
     * 根据当前页面查询软件列表
     * @param currentPage 当前页面
     * @return 当前页面需要显示的软件列表
     */
    public List<Software> selectSoftwaresByCurrentPage(int currentPage){
        int startNum = (currentPage-1) * 4 + 1;
        int endPage = 4;
        String sql = "select soft_name as softName, " +
                "soft_info as softInfo, soft_author as softAuthor, soft_last_modified_date as " +
                "softLastModifiedDate from tb_software limit " + startNum + ", " + endPage;

        List<Software> softwareList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Software.class));
        return softwareList;
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


    /**
     * 编辑管理员
     * @param admin 编辑的管理员
     * @return 改变的行数
     */
    @Override
    public int updateAdmin(Administrator admin) {
        String sql = "update administrator set admin_name=?, admin_email=? where admin_id=?";
        return jdbcTemplate.update(sql, new Object[] {admin.getAdmin_name(), admin.getAdmin_email(),
                admin.getAdmin_id()});
    }


    /**
     * 根据admin_email和admin_email查询管理员对象
     * @param admin_email 管理员emial
     * @param admin_email 管理员密码
     * @return 查询的Administrator对象
     */
    @Override
    public Administrator selectAdminByEmailAndPassword(String admin_email, String admin_password) {
        List<Administrator> admin = jdbcTemplate.query(
                "select * from administrator where admin_email=? and admin_password=?",
                new Object[]{admin_email, admin_password},
                new BeanPropertyRowMapper<>(Administrator.class));
        if(admin == null || admin.size() ==0)
            return null;
        return admin.get(0);
    }
}
