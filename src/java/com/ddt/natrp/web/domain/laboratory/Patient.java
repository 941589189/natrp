package com.ddt.natrp.web.domain.laboratory;

import com.ddt.natrp.common.annotation.ExportEntityMap;
import lombok.Data;

@Data
public class Patient {

    /** 患者ID */
    @ExportEntityMap(CnName = "患者ID",EnName = "patientId")
    private Long patientId;

    /** 患者姓名 */
    @ExportEntityMap(CnName = "患者姓名",EnName = "patientName")
    private String patientName;

    /** 患者性别 0 男 / 1 女 */
    @ExportEntityMap(CnName = "患者性别",EnName = "sex")
    private int sex;

    /** 年龄 **/
    @ExportEntityMap(CnName = "年龄",EnName = "age")
    private int age;

    /** 患者身份证号码 */
    @ExportEntityMap(CnName = "患者身份证号码",EnName = "patientNo")
    private String patientNo;

    /** 患者编号 */
    @ExportEntityMap(CnName = "患者编号",EnName = "serialNum")
    private Long serialNum;

    /** 患者手机号码 */
    @ExportEntityMap(CnName = "手机号码",EnName = "phoneNum")
    private String phoneNum;

    /** 患者所在省 */
    private String province;

    /** 患者所在市 */
    private String city;

    /** 患者所在区/县 */
    private String town;

    /** 详细地址 */
    private String address;

    /** 备注 */
    private String remark;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;
}
