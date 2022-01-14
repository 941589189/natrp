package com.ddt.natrp.web.controller.report;

import com.ddt.natrp.web.domain.laboratory.InReport;
import com.ddt.natrp.web.service.InReportService;
import com.example.demo.core.controller.BaseController;
import com.example.demo.core.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reportin")
@Slf4j
public class ReportInputController extends BaseController {

    private final InReportService inReportService;

    public ReportInputController(InReportService inReportService) {
        this.inReportService = inReportService;
    }

    @RequiresRoles("worker")
    @GetMapping("/main")
    public String openMain(){
        return "report/reportInput";
    }

    /**
     *
     * @param reportId 报告单Id
     * @return  AjaxResult
     */
    @RequiresPermissions("reportin:list")
    @PostMapping("/list")
    @ResponseBody
    public AjaxResult selectList(@RequestParam(value = "reportId",required = false) Long reportId){
        InReport inReport = new InReport();
        inReport.setDoctadviseno(reportId);
        try {
            List<InReport> inReports = inReportService.selectReportList(inReport);
            //判断是否查询到数据
            if(inReports.isEmpty()){
                return new AjaxResult(AjaxResult.Type.ERROR,"查询失败,无数据");
            }else {
                //查询到多个数据只取第一个数据
                return new AjaxResult(AjaxResult.Type.SUCCESS,"查询成功",inReports.get(0));
            }
        }catch (Exception e){
            log.error(e.getMessage());
            return new AjaxResult(AjaxResult.Type.ERROR,"查询失败");
        }
    }

    /**
     * 通过Excel批量导入 报告单
     */
    @RequiresPermissions("reportin:excel")
    @PostMapping("/excel")
    @ResponseBody
    @Async
    public void excel(@RequestBody List<InReport> fileData){
        for(int i=0;i<fileData.size();i++){
            try {
                inReportService.insertReport(fileData.get(i));
            }catch (Exception e){
                log.error("第" + i + "行插入错误：" + e.getMessage());
            }
            log.info("线程" + Thread.currentThread().getName()+"完成插入InReport操作");
        }
    }
}
