package com.intlsos.crud.example.service.impl;

import static org.mockito.Mockito.when;

import com.intlsos.crud.example.DoctorPatientCrudRestApiSpringBootMysqlApplication;
import com.intlsos.crud.example.entity.Patient;
import com.intlsos.crud.example.repository.PatientRepository;
import com.intlsos.crud.example.service.PatientServiceImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = DoctorPatientCrudRestApiSpringBootMysqlApplication.class)
@AutoConfigureMockMvc
public class PattientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    Patient patient  = new Patient();
    Patient patient2  = new Patient();
    Patient Patient = new Patient();
    List<Patient> patientList = new ArrayList<>();


    @BeforeEach
    public void setup() {
        patient.setId(1);
        patient.setName("Hari");
        patient2.setId(2);
        patient2.setName("Suresh");
        patient2.setDateOfBirth(new Date());

        patientList.addAll(List.of(patient,patient2));
    }

    @Test
    public void savePatientTest() {
        when(patientRepository.save(patient)).thenReturn(patient);
        Patient savedPatient = patientService.savePatient(patient);
        Assert.assertNotNull(savedPatient);
    }

    @Test
    public void listAllPatientsTest() {
        when(patientRepository.findAll()).thenReturn(patientList);
        List<Patient> savePatient = patientService.listAllPatients();
        Assert.assertEquals(2,savePatient.size());
    }

    @Test
    public void getPatientByIdTest() {
        when(patientRepository.findById(1)).thenReturn(Optional.ofNullable(patient));
        Patient savePatient = patientService.getPatientById(1);
        Assert.assertEquals("Hari",savePatient.getName());
    }

    @Test
    public void updatePatientNameByIdTest() {
        when(patientRepository.findById(1)).thenReturn(Optional.ofNullable(patient));
        Patient savePatient = patientService.getPatientById(1);
        savePatient.setName("Ramesh");
        when(patientRepository.save(savePatient)).thenReturn(savePatient);
        Assert.assertEquals("Ramesh",savePatient.getName());
    }


}
