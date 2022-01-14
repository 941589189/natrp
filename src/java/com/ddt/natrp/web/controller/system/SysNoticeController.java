package com.ddt.natrp.web.controller.system;

import com.ddt.natrp.web.domain.system.SysNotice;
import com.ddt.natrp.web.service.SysNoticeService;
import com.example.demo.core.controller.BaseController;
import com.example.demo.core.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
@RequestMapping("/system/notice")
@Slf4j
public class SysNoticeController extends BaseController {

    private final SysNoticeService sysNoticeService;

    public SysNoticeController(SysNoticeService sysNoticeService){
        this.sysNoticeService = sysNoticeService;
    }

    /**
     * 主业务 最近一封 告用户书
     * @return ModelAndView
     */
    @GetMapping("/open")
    @ResponseBody
    public ModelAndView noticeOut() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("notice/outNotice");
        SysNotice notice = sysNoticeService.selectNoticeNow();
        Long noticeId = notice.getNoticeId();
        String text =notice.getText();
        String username = notice.getUser();
        Date createTime = notice.getCreateTime();
        mv.addObject("noticeId",noticeId);
        mv.addObject("text",text);
        mv.addObject("username",username);
        mv.addObject("createTime",SysNotice.formateTime(createTime));
        return mv;
    }

    /**
     * 录入告用户书界面
     * @return String
     */
    @RequiresRoles("worker")
    @GetMapping("/innotice")
    public String noticeIn() {
        return "notice/inNotice";
    }

    /**
     * 插入告用户书
     * @param data 用户书text 内容
     * @return AjaxResult
     */
    @RequiresPermissions("system:notice:innotice")
    @PostMapping("/innotice")
    @ResponseBody
    public AjaxResult noticeInsert(@RequestParam("filedata") String data){
        SysNotice sysNotice = new SysNotice();
        sysNotice.setText(data);
        sysNotice.setUser("管理员");
        try {
            sysNoticeService.insertNotice(sysNotice);
            return success("用户书保存成功");
        }catch (Exception e){
            log.error("告用户书保存失败：" + e.getMessage());
            return error("告用户书保存失败：");
        }
    }
}
