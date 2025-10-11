package com.pm.patient_service.Service;

import com.pm.patient_service.DTO.PatientRequestDTO;
import com.pm.patient_service.DTO.PatientResponseDTO;
import com.pm.patient_service.Exceptions.EmailAlreadyExistsException;
import com.pm.patient_service.Exceptions.PatientNotFoundExcepion;
import com.pm.patient_service.Repository.PatientRepository;
import com.pm.patient_service.mapper.PatientMapper;
import com.pm.patient_service.model.Patient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        List<PatientResponseDTO> patientResponseDTOS = patients.stream().map(patient -> PatientMapper.toDTO(patient)).toList();
        return patientResponseDTOS;
    }


    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("A patient Email already exists" + patientRequestDTO.getEmail());
        }
        Patient patient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
        return PatientMapper.toDTO(patient);
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundExcepion("patient not found :" + id));
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("A patient Email already exists" + patientRequestDTO.getEmail());
        }
        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        Patient updatedPatient =patientRepository.save(patient);
        return PatientMapper.toDTO(updatedPatient);

    }
}
