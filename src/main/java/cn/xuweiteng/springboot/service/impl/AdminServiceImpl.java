package cn.xuweiteng.springboot.service.impl;

import cn.xuweiteng.springboot.dao.AdminDao;
import cn.xuweiteng.springboot.pojo.Administrator;
import cn.xuweiteng.springboot.pojo.Software;
import cn.xuweiteng.springboot.pojo.SoftwareVersions;
import cn.xuweiteng.springboot.pojo.User;
import cn.xuweiteng.springboot.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    /*********************************管理员操作***************************************/

    /**
     * 根据admin_email和admin_email查询管理员对象
     * @param admin_email 管理员emial
     * @param admin_email 管理员密码
     * @return 查询的Administrator对象
     */
    @Override
    public Administrator selectAdminByEmailAndPassword(String admin_email, String admin_password) {
        return adminDao.selectAdminByEmailAndPassword(admin_email, admin_password);
    }

    /**
     * 更新管理员
     * @param admin 更新的管理员
     * @return 改变的行数
     */
    @Override
    public int updateAdmin(Administrator admin) {
        return adminDao.updateAdmin(admin);
    }


    /*********************************员工操作***************************************/

    /**
     * 查询所有员工
     * @return 员工列表
     */
    @Override
    public List<User> selectAllUser() {
        return adminDao.selectAllUser();
    }


    /**
     * 根据用户ID查询单个用户
     * @param userId 待查询的用户ID
     * @return 用户
     */
    @Override
    public User selectUserById(Long userId) {
        return adminDao.selectUserById(userId);
    }


    /**
     * 根据ID删除用户
     * @param userId 删除用户ID
     * @return 改变的行号
     */
    @Override
    public int deleteUserById(Long userId) {
        return adminDao.deleteUserById(userId);
    }


    /**
     * 添加用户
     * @param user 添加的用户
     * @return 改变的行数
     */
    @Override
    public int addUser(User user) {
        return adminDao.addUser(user);
    }


    /**
     * 更新用户
     * @param user 更新的用户
     * @return 改变的行数
     */
    @Override
    public int updateUser(User user) {
        return adminDao.updateUser(user);
    }


    /*********************************软件操作***************************************/

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
     * 根据指定ID查询软件名
     * @param id 查询软件ID
     * @return 软件集合
     */
    @Override
    public List<Software> selectSoftwareById(Long id) {
        return adminDao.selectSoftwareById(id);
    }


    /**
     * 查找指定软件的所有  测试版本  版本号
     * @param id 指定的软件ID
     * @return 指定软件的所有版本的ID
     */
    @Override
    public List<SoftwareVersions> selectAllVersionBetaIdByFkId(Long id) {
        return adminDao.selectAllVersionBetaIdByFkId(id);
    }


    /**
     * 查找指定软件的所有  发机版本  版本号
     * @param id 指定的软件ID
     * @return 指定软件的所有版本的ID
     */
    @Override
    public List<SoftwareVersions> selectAllVersionReleaseIdByFkId(Long id) {
        return adminDao.selectAllVersionReleaseIdByFkId(id);
    }


    /**
     * 查找指定svId的版本
     * @param svId 指定id
     * @return 版本列表
     */
    @Override
    public List<SoftwareVersions> selectVersionBetaBySvId(Long svId) {
        return adminDao.selectVersionBetaBySvId(svId);
    }


    /**
     * 更新软件版本 信息
     * @param softwareVersions 更新的信息
     * @return 改变的行数
     */
    @Override
    public int updateVersionBeta(SoftwareVersions softwareVersions) {
        return adminDao.updateVersionBeta(softwareVersions);
    }
}
