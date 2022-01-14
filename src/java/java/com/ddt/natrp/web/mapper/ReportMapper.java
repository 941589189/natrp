package com.ddt.natrp.web.mapper;

import com.ddt.natrp.web.domain.laboratory.Report;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ReportMapper {

    /**
     * 查询报告单列表
     *
     * @param report 报告单 筛选
     * @return List<Report>
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
     * 时间段查询报告单
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param status    报告单状态
     * @return List<Report>
     */
    List<Report> selectReportByTime(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("status") String status);

    /**
     * 新建报告单
     *
     * @param report 报告单
     */
    void insertReport(Report report);

    /**
     * 更新报告单
     *
     * @param report 报告单
     * @return 结果
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
