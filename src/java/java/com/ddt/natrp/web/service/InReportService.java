package com.ddt.natrp.web.service;

import com.ddt.natrp.web.domain.laboratory.InReport;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface InReportService {

    /**
     * 查询报告单列表
     *
     * @param report 报告单 筛选
     * @return 结果
     */
    List<InReport> selectReportList(InReport report);

    /**
     * 新建报告单
     *
     * @param report 报告单
     */
    @Async("taskExecutor")
    void insertReport(InReport report);
}
