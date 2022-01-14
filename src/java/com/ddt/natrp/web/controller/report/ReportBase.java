package com.ddt.natrp.web.controller.report;

import com.ddt.natrp.web.domain.laboratory.Report;
import com.ddt.natrp.web.service.ReportService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ReportBase {

    private final ReportService reportService;

    public ReportBase(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * report复杂查询
     * @param reportId      报告单ID
     * @param patientId     病人ID
     * @param status        报告单状态
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @return List<Report>
     */
    public List<Report> selectReport(Long reportId, Long patientId, String status, Date startTime, Date endTime){
        List<Report> reports;
        Report report = new Report();
        //如果存在报告单 id  则优先  (无需指定样本状态)
        if(reportId != -1){
            report.setReportId(reportId);
            reports = reportService.selectReportList(report);
            //如果存在 病人就诊号  优先 (无需指定样本状态)
        }else if(patientId != -1){
            report.setPatientId(patientId);
            reports = reportService.selectReportList(report);
            //如果前端没有传日期 或者 "开始时间等于结束时间" 则默认为今天
        }else if(DateUtils.isSameDay(startTime,endTime)){
            report.setReceiveTime(endTime);
            report.setStatus(status);
            reports = reportService.selectReportList(report);
        }else{
            reports = reportService.selectReportByTime(startTime, endTime, status);
        }
        return reports;
    }

}
