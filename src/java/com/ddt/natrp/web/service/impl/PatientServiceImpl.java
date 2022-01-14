package com.ddt.natrp.web.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.ddt.natrp.web.domain.laboratory.Patient;
import com.ddt.natrp.web.mapper.PatientInfoMapper;
import com.ddt.natrp.web.service.PatientService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientInfoMapper patientInfoMapper;

    public PatientServiceImpl(PatientInfoMapper patientInfoMapper){
        this.patientInfoMapper = patientInfoMapper;
    }

    @Override
    public Patient selectPatientById(Long patientId) {
        return patientInfoMapper.selectPatientById(patientId);
    }

    @Override
    public List<Patient> selectPatientList(Patient patient) {
        return patientInfoMapper.selectPatientList(patient);
    }

    @Override
    public int insertPatient(Patient patient) {
        String frontSerial = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN);
        String baclSerial = RandomUtil.randomNumbers(4);
        Long serial = Long.valueOf(frontSerial + baclSerial);
        patient.setSerialNum(serial);
        return patientInfoMapper.insertPatient(patient);
    }

    @Override
    public int updatePatient(Patient patient) {
        return patientInfoMapper.updatePatient(patient);
    }

    @Override
    public int deletePatientByIds(String ids) {
        return 0;
    }

    @Override
    public int deletePatientById(Long patientId) {
        return patientInfoMapper.deletePatientById(patientId);
    }
}
