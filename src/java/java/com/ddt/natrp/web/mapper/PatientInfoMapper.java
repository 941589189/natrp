package com.ddt.natrp.web.mapper;

import com.ddt.natrp.web.domain.laboratory.Patient;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 病人信息
 */
@Mapper
public interface PatientInfoMapper {
    /**
     * 查询患者信息
     * @param patientId 患者信息ID
     * @return 患者信息
     */
    Patient selectPatientById(Long patientId);

    /**
     * 查询患者信息列表
     *
     * @param patient 患者信息
     * @return 患者信息集合
     */
    List<Patient> selectPatientList(Patient patient);

    /**
     * 新增患者信息
     *
     * @param patient 患者信息
     * @return 结果
     */
    int insertPatient(Patient patient);

    /**
     * 修改患者信息
     *
     * @param patient 患者信息
     * @return 结果
     */
    int updatePatient(Patient patient);

    /**
     * 批量删除患者信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deletePatientByIds(String ids);

    /**
     * 删除患者信息信息
     *
     * @param patientId 患者信息ID
     * @return 结果
     */
    int deletePatientById(Long patientId);
}
