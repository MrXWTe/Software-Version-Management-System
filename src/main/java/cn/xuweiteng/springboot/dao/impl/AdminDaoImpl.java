package cn.xuweiteng.springboot.dao.impl;

import cn.xuweiteng.springboot.dao.AdminDao;
import cn.xuweiteng.springboot.pojo.Administrator;
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
     * 编辑管理员
     * @param admin 编辑的管理员
     * @return 改变的行数
     */
    @Override
    public int updateAdmin(Administrator admin) {
        if(admin.getAdmin_password().equals("")){
            String sql = "update administrator set admin_name=?, admin_email=? where admin_id=?";
            return jdbcTemplate.update(sql,
                    new Object[] {admin.getAdmin_name(),
                            admin.getAdmin_email(),
                            admin.getAdmin_id()});
        }else{
            String sql = "update administrator set admin_name=?, admin_email=?, admin_password=? where admin_id=?";
            return jdbcTemplate.update(sql,
                    new Object[] {admin.getAdmin_name(),
                            admin.getAdmin_email(),
                            admin.getAdmin_password(),
                            admin.getAdmin_id()});
        }

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
