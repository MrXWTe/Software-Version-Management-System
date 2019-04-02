package cn.xuweiteng.springboot.dao;

import cn.xuweiteng.springboot.pojo.Software;

import java.util.List;

/**
 * 主要是管理员操作的DAO
 */
public interface AdminDao {

    /**
     * 查询所有软件
     * @return 软件集合
     */
    List<Software> selectAllSoftwares();
}
