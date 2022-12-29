package com.intlsos.crud.example.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.intlsos.crud.example.DoctorPatientCrudRestApiSpringBootMysqlApplication;
import com.intlsos.crud.example.entity.Doctor;
import com.intlsos.crud.example.repository.DoctorRepository;
import com.intlsos.crud.example.service.DoctorServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = DoctorPatientCrudRestApiSpringBootMysqlApplication.class)
@AutoConfigureMockMvc
public class DoctorServiceImplTest {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorServiceImpl doctorService;

    Doctor doctor = new Doctor();
    Doctor doctor2 = new Doctor();
    List<Doctor> doctorList = new ArrayList<>();


    @BeforeEach
    public void setup() {
        doctor.setId(1);
        doctor.setName("Simha");

        doctor2.setId(2);
        doctor2.setName("Narasimha");
        doctorList.addAll(List.of(doctor,doctor2));
    }

    @Test
    public void saveDoctorTest() {
        when(doctorRepository.save(doctor)).thenReturn(doctor);
        Doctor saveDoctor = doctorService.saveDoctor(doctor);
        Assert.assertNotNull(saveDoctor);
    }

    @Test
    public void listAllDoctorsTest() {
        when(doctorRepository.findAll()).thenReturn(doctorList);
        List<Doctor> saveDoctor = doctorService.listAllDoctors();
        Assert.assertEquals(2,saveDoctor.size());
    }

    @Test
    public void getDoctorByIdTest() {
        when(doctorRepository.findById(1)).thenReturn(Optional.ofNullable(doctor));
        Doctor saveDoctor = doctorService.getDoctorById(1);
        Assert.assertEquals("Simha",saveDoctor.getName());
    }

    @Test
    public void updateDoctorNameByIdTest() {
        when(doctorRepository.findById(1)).thenReturn(Optional.ofNullable(doctor));
        Doctor saveDoctor = doctorService.getDoctorById(1);
        saveDoctor.setName("Jhon");
        when(doctorRepository.save(saveDoctor)).thenReturn(saveDoctor);
        Assert.assertEquals("Jhon",saveDoctor.getName());
    }

  /*  @Test
    public void deleteDoctorByIdTest() {
        when(doctorRepository.findById(1)).thenReturn(Optional.ofNullable(doctor));
        Doctor saveDoctor = doctorService.getDoctorById(1);
        when(doctorRepository.deleteById(saveDoctor.getId())).then(void)

        verify(doctorRepository).deleteById(saveDoctor.getId());

    }*/

}
