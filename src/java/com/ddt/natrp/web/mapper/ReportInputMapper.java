package com.ddt.natrp.web.mapper;

import com.ddt.natrp.web.domain.laboratory.InReport;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportInputMapper {
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
    void insertReport(InReport report);
}
