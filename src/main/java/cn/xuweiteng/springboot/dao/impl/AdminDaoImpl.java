package cn.xuweiteng.springboot.dao.impl;

import cn.xuweiteng.springboot.dao.AdminDao;
import cn.xuweiteng.springboot.pojo.Software;
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
     * @return
     */
    public int selectCountOfSoftwares(){
        return jdbcTemplate.queryForObject("select count(*) from tb_software", Integer.class);
    }


    public List<Software> selectSoftwaresByCurrentPage(int currentPage){
        int startNum = (currentPage-1) * 4 + 1;
        int endPage = 4;
        String sql = "select soft_name as softName, " +
                "soft_info as softInfo, soft_author as softAuthor, soft_last_modified_date as " +
                "softLastModifiedDate from tb_software limit " + startNum + ", " + endPage;

        List<Software> softwareList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Software.class));
        return softwareList;
    }

}
