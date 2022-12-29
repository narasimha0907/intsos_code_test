package com.intlsos.crud.example.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intlsos.crud.example.DoctorPatientCrudRestApiSpringBootMysqlApplication;
import com.intlsos.crud.example.entity.Doctor;
import com.intlsos.crud.example.entity.Patient;
import com.intlsos.crud.example.exception.ResourceNotFoundException;
import com.intlsos.crud.example.repository.DoctorRepository;
import com.intlsos.crud.example.service.DoctorService;
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
public class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorService doctorService;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private DoctorRepository doctorRepository;

    List<Patient> patientList = new ArrayList<>();
    List<Doctor> doctorList = new ArrayList<>();

    @BeforeEach
    public void setup () {
        Patient patient1 = new Patient();
        patient1.setId(1);
        patient1.setName("Hari");
        patient1.setDisease("Fever");
        patient1.setDateOfBirth(new Date());

        Patient patient2 = new Patient();
        patient2.setId(2);
        patient2.setName("Suman");
        patient2.setDisease("Knee Pain");
        patient2.setDateOfBirth(new Date());

        patientList.add(patient1);
        patientList.add(patient2);

        Doctor doctor = new Doctor();
        doctor.setId(1);
        doctor.setName("Jhon");
        doctor.setPatients(patientList);

        Doctor doctor2 = new Doctor();
        doctor2.setId(2);
        doctor2.setName("Kevin");
        doctor2.setPatients(patientList);
        doctorList.add(doctor);
        doctorList.add(doctor2);
    }
    @Test
    public void listAllDoctorsTest() throws Exception {
        when(doctorService.listAllDoctors()).thenReturn(doctorList);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/doctors/list")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$",hasSize(2)))
            .andExpect(jsonPath("$[0].id",is(1)))
            .andExpect(jsonPath("$[0].name",is("Jhon")))
            .andExpect(jsonPath("$[1].id",is(2)))
            .andExpect(jsonPath("$[1].name",is("Kevin")));
    }

    @Test
    public void listAllDoctorsBadRequestTest() throws Exception {
        when(doctorService.listAllDoctors()).thenReturn(doctorList);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/doctors/list123")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    public void saveDoctorTest() throws Exception {
        Doctor doctor = new Doctor();
        doctor.setId(2);
        doctor.setName("Simha");
        when(doctorService.saveDoctor(doctor)).thenReturn(doctor);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/doctors/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(doctor)))
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id",is(2)))
            .andExpect(jsonPath("$.name",is("Simha")));
    }

    @Test
    public void saveDoctorMethodNotAllowedTest() throws Exception {
        Doctor doctor = new Doctor();
        doctor.setId(2);
        doctor.setName("Simha");
        when(doctorService.saveDoctor(doctor)).thenReturn(doctor);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/doctors/ac")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(doctor)))
            .andDo(print())
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void getDoctorByIdTest() throws Exception {
        Patient patient1 = new Patient();
        patient1.setId(1);
        patient1.setName("Hari");
        patient1.setDisease("Fever");
        patient1.setDateOfBirth(new Date());
        Doctor doctor = new Doctor();
        doctor.setId(2);
        doctor.setName("Simha");
        doctor.setPatients(List.of(patient1));
        when(doctorService.getDoctorById(2)).thenReturn(doctor);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/doctors/2")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id",is(2)))
            .andExpect(jsonPath("$.name",is("Simha")));

    }

    @Test
    public void getDoctorByIdNotFoundTest() throws Exception {
        when(doctorService.getDoctorById(2)).thenThrow(new ResourceNotFoundException("Doctor", "Id", 2));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/doctors/2")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound());

    }
    @Test
    public void updateDoctorNameByIdTest() throws Exception {

        Doctor doctor1 = new Doctor();
        doctor1.setId(2);
        doctor1.setName("Reddy");
        when(doctorService.updateDoctorNameById(2,"Reddy")).thenReturn(doctor1);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/doctors/2?name=Reddy")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id",is(2)))
            .andExpect(jsonPath("$.name",is("Reddy")));
    }

    @Test
    public void updateDoctorNameByWithBadRequestIdTest() throws Exception {

        Doctor doctor1 = new Doctor();
        doctor1.setId(2);
        doctor1.setName("Reddy");
        when(doctorService.updateDoctorNameById(2,"Reddy")).thenReturn(doctor1);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/doctors/fkjf/2?name=Reddy/gfrg")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteDoctorByIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/doctors/2")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(content().string("Doctor deleted successfully."));
    }

    @Test
    public void deleteDoctorByIdBadRequestTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/doctors/2/ad")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void addPatientUnderDoctorTest() throws Exception {
        Patient patient1 = new Patient();
        patient1.setId(1);
        patient1.setName("Hari");
        patient1.setDisease("Fever");
        patient1.setDateOfBirth(new Date());
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/doctors/2/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(patient1)))
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(content().string("Patient added under doctor successfully."));
    }


    @Test
    public void addPatientUnderDoctorBadRequestTest() throws Exception {
        Patient patient1 = new Patient();
        patient1.setId(1);
        patient1.setName("Hari");
        patient1.setDisease("Fever");
        patient1.setDateOfBirth(new Date());
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/doctors/2/patient/kfafk")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(patient1)))
            .andDo(print())
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void dischargePatientTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/doctors/2/patient/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(content().string("Patient discharged successfully."));
    }

    @Test
    public void dischargePatientBadRequestTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/doctors/2/patient/1/jd")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().is4xxClientError());
    }
}
