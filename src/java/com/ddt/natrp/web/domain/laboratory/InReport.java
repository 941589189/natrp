package com.ddt.natrp.web.domain.laboratory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;

@Data
public class InReport {

    /** 报告单ID 后端Long超出17位  前端会丢失精度 */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long doctadviseno;

    /** 实验样本号 */
    private String sampleno;

    /** 申请者 */
    private String requester;

    /** 开单时间 */
    @JsonProperty("requesttime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date requestTime;

    /** 门诊号（就诊卡号） */
    @JsonProperty("patientid")
    private Long patientId;

    /** 病人姓名 */
    @JsonProperty("patientname")
    private String patientName;

    /** 性别 */
    private int sex;

    /** 年龄 */
    private int age;

    /** 检验目的 */
    private String examinaim;

    /** 样本类型 */
    @JsonProperty("sampletype")
    private String sampleType;

    /** 申请单位/科室 */
    private String section;

    /** 床号 */
    @JsonProperty("bed_no")
    private String bedNo;

    /** 病人性质 **/
    private String brxz;

    /** 结果 */
    private String result;

    /** 报告单状态(0未出 1已出 2作废) */
    @JsonProperty("SAMPLESTATUS")
    private String sampleStatus ;

    /** 报告时间 */
    @JsonProperty("CHECKTIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private String checkTime;
    /** 审核者 */
    private String checktor;
}
