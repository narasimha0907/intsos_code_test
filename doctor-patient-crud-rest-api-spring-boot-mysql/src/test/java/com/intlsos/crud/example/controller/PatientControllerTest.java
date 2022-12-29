package com.intlsos.crud.example.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intlsos.crud.example.DoctorPatientCrudRestApiSpringBootMysqlApplication;
import com.intlsos.crud.example.entity.Doctor;
import com.intlsos.crud.example.entity.Patient;
import com.intlsos.crud.example.service.PatientService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = DoctorPatientCrudRestApiSpringBootMysqlApplication.class)
@AutoConfigureMockMvc
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @Autowired
    private ObjectMapper objectMapper;

    Doctor  doctor  = new Doctor();
    Doctor  doctor2  = new Doctor();
    Patient patient = new Patient();
    Patient patient2 = new Patient();

    List<Patient> patientList = new ArrayList<>();


    @BeforeEach
    public void setUp () {

        doctor.setId(1);
        doctor.setName("Simha");

        doctor2.setId(2);
        doctor2.setName("Reddy");

        patient.setId(1);
        patient.setName("Hari");
        patient.setDisease("Fever");
        patient.setDateOfBirth(new Date());
        patient.setDoctor(doctor);

        patient2.setId(2);
        patient2.setName("Ramesh");
        patient2.setDisease("Knee Pain");
        patient2.setDateOfBirth(new Date());
        patient2.setDoctor(doctor2);
        patientList.addAll(List.of(patient,patient2));

    }

    @Test
    public void getPatientByIdTest() throws Exception {
        when(patientService.getPatientById(1)).thenReturn(patient);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/patients/1")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id",is(1)))
            .andExpect(jsonPath("$.name",is("Hari")))
            .andExpect(jsonPath("$.doctor.name",is("Simha")));
    }

    @Test
    public void listAllPatientsTest() throws Exception {
        when(patientService.listAllPatients()).thenReturn(patientList);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/patients/list")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id",is(1)))
            .andExpect(jsonPath("$[0].name",is("Hari")))
            .andExpect(jsonPath("$[0].disease",is("Fever")))
            .andExpect(jsonPath("$[0].doctor.name",is("Simha")))
            .andExpect(jsonPath("$[1].id",is(2)))
            .andExpect(jsonPath("$[1].name",is("Ramesh")))
            .andExpect(jsonPath("$[1].disease",is("Knee Pain")))
            .andExpect(jsonPath("$[1].doctor.name",is("Reddy")));

    }

    @Test
    public void savePatientTest() throws Exception {
        when(patientService.savePatient(patient)).thenReturn(patient);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/patients/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(patient)))
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id",is(1)))
            .andExpect(jsonPath("$.name",is("Hari")));
    }

    @Test
    public void updatePatientByIdTest() throws Exception {

        when(patientService.updatePatientById(1,patient)).thenReturn(patient);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/patients/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(patient)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id",is(1)))
            .andExpect(jsonPath("$.name",is("Hari")));
    }

    @Test
    public void deletePatientByIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/patients/1")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(content().string("Patient deleted successfully."));
    }

    @Test
    public void assignDoctorToPatientTest() throws Exception {

        when(patientService.assignDoctorToPatient(1,2)).thenReturn(patient);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/patients/1/doctor/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk());

    }
}
