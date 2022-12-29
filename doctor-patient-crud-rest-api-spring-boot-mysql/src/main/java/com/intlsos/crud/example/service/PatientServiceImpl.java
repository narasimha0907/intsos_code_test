package com.intlsos.crud.example.service;

import com.intlsos.crud.example.entity.Doctor;
import com.intlsos.crud.example.entity.Patient;
import com.intlsos.crud.example.exception.ResourceNotFoundException;
import com.intlsos.crud.example.repository.DoctorRepository;
import com.intlsos.crud.example.repository.PatientRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    //Logger integrated
    Logger logger = (Logger) LoggerFactory.getLogger(PatientServiceImpl.class);

    @Override
    public Patient savePatient(Patient patient) {
        logger.info("Saving patient record!!!" );
        return patientRepository.save(patient);
    }

    @Override
    public List<Patient> listAllPatients() {
        logger.info("Retrieving list of patient records!!!" );
        return patientRepository.findAll();
    }

    @Override
    public Patient getPatientById(int patientId) {
        logger.info("Retrieving patient record with Patient Id : {}", patientId);
        // check whether Patient with given id exists or not else throw exception
        return patientRepository.findById(patientId)
            .orElseThrow(() -> new ResourceNotFoundException("Patient", "Id", patientId));
    }

    @Override
    public Patient updatePatientById(int patientId, Patient patient) {
        logger.info("Updating patient record with Patient Id : {}", patientId);
        // check whether Patient with given id exists or not else throw exception

        Patient existingPatient = patientRepository.findById(patientId)
            .orElseThrow(() -> new ResourceNotFoundException("Patient", "Id", patientId));
        existingPatient.setName(patient.getName());
        existingPatient.setDisease(patient.getDisease());
        existingPatient.setDateOfBirth(patient.getDateOfBirth());

        // save updated Patient to DB
        patientRepository.save(existingPatient);
        return existingPatient;
    }

    @Override
    public void deletePatientById(int patientId) {
        logger.info("Deleting patient record with Patient Id : {}", patientId);
        // check whether patient with given id exists or not else throw exception
        Patient p = patientRepository.findById(patientId)
            .orElseThrow(() -> new ResourceNotFoundException("Patient", "Id", patientId));

        //Persisting the Doctor record in DB and deleting only the Patient record
        if(p.getDoctor() != null) {
            Doctor d = p.getDoctor();
            d.removePatient(p);
            doctorRepository.save(d);
        }
        patientRepository.deleteById(patientId);
    }

    @Override
    public Patient assignDoctorToPatient(int patientId, int doctorId) {
        logger.info("Assigning doctor with Doctor Id : {} to patient with Patient Id : {}", doctorId, patientId);

        // check whether patient with given id exists or not else throw exception
        Patient patient = patientRepository.findById(patientId)
            .orElseThrow(() -> new ResourceNotFoundException("Patient", "Id", patientId));
        // check whether doctor with given id exists or not else throw exception
        Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor", "Id", doctorId));
        patient.setDoctor(doctor);
        patientRepository.save(patient);
        return patient;
    }
}
