package application.controllers;

import application.jpa.entities.Teacher;
import application.jpa.entities.WorkshopLocation;
import application.services.TeacherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TeacherControllerTest {

    public static final String APPLICATION_JSON = "application/json";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherService teacherService;

    @Test
    void getAll() throws Exception {
        given(teacherService.findAll()).willReturn(
                new ArrayList<>(List.of(
                        new Teacher(1, "John Smith", "MBA"),
                        new Teacher(2, "Katy Perry", "PhD")
                )));

        mockMvc.perform(get("/teachers"))
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getOne() throws Exception {
        given(teacherService.findOne(1)).willReturn(
                new Teacher(1, "John Smith", "MBA"));

        mockMvc.perform(get("/teachers/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
    }

    @Test
    void createUpdateDelete() throws Exception {
        Teacher teacher = new Teacher(1, "John Smith", "MBA");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(teacher);

        mockMvc.perform(post("/teachers").contentType(APPLICATION_JSON).content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));

        teacher.setTeacherFullName("Katy Perry");
        teacher.setTeacherScienceDegree("PhD");

        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow2 = mapper.writer().withDefaultPrettyPrinter();
        String requestJson2 = ow2.writeValueAsString(teacher);

        mockMvc.perform(patch("/teachers/1").contentType(APPLICATION_JSON).content(requestJson2))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));

        mockMvc.perform(delete("/teachers/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
    }
}