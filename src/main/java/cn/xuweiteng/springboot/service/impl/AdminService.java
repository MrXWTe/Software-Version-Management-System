package cn.xuweiteng.springboot.service.impl;

import cn.xuweiteng.springboot.dao.AdminDao;
import cn.xuweiteng.springboot.pojo.Software;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    /**
     * 查询所有软件
     * @return 软件集合
     */
    public List<Software> selectAllSoftwares(){
        return adminDao.selectAllSoftwares();
    }
}
