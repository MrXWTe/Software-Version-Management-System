package cn.xuweiteng.springboot.dao.impl;

import cn.xuweiteng.springboot.dao.AdminDao;
import cn.xuweiteng.springboot.pojo.Administrator;
import cn.xuweiteng.springboot.pojo.Software;
import cn.xuweiteng.springboot.pojo.SoftwareVersions;
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

    /****************************管理员操作**************************************************/

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


    /****************************用户操作**************************************************/

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



    /****************************软件操作**************************************************/

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
        int startNum = (currentPage-1) * 4;
        int endPage = 4;
        String sql = "select soft_id as softId, soft_name as softName, " +
                "soft_info as softInfo, soft_author as softAuthor, soft_last_modified_date as " +
                "softLastModifiedDate from tb_software limit " + startNum + ", " + endPage;

        List<Software> softwareList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Software.class));
        return softwareList;
    }


    /**
     * 根据指定ID查询软件名
     * @param id 查询软件ID
     * @return 软件集合
     */
    public List<Software> selectSoftwareById(Long id){
        String sql = "select soft_name as softName from tb_software where soft_id=?";
        List<Software> softwareList = jdbcTemplate.query(sql, new Object[] {id},
                new BeanPropertyRowMapper<>(Software.class));
        return softwareList;

    }


    /**
     * 查找指定软件的所有  测试版本  号
     * @param softVersionId 指定的软件ID
     * @return 指定软件的所有版本的ID
     */
    @Override
    public List<SoftwareVersions> selectAllVersionBetaIdByFkId(Long softVersionId) {
        String sql = "select sv_id as svId, sv_versionId as svVersionId, sv_link as svLink from tb_version where soft_version_id=? and sv_version=?";
        List<SoftwareVersions> versionList = jdbcTemplate.query(sql, new Object[]{softVersionId, 0},//0 代表测试版
                new BeanPropertyRowMapper<>(SoftwareVersions.class));
        return versionList;
    }


    /**
     * 查找指定软件的所有  发机版本  号
     * @param softVersionId 指定的软件ID
     * @return 指定软件的所有版本的ID
     */
    @Override
    public List<SoftwareVersions> selectAllVersionReleaseIdByFkId(Long softVersionId) {
        String sql = "select sv_id as svId, sv_versionId as svVersionId, sv_link as svLink from tb_version " +
                "where soft_version_id=? and sv_version=?";

        List<SoftwareVersions> versionList = jdbcTemplate.query(sql, new Object[]{softVersionId, 1}, //1 代表发机版
                new BeanPropertyRowMapper<>(SoftwareVersions.class));
        return versionList;
    }



    @Override
    public List<SoftwareVersions> selectVersionBetaBySvId(Long svId) {
        String sql = "select sv_info as svInfo, sv_link as svLink, sv_versionId as svVersionId, " +
                "sv_version as svVersion, soft_name as softName from tb_version" +
                "inter join tb_software on soft_version_id=soft_id where sv_id=?";

        List<SoftwareVersions> versionList =  jdbcTemplate.query(sql, new Object[] {svId},
                new BeanPropertyRowMapper<>(SoftwareVersions.class));
        return versionList;
    }
}
