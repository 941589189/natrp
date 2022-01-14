package com.ddt.natrp.web.domain.laboratory;

import com.ddt.natrp.common.annotation.ExportEntityMap;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class Report {

    /** 报告单ID 后端Long超出17位  前端会丢失精度 */
    @ExportEntityMap(CnName = "报告单ID",EnName = "reportId")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long reportId;

    /** 患者ID */
    @ExportEntityMap(CnName = "患者ID",EnName = "patientId")
    private Long patientId;

    /** 患者姓名 */
    @ExportEntityMap(CnName = "患者姓名",EnName = "patientName")
    private String patientName;

    /** 开单日期 */
    @ExportEntityMap(CnName = "开单日期",EnName = "createTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    /** 申请者 */
    @ExportEntityMap(CnName = "申请者",EnName = "createBy")
    private String createBy;

    /** 报告状态 */
    private String status;

    /** 医嘱项目 */
    @ExportEntityMap(CnName = "医嘱项目",EnName = "item")
    private String item;

    /** 结果 */
    @ExportEntityMap(CnName = "结果",EnName = "result")
    private String result;

    /** 样本类型 **/
    private String sampleType;

    /** 样本接收时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date receiveTime;

    /** 审核时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @ExportEntityMap(CnName = "审核时间",EnName = "checkTime")
    private Date checkTime;

    /** 审核者 **/
    @ExportEntityMap(CnName = "审核者",EnName = "checktor")
    private String checktor;

    /** 备注 */
    private String remark;

    /** 病人信息 */
    private Patient patient;

    /** 日期格式化 */
    public static String formateTime(Date date) {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }
}
