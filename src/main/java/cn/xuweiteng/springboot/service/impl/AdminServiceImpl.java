package cn.xuweiteng.springboot.service.impl;

import cn.xuweiteng.springboot.dao.AdminDao;
import cn.xuweiteng.springboot.pojo.Software;
import cn.xuweiteng.springboot.pojo.User;
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
    @Override
    public List<Software> selectAllSoftwares(){
        return adminDao.selectAllSoftwares();
    }


    /**
     * 查询软件数量
     * @return 软件总数
     */
    @Override
    public int selectCountOfSoftwares(){
        return adminDao.selectCountOfSoftwares();
    }


    /**
     * 根据当前页面查询软件列表
     * @param currentPage 当前页面
     * @return 当前页面需要显示的软件列表
     */
    @Override
    public List<Software> selectSoftwaresByCurrentPage(int currentPage){
        return adminDao.selectSoftwaresByCurrentPage(currentPage);
    }


    /**
     * 查询所有员工
     * @return 员工列表
     */
    @Override
    public List<User> selectAllUser() {
        return adminDao.selectAllUser();
    }
}
