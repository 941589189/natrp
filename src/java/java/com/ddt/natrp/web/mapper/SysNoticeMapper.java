package com.ddt.natrp.web.mapper;

import com.ddt.natrp.web.domain.system.SysNotice;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lzx
 */

@Mapper
public interface SysNoticeMapper {

    /**
     * 根据ID查找Notice
     * @param noticeId 日志ID
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
     * @param notice 日志
     */
    void insertNotice(SysNotice notice);
}
