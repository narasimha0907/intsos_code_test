package com.intlsos.crud.example.controller;

import com.intlsos.crud.example.entity.Doctor;
import com.intlsos.crud.example.entity.Patient;
import com.intlsos.crud.example.service.DoctorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/list")
    public List<Doctor> listAllDoctors() {
        return doctorService.listAllDoctors();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Doctor> getDoctorById(@PathVariable("id") int doctorId) {
        return new ResponseEntity<Doctor>(doctorService.getDoctorById(doctorId), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Doctor> saveDoctor(@RequestBody Doctor doctor) {
        return new ResponseEntity<Doctor>(doctorService.saveDoctor(doctor), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctorNameById(@PathVariable("id") int doctorId, @RequestParam(name="name", required = true) String doctorName) {
        return new ResponseEntity<Doctor>(doctorService.updateDoctorNameById(doctorId, doctorName), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctorById(@PathVariable("id") int doctorId) {
        doctorService.deleteDoctorById(doctorId);
        return new ResponseEntity<String>("Doctor deleted successfully.", HttpStatus.OK);
    }

    @PostMapping("/{id}/patient")
    @ResponseBody
    public ResponseEntity<String> addPatientUnderDoctor(@PathVariable("id") int doctorId, @RequestBody Patient patient) {
        doctorService.addPatientUnderDoctor(doctorId, patient);
        return new ResponseEntity<String>("Patient added under doctor successfully.", HttpStatus.OK);
    }

    @PutMapping("/{id}/patient/{pId}")
    public ResponseEntity<String> dischargePatient(@PathVariable("id") int doctorId, @PathVariable("pId") int patientId) {
        doctorService.dischargePatient(doctorId, patientId);
        return new ResponseEntity<String>("Patient discharged successfully.", HttpStatus.OK);
    }

}
