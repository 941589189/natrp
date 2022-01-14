package com.ddt.natrp.web.service;

import com.ddt.natrp.web.domain.system.SysNotice;

public interface SysNoticeService {

    /**
     * 根据ID查找Notice
     * @param noticeId 文档ID
     * @return SysNotice
     */
    SysNotice selectNoticeById(Long noticeId);

    /**
     * 查找最近的一篇Notice
     * @return SysNotice
     */
    SysNotice selectNoticeNow();

    /**
     * 插入notice
     * @param notice notice
     */
    void insertNotice(SysNotice notice);
}
