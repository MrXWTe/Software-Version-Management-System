package cn.xuweiteng.springboot.service;

import cn.xuweiteng.springboot.pojo.Software;

import java.util.List;

/**
 * 管理员操作的service
 */
public interface AdminService {
    /**
     * 查询所有软件
     * @return 软件集合
     */
    List<Software> selectAllSoftwares();

    /**
     * 查询软件数量
     * @return
     */
    int selectCountOfSoftwares();

    /**
     * 根据当前页面查询软件列表
     * @param current
     * @return
     */
    List<Software> selectSoftwaresByCurrentPage(int currentPage);
}
