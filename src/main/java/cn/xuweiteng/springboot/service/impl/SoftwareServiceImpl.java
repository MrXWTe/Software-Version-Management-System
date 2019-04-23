package cn.xuweiteng.springboot.service.impl;

import cn.xuweiteng.springboot.dao.SoftwareDao;
import cn.xuweiteng.springboot.pojo.Software;
import cn.xuweiteng.springboot.pojo.SoftwareVersions;
import cn.xuweiteng.springboot.service.SoftwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("softwareService")
@Transactional(rollbackFor = Exception.class)
public class SoftwareServiceImpl implements SoftwareService {

    private final SoftwareDao softwareDao;

    @Autowired
    public SoftwareServiceImpl(SoftwareDao softwareDao) {
        this.softwareDao = softwareDao;
    }


    /**
     * 查询所有软件
     *
     * @return 软件集合
     */
    @Override
    public List<Software> selectAllSoftwares() {
        List<Software> list;
        try {
            list = softwareDao.selectAllSoftwares();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    /**
     * 查询软件数量
     *
     * @return 软件总数
     */
    @Override
    public int selectCountOfSoftwares() {
        int row;
        try {
            row = softwareDao.selectCountOfSoftwares();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return row;
    }


    /**
     * 根据当前页面查询软件列表
     *
     * @param currentPage 当前页面
     * @return 当前页面需要显示的软件列表
     */
    @Override
    public List<Software> selectSoftwaresByCurrentPage(int currentPage) {
        List<Software> list;
        try {
            list = softwareDao.selectSoftwaresByCurrentPage(currentPage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    /**
     * 根据指定ID查询软件名
     *
     * @param id 查询软件ID
     * @return 软件集合
     */
    @Override
    public List<Software> selectSoftwareById(Long id) {
        List<Software> list;
        try {
            list = softwareDao.selectSoftwareById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    /**
     * 查找指定软件的所有  测试版本  版本号
     *
     * @param id 指定的软件ID
     * @return 指定软件的所有版本的ID
     */
    @Override
    public List<SoftwareVersions> selectAllVersionBetaIdByFkId(Long id) {
        List<SoftwareVersions> list;
        try {
            list = softwareDao.selectAllVersionBetaIdByFkId(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    /**
     * 查找指定软件的所有  发机版本  版本号
     *
     * @param id 指定的软件ID
     * @return 指定软件的所有版本的ID
     */
    @Override
    public List<SoftwareVersions> selectAllVersionReleaseIdByFkId(Long id) {
        List<SoftwareVersions> list;
        try {
            list = softwareDao.selectAllVersionReleaseIdByFkId(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    /**
     * 查找指定svId的版本
     *
     * @param svId 指定id
     * @return 版本列表
     */
    @Override
    public List<SoftwareVersions> selectVersionDetailBySvId(Long svId) {
        List<SoftwareVersions> list;
        try {
            list = softwareDao.selectVersionDetailBySvId(svId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    /**
     * 更新软件信息
     *
     * @param software 更新的软件
     * @return 改变的行数
     */
    @Override
    public int updateSoftware(Software software) {
        int row;
        try {
            row = softwareDao.updateSoftware(software);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return row;
    }


    /**
     * 更新软件版本 信息
     *
     * @param softwareVersions 更新的信息
     * @return 改变的行数
     */
    @Override
    public int updateSoftwareDetail(SoftwareVersions softwareVersions) {
        int row;
        try {
            row = softwareDao.updateSoftwareDetail(softwareVersions);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return row;
    }


    /**
     * 添加软件
     *
     * @param software 软件对象
     * @return 改变的行数
     */
    @Override
    public int addSoftware(Software software) {
        int row;
        try {
            row = softwareDao.addSoftware(software);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return row;
    }


    /**
     * 增加  测试版本  信息
     *
     * @param softwareVersions 增加的信息
     * @return 改变的行数
     */
    @Override
    public int addVersionBeta(SoftwareVersions softwareVersions) {
        int row;
        try {
            row = softwareDao.addVersionBeta(softwareVersions);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return row;
    }


    /**
     * 增加  发机版本  信息
     *
     * @param softwareVersions 增加的信息
     * @return 改变的行数
     */
    @Override
    public int addReleaseVersion(SoftwareVersions softwareVersions) {
        int row;
        try {
            row = softwareDao.addReleaseVersion(softwareVersions);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return row;
    }
}
