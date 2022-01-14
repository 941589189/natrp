package com.ddt.natrp.web.controller.report;

import com.ddt.natrp.common.utils.ExportExcelUtils;
import com.ddt.natrp.common.utils.ShiroUtils;
import com.ddt.natrp.web.domain.laboratory.Report;
import com.ddt.natrp.web.domain.system.SysUser;
import com.ddt.natrp.web.service.ReportService;
import com.example.demo.core.controller.BaseController;
import com.example.demo.core.domain.AjaxResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author lzx
 */
@Controller
@RequestMapping(value = "/report")
@Slf4j
public class ReportMainController extends BaseController {

    private final ReportService reportService;

    private final ReportBase reportBase;

    public ReportMainController(ReportService reportService, ReportBase reportBase) {
        this.reportService = reportService;
        this.reportBase = reportBase;
    }

    @GetMapping(value = "/main")
    public String openMain(){
        return "report/main";
    }

    @RequiresRoles("worker")
    @GetMapping(value = "/check")
    public String openCheck(){
        return "report/report_check";
    }

    /**
     * 报告单主页面
     * @param reportId      reportID
     * @param patientId     patientId
     * @param status        status
     * @param startTime     startTime
     * @param endTime       endTime
     * @return PageInfo<Report>
     */
    @PostMapping("/list")
    @ResponseBody
    @ApiOperation(value = "报告单列表")
    public PageInfo<Report> reportList(@RequestParam(value = "reportId",defaultValue = "-1") String reportId,
                                       @RequestParam(value = "patientId",defaultValue = "-1") String patientId,
                                       //status 用来接收 标本审核 请求  默认为空
                                       @RequestParam(value = "status",required = false) String status,
                                       @RequestParam(value = "startTime",required = false) Date startTime,
                                       @RequestParam(value = "endTime",required = false) Date endTime) {
        PageHelper.startPage(1,15);
        return new PageInfo<>(reportBase.selectReport(Long.valueOf(reportId), Long.valueOf(patientId), status, startTime, endTime));
    }

    @PostMapping("/receive/today")
    @ResponseBody
    @RequiresPermissions("report:receive:today")
    @ApiOperation(value = "接收当日报告单")
    public PageInfo<Report> reportListByReceive(){
        Date date = new Date();
        PageHelper.startPage(1,15);
        Report report = new Report();
        report.setReceiveTime(date);
        return new PageInfo<>(reportService.selectReportList(report));
    }

    /**
     * 报告单录入
     * @param report 报告单
     * @return AjaxResult
     */
    @RequiresPermissions("report:insert")
    @PostMapping("/insert")
    @ResponseBody
    public AjaxResult insertReport(Report report){
        String msg;
        String status = "未审核";
        //前端传入 "未审核"
        if(status.equals(report.getStatus())){
            report.setStatus("0");
        }else{
            report.setStatus("1");
        }
        try{
            reportService.insertReport(report);
            msg = "保存成功";
            return success(msg);
        }catch (DuplicateKeyException e) {
            log.error(e.getMessage());
            msg = "该报告单已存在";
            return error(msg);
        }catch (Exception e){
            log.error(e.getMessage());
            msg = "保存失败";
            return error(msg);
        }
    }

    /**
     * 查询未审核报告单, 用于审核报告单
     * @param reportId      reportId
     * @param patientId     patientId
     * @param status        status
     * @param startTime     startTime 接收 开始时间
     * @param endTime       endTime   结束 开始时间
     * @return PageInfo<Report>
     */
    @RequiresPermissions("report:check")
    @PostMapping("/list/time")
    @ResponseBody
    public PageInfo<Report> selectListByTime(@RequestParam(value = "reportId",defaultValue = "-1") String reportId,
                                             @RequestParam(value = "patientId",defaultValue = "-1") String patientId,
                                             @RequestParam(value = "status",required = false) String status,
                                             @RequestParam(value = "startTime",required = false) Date startTime,
                                             @RequestParam(value = "endTime",required = false) Date endTime){
        PageHelper.startPage(1,15);
        return new PageInfo<>(reportBase.selectReport(Long.valueOf(reportId), Long.valueOf(patientId), status, startTime, endTime));
    }

    /**
     * 报告单 审核
     * @param ids 报告单列表
     * @return AjaxResult
     */
    @RequiresPermissions("report:status")
    @PostMapping("/status")
    @ResponseBody
    public AjaxResult updateStatus(@RequestBody String[] ids){
        String msg = "审核通过";
        Report report = new Report();
        Date dateNow = new Date();
        //取出当前用户数据
        SysUser user = ShiroUtils.getSysUser();
        //遍历ids
        for(String id:ids) {
            Long reportId = Long.valueOf(id);
            report.setReportId(reportId);
            //将报告状态 未审核 改为 已审核
            report.setStatus("1");
            //将当前时间  设为  审核时间
            report.setCheckTime(dateNow);
            //当前用户设为审核者
            report.setChecktor(user.getUserName());
            try {
                reportService.updateReport(report);
            } catch (Exception e) {
                log.error(e.getMessage());
                msg = "审核未通过";
                return error(msg);
            }
        }
        return success(msg);
    }

    /**
     * 报告单结果，备注录入
     * @param reportId 报告单Id
     * @param column 修改的字段名
     * @param value 修改的值
     */
    @PostMapping("/update")
    @ResponseBody
    public void update(@RequestParam("pk") Long reportId,
                       @RequestParam("name") String column,
                       @RequestParam("value") String value){
        Report report = new Report();
        //取第一个
        System.out.println("reportId"+reportId+",column:"+column+",value:"+value);
        report = reportService.selectReportById(reportId);
        switch (column){
            case "result":
                report.setResult(value);
                break;
            case "remark":
                report.setRemark(value);
                break;
            default:
               log.error("修改的字段不存在");
               break;
        }
        reportService.updateReport(report);
    }

    /**
     * 导出报告单Excel
     * @param startTime     开始时间  创建时间
     * @param endTime       结束时间
     * @param response      response
     */
    @RequiresPermissions("report:export")
    @GetMapping("/export")
    @ResponseBody
    public void exportReport(@RequestParam(value = "startTime") Date startTime,
                             @RequestParam(value = "endTime") Date endTime,
                              HttpServletResponse response){
        List<Report> reports = reportService.selectReportByTime(startTime,endTime,"1");
        String fileName = "报告单表";
        ExportExcelUtils.export(fileName,reports,Report.class,response);
    }
}
