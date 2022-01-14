package com.ddt.natrp.web.controller.report;

import com.ddt.natrp.common.utils.PDFUtil;
import com.ddt.natrp.web.domain.laboratory.Report;
import com.ddt.natrp.web.service.ReportService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.TemplateEngine;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/report")
public class ReportPDFController {

    private final ReportService reportService;

    private final TemplateEngine templateEnginel;

    @Value("${pdf.windowsFileTempLoc}")
    private String windowsUrl;

    @Value("${pdf.linuxFileTempLoc}")
    private String linuxUrl;

    public ReportPDFController(ReportService reportService, TemplateEngine templateEnginel) {
        this.reportService = reportService;
        this.templateEnginel = templateEnginel;
    }

    @GetMapping(value = "/pdf/download")
    @ApiOperation(value="pdf下载")
    public void download(@RequestParam("reportId") String reportId, HttpServletResponse response) {
        Report report = new Report();
        String fileName = "未定义报告单";
        report.setReportId(Long.valueOf(reportId));
        //定义下载的 数据集合
        List<Map<String,Object>> listVars = new ArrayList<>(1);

        //title
        Map<String,Object> variablesA = new HashMap<>(16);
        variablesA.put("title","测试下载PDF!");

        //data  将List<Report>中report依次拿出来
        List<Report> reports = reportService.selectReportList(report);
        for (Report reportOne : reports) {
            variablesA.put("data", reportOne);
            fileName = reportOne.getPatientName();
        }
        //将map放入 listVars
        listVars.add(variablesA);
        PDFUtil.download(templateEnginel,"PDF/repoertPDF",listVars,response,(fileName +".pdf"));
    }
}
