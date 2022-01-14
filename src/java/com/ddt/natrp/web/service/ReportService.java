package com.ddt.natrp.web.service;

import com.ddt.natrp.web.domain.laboratory.Report;

import java.util.Date;
import java.util.List;

/**
 * @author lzx
 */
public interface ReportService {
    /**
     * 查询报告单列表
     *
     * @param report 报告单 筛选
     * @return 结果
     */
    List<Report> selectReportList(Report report);

    /**
     * 查询报告单列表
     *
     * @param id 报告单 筛选
     * @return Report
     */
    Report selectReportById(Long id);

    /**
     * 时间段查询报告单  接收时间
     * @param startTime 起始时间
     * @param endTime   结束时间
     * @param status    样本状态
     * @return  List<Report></>
     */
    List<Report> selectReportByTime(Date startTime, Date endTime,String status);

    /**
     * 新建报告单
     * @param report 报告单
     */
    void insertReport(Report report);

    /**
     * 更新报告单
     *
     * @param report 报告单
     * @return 1
     */
    int updateReport(Report report);

    /**
     * 批量删除报告单信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteReportByIds(String ids);

    /**
     * 删除报告单信息
     *
     * @param reportId 信息ID
     * @return 结果
     */
    int deleteReportById(Long reportId);

}
