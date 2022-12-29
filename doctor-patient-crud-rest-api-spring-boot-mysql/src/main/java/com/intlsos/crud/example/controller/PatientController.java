package com.intlsos.crud.example.controller;

import com.intlsos.crud.example.entity.Patient;
import com.intlsos.crud.example.service.PatientService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/list")
    public List<Patient> listAllPatients() {
        return patientService.listAllPatients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable("id") int patientId) {
        return new ResponseEntity<Patient>(patientService.getPatientById(patientId), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Patient> savePatient(@RequestBody Patient patient) {
        return new ResponseEntity<Patient>(patientService.savePatient(patient), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatientById(@PathVariable("id") int patientId, @RequestBody Patient patient) {
        return new ResponseEntity<Patient>(patientService.updatePatientById(patientId, patient), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatientById(@PathVariable(name="id", required = true) int patientId) {
        patientService.deletePatientById(patientId);
        return new ResponseEntity<String>("Patient deleted successfully.", HttpStatus.OK);
    }

    // assign doctor to Patient
    @PutMapping("/{pId}/doctor/{dId}")
    @ResponseBody
    public ResponseEntity<Patient> assignDoctorToPatient(@PathVariable(name="pId", required = true) int patientId,
        @PathVariable(name="dId", required = true) int doctorId) {
        patientService.assignDoctorToPatient(patientId, doctorId);
        return new ResponseEntity<Patient>(patientService.assignDoctorToPatient(patientId, doctorId), HttpStatus.OK);
    }

}
