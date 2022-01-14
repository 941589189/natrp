package com.ddt.natrp.web.service.impl;

import com.ddt.natrp.web.domain.system.SysNotice;
import com.ddt.natrp.web.mapper.SysNoticeMapper;
import com.ddt.natrp.web.service.SysNoticeService;
import org.springframework.stereotype.Service;

@Service
public class SysNoticeServiceImpl implements SysNoticeService {

    private final SysNoticeMapper sysNoticeMapper;

    public SysNoticeServiceImpl(SysNoticeMapper sysNoticeMapper) {
        this.sysNoticeMapper = sysNoticeMapper;
    }


    @Override
    public SysNotice selectNoticeById(Long noticeId) {
        return sysNoticeMapper.selectNoticeById(noticeId);
    }

    @Override
    public SysNotice selectNoticeNow() {
        return sysNoticeMapper.selectNoticeNow();
    }

    @Override
    public void insertNotice(SysNotice notice) {
        sysNoticeMapper.insertNotice(notice);
    }


}
