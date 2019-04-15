package cn.xuweiteng.springboot.dao.impl;

import cn.xuweiteng.springboot.dao.SoftwareDao;
import cn.xuweiteng.springboot.pojo.Software;
import cn.xuweiteng.springboot.pojo.SoftwareVersions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("softwareDao")
public class SoftwareDaoImpl implements SoftwareDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SoftwareDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }



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
     * @return 软件总数
     */
    public int selectCountOfSoftwares(){
        return jdbcTemplate.queryForObject("select count(*) from tb_software", Integer.class);
    }


    /**
     * 根据当前页面查询软件列表
     * @param currentPage 当前页面
     * @return 当前页面需要显示的软件列表
     */
    public List<Software> selectSoftwaresByCurrentPage(int currentPage){
        int startNum = (currentPage-1) * 4;
        int endPage = 4;
        String sql = "select soft_id as softId, soft_name as softName, " +
                "soft_info as softInfo, soft_last_modified_date as " +
                "softLastModifiedDate from tb_software limit " + startNum + ", " + endPage;

        List<Software> softwareList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Software.class));
        return softwareList;
    }


    /**
     * 根据指定ID查询软件名
     * @param id 查询软件ID
     * @return 软件集合
     */
    public List<Software> selectSoftwareById(Long id){
        String sql = "select soft_name as softName from tb_software where soft_id=?";
        List<Software> softwareList = jdbcTemplate.query(sql, new Object[] {id},
                new BeanPropertyRowMapper<>(Software.class));
        return softwareList;

    }


    /**
     * 查找指定软件的所有  测试版本  号
     * @param softVersionId 指定的软件ID
     * @return 指定软件的所有版本的ID
     */
    @Override
    public List<SoftwareVersions> selectAllVersionBetaIdByFkId(Long softVersionId) {
        String sql = "select sv_id as svId, sv_versionId as svVersionId, sv_link as svLink from tb_version where soft_version_id=? and sv_version=?";
        List<SoftwareVersions> versionList = jdbcTemplate.query(sql, new Object[]{softVersionId, 0},//0 代表测试版
                new BeanPropertyRowMapper<>(SoftwareVersions.class));
        return versionList;
    }


    /**
     * 查找指定软件的所有  发机版本  号
     * @param softVersionId 指定的软件ID
     * @return 指定软件的所有版本的ID
     */
    @Override
    public List<SoftwareVersions> selectAllVersionReleaseIdByFkId(Long softVersionId) {
        String sql = "select sv_id as svId, sv_versionId as svVersionId, sv_link as svLink from tb_version " +
                "where soft_version_id=? and sv_version=?";

        List<SoftwareVersions> versionList = jdbcTemplate.query(sql, new Object[]{softVersionId, 1}, //1 代表发机版
                new BeanPropertyRowMapper<>(SoftwareVersions.class));
        return versionList;
    }


    /**
     * 查找指定svId的版本信息
     * @param svId 指定id
     * @return 版本列表
     */
    @Override
    public List<SoftwareVersions> selectVersionDetailBySvId(Long svId) {
        String sql = "select sv_id as svId, sv_info as svInfo, sv_link as svLink, sv_versionId as svVersionId, " +
                "sv_version as svVersion, soft_version_id as softVersionId from tb_version" +
                " inter join tb_software on soft_version_id=soft_id where sv_id=?";

        List<SoftwareVersions> versionList = jdbcTemplate.query(sql, new Object[] {svId},
                new BeanPropertyRowMapper<>(SoftwareVersions.class));
        return versionList;
    }


    /**
     * 更新软件版本 信息
     * @param softwareVersions 更新的信息
     * @return 改变的行数
     */
    @Override
    public int updateSoftwareDetail(SoftwareVersions softwareVersions) {
        String sql = "update tb_version set sv_info=?, sv_versionId=? where sv_id=?";
        return jdbcTemplate.update(sql, new Object[] {softwareVersions.getSvInfo(),
                softwareVersions.getSvVersionId(), softwareVersions.getSvId()});
    }


    /**
     * 增加  测试版本  信息
     * @param softwareVersions 增加的信息
     * @return 改变的行数
     */
    @Override
    public int addVersionBeta(SoftwareVersions softwareVersions) {
        String sql = "insert into tb_version (sv_info, sv_link, soft_version_id, " +
                "sv_versionId, sv_version) values (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, new Object[] {softwareVersions.getSvInfo(),
                softwareVersions.getSvLink(),
                softwareVersions.getSoftVersionId(),
                softwareVersions.getSvVersionId(),
                0});
    }


    /**
     * 增加  发机版本  信息
     * @param softwareVersions 增加的信息
     * @return 改变的行数
     */
    @Override
    public int addReleaseVersion(SoftwareVersions softwareVersions) {
        String sql = "insert into tb_version (sv_info, sv_link, soft_version_id, " +
                "sv_versionId, sv_version) values (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, new Object[] {softwareVersions.getSvInfo(),
                softwareVersions.getSvLink(),
                softwareVersions.getSoftVersionId(),
                softwareVersions.getSvVersionId(),
                1});
    }

}
