package com.pm.patient_service.Service;

import com.pm.patient_service.DTO.PatientResponseDTO;
import com.pm.patient_service.Repository.PatientRepository;
import com.pm.patient_service.mapper.PatientMapper;
import com.pm.patient_service.model.Patient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {
    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients = patientRepository.findAll();
        List<PatientResponseDTO> patientResponseDTOS=patients.stream().map(patient-> PatientMapper.toDTO(patient)).toList();
        return patientResponseDTOS;


    }
}
