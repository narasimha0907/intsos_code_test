package com.intlsos.crud.example.service;

import com.intlsos.crud.example.entity.Patient;
import java.util.List;

public interface PatientService {

    Patient savePatient(Patient patient);

    List<Patient> listAllPatients();

    Patient getPatientById(int patientId);

    Patient updatePatientById(int patientId, Patient patient);

    void deletePatientById(int patientId);

    Patient assignDoctorToPatient(int patientId, int doctorId);
}
