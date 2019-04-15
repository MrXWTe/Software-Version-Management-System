package cn.xuweiteng.springboot.service.impl;

import cn.xuweiteng.springboot.dao.SoftwareDao;
import cn.xuweiteng.springboot.pojo.Software;
import cn.xuweiteng.springboot.pojo.SoftwareVersions;
import cn.xuweiteng.springboot.service.SoftwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("softwareService")
public class SoftwareServiceImpl implements SoftwareService {

    private final SoftwareDao softwareDao;

    @Autowired
    public SoftwareServiceImpl(SoftwareDao softwareDao){
        this.softwareDao = softwareDao;
    }


    /**
     * 查询所有软件
     * @return 软件集合
     */
    @Override
    public List<Software> selectAllSoftwares(){
        return softwareDao.selectAllSoftwares();
    }


    /**
     * 查询软件数量
     * @return 软件总数
     */
    @Override
    public int selectCountOfSoftwares(){
        return softwareDao.selectCountOfSoftwares();
    }


    /**
     * 根据当前页面查询软件列表
     * @param currentPage 当前页面
     * @return 当前页面需要显示的软件列表
     */
    @Override
    public List<Software> selectSoftwaresByCurrentPage(int currentPage){
        return softwareDao.selectSoftwaresByCurrentPage(currentPage);
    }


    /**
     * 根据指定ID查询软件名
     * @param id 查询软件ID
     * @return 软件集合
     */
    @Override
    public List<Software> selectSoftwareById(Long id) {
        return softwareDao.selectSoftwareById(id);
    }


    /**
     * 查找指定软件的所有  测试版本  版本号
     * @param id 指定的软件ID
     * @return 指定软件的所有版本的ID
     */
    @Override
    public List<SoftwareVersions> selectAllVersionBetaIdByFkId(Long id) {
        return softwareDao.selectAllVersionBetaIdByFkId(id);
    }


    /**
     * 查找指定软件的所有  发机版本  版本号
     * @param id 指定的软件ID
     * @return 指定软件的所有版本的ID
     */
    @Override
    public List<SoftwareVersions> selectAllVersionReleaseIdByFkId(Long id) {
        return softwareDao.selectAllVersionReleaseIdByFkId(id);
    }


    /**
     * 查找指定svId的版本
     * @param svId 指定id
     * @return 版本列表
     */
    @Override
    public List<SoftwareVersions> selectVersionDetailBySvId(Long svId) {
        return softwareDao.selectVersionDetailBySvId(svId);
    }


    /**
     * 更新软件版本 信息
     * @param softwareVersions 更新的信息
     * @return 改变的行数
     */
    @Override
    public int updateSoftwareDetail(SoftwareVersions softwareVersions) {
        return softwareDao.updateSoftwareDetail(softwareVersions);
    }


    /**
     * 增加  测试版本  信息
     * @param softwareVersions 增加的信息
     * @return 改变的行数
     */
    @Override
    public int addVersionBeta(SoftwareVersions softwareVersions) {
        return softwareDao.addVersionBeta(softwareVersions);
    }


    /**
     * 增加  发机版本  信息
     * @param softwareVersions 增加的信息
     * @return 改变的行数
     */
    @Override
    public int addReleaseVersion(SoftwareVersions softwareVersions) {
        return softwareDao.addReleaseVersion(softwareVersions);
    }

}
