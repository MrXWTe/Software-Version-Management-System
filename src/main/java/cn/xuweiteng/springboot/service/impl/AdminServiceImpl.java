package cn.xuweiteng.springboot.service.impl;

import cn.xuweiteng.springboot.dao.AdminDao;
import cn.xuweiteng.springboot.pojo.Software;
import cn.xuweiteng.springboot.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    /**
     * 查询所有软件
     * @return 软件集合
     */
    public List<Software> selectAllSoftwares(){
        return adminDao.selectAllSoftwares();
    }

    /**
     * 查询软件数量
     * @return
     */
    public int selectCountOfSoftwares(){
        return adminDao.selectCountOfSoftwares();
    }


    public List<Software> selectSoftwaresByCurrentPage(int currentPage){
        return adminDao.selectSoftwaresByCurrentPage(currentPage);
    }
}
