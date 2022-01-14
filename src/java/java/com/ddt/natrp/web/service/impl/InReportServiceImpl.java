package com.ddt.natrp.web.service.impl;

import com.ddt.natrp.web.domain.laboratory.InReport;
import com.ddt.natrp.web.mapper.ReportInputMapper;
import com.ddt.natrp.web.service.InReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class InReportServiceImpl implements InReportService {

    private final ReportInputMapper reportInMapper;

    public InReportServiceImpl(ReportInputMapper reportInMapper) {
        this.reportInMapper = reportInMapper;
    }

    @Override
    public List<InReport> selectReportList(InReport report) {
        return reportInMapper.selectReportList(report);
    }

    @Override
    public void insertReport(InReport report) {
        reportInMapper.insertReport(report);
    }
}
