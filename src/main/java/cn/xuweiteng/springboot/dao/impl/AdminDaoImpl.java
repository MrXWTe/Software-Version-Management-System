package cn.xuweiteng.springboot.dao.impl;

import cn.xuweiteng.springboot.dao.AdminDao;
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
        List<User> userList = jdbcTemplate.query("select * from tb_user",
                new BeanPropertyRowMapper<>(User.class));
        return userList;
    }


    /**
     * 根据ID删除用户
     * @param userId 删除用户ID
     * @return 改变的行号
     */
    @Override
    public int deleteUserById(Long userId){
        return jdbcTemplate.update("delete from tb_user where user_id=?", userId);
    }
}
