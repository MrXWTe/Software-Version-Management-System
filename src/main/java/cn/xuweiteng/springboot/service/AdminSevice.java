package cn.xuweiteng.springboot.service;

import cn.xuweiteng.springboot.pojo.Software;

import java.util.List;

/**
 * 管理员操作的service
 */
public interface AdminSevice {
    /**
     * 查询所有软件
     * @return 软件集合
     */
    List<Software> selectAllSoftwares();
}
