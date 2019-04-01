package cn.xuweiteng.springboot.dao.impl;

import cn.xuweiteng.springboot.dao.LoginDao;
import cn.xuweiteng.springboot.pojo.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("loginDao")
public class LoginDaoImpl implements LoginDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 用于验证管理员登陆时是否存在该管理员
     * @param admin_email 管理员emial
     * @param admin_password 管理员密码
     * @return true:存在  false:不存在
     */
    @Override
    public boolean existAdmin(String admin_email, String admin_password) {
        List<Administrator> admin = jdbcTemplate.query(
                "select * from administrator where admin_email=? and admin_password=?",
                new Object[]{admin_email, admin_password},
                new BeanPropertyRowMapper<>(Administrator.class));
        if(admin == null || admin.size() ==0)
            return false;
        return true;
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
