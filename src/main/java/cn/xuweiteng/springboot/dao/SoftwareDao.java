package cn.xuweiteng.springboot.dao;

import cn.xuweiteng.springboot.pojo.Software;
import cn.xuweiteng.springboot.pojo.SoftwareVersions;

import java.util.List;

public interface SoftwareDao {

    /**
     * 查询所有软件
     * @return 软件集合
     */
    List<Software> selectAllSoftwares();


    /**
     * 查询软件数量
     * @return 软件总数
     */
    int selectCountOfSoftwares();


    /**
     * 根据当前页面查询软件列表
     * @param currentPage 当前页面
     * @return 当前页面需要显示的软件列表
     */
    List<Software> selectSoftwaresByCurrentPage(int currentPage);


    /**
     * 根据指定ID查询软件名
     * @param id 查询软件ID
     * @return 软件集合
     */
    List<Software> selectSoftwareById(Long id);


    /**
     * 查找指定软件的所有  测试版本  版本号
     * @param softVersionId 指定的软件ID
     * @return 指定软件的所有版本的ID
     */
    List<SoftwareVersions> selectAllVersionBetaIdByFkId(Long softVersionId);


    /**
     * 查找指定软件的所有  发机版本  版本号
     * @param softVersionId 指定的软件ID
     * @return 指定软件的所有版本的ID
     */
    List<SoftwareVersions> selectAllVersionReleaseIdByFkId(Long softVersionId);


    /**
     * 查找指定svId的版本
     * @param svId 指定id
     * @return 版本列表
     */
    List<SoftwareVersions> selectVersionDetailBySvId(Long svId);


    /**
     * 更新  测试版本 信息
     * @param softwareVersions 更新的信息
     * @return 改变的行数
     */
    int updateSoftwareDetail(SoftwareVersions softwareVersions);


    /**
     * 增加  测试版本  信息
     * @param softwareVersions 增加的信息
     * @return 改变的行数
     */
    int addVersionBeta(SoftwareVersions softwareVersions);


    /**
     * 增加  发机版本  信息
     * @param softwareVersions 增加的信息
     * @return 改变的行数
     */
    int addReleaseVersion(SoftwareVersions softwareVersions);

}
