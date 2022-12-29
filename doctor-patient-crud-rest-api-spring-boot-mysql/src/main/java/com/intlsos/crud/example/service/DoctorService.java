package com.intlsos.crud.example.service;

import com.intlsos.crud.example.entity.Doctor;
import com.intlsos.crud.example.entity.Patient;
import java.util.List;

public interface DoctorService {

        Doctor saveDoctor(Doctor doctor);

        List<Doctor> listAllDoctors();

        Doctor getDoctorById(int doctorId);

        Doctor updateDoctorNameById(int doctorId, String doctorName);

        void deleteDoctorById(int doctorId);

        void addPatientUnderDoctor(int doctorId, Patient patient);

        void dischargePatient(int doctorId, int patientId);
}
