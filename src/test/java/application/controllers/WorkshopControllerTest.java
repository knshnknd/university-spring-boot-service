package application.controllers;

import application.jpa.entities.*;
import application.services.WorkshopService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WorkshopControllerTest {

    public static final String APPLICATION_JSON = "application/json";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkshopService workshopService;

    @Test
    void getAll() throws Exception {
        Subject subject = new Subject(1, "Math");
        Teacher teacher = new Teacher(1, "John Smith", "MBA");
        WorkshopLocation workshopLocation = new WorkshopLocation(1, "Main building, auditorium 205");
        StudentGroup studentGroup = new StudentGroup("Group 1");

        List<Student> students = new ArrayList<>(List.of(
                new Student(1, "Jake Smith", studentGroup),
                new Student(2, "Lana Rey", studentGroup)
        ));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2022-07-22");

        given(workshopService.findAll()).willReturn(
                new ArrayList<>(List.of(
                        new Workshop(subject, workshopLocation, teacher, date, students),
                        new Workshop(subject, workshopLocation, teacher, date, students),
                        new Workshop(subject, workshopLocation, teacher, date, students)
                )));

        mockMvc.perform(get("/students"))
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getOne() throws Exception {
        Subject subject = new Subject(1, "Math");
        Teacher teacher = new Teacher(1, "John Smith", "MBA");
        WorkshopLocation workshopLocation = new WorkshopLocation(1, "Main building, auditorium 205");
        StudentGroup studentGroup = new StudentGroup("Group 1");

        List<Student> students = new ArrayList<>(List.of(
                new Student(1, "Jake Smith", studentGroup),
                new Student(2, "Lana Rey", studentGroup)
        ));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2022-07-22");

        given(workshopService.findOne(1L)).willReturn(new Workshop(subject, workshopLocation, teacher, date, students));

        mockMvc.perform(get("/students/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
    }
}