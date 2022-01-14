package com.ddt.natrp.web.service.impl;

import com.ddt.natrp.web.domain.laboratory.Report;
import com.ddt.natrp.web.mapper.ReportMapper;
import com.ddt.natrp.web.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

/**
 * 报告单服务层
 * @author lzx
 */

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final ReportMapper reportMapper;

    public ReportServiceImpl(ReportMapper reportMapper) {
        this.reportMapper = reportMapper;
    }


    @Override
    public List<Report> selectReportList(Report report) {
        return reportMapper.selectReportList(report);
    }

    @Override
    public Report selectReportById(Long id) {
        return reportMapper.selectReportById(id);
    }

    @Override
    public List<Report> selectReportByTime(Date startTime, Date endTime,String status) {
        return reportMapper.selectReportByTime(startTime,endTime,status);
    }

    @Override
    public void insertReport(Report report) {
        reportMapper.insertReport(report);
    }

    @Override
    public int updateReport(Report report) {
        return reportMapper.updateReport(report);
    }

    @Override
    public int deleteReportByIds(String ids) {
        return 0;
    }

    @Override
    public int deleteReportById(Long reportId) {
        return reportMapper.deleteReportById(reportId);
    }

}
