package application.controllers;

import application.jpa.entities.StudentGroup;
import application.services.StudentGroupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class StudentGroupControllerTest {

    public static final String APPLICATION_JSON = "application/json";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentGroupService studentGroupService;

    @Test
    void getAll() throws Exception {
        given(studentGroupService.findAll()).willReturn(
                new ArrayList<>(List.of(
                        new StudentGroup(1, "Group 1"),
                        new StudentGroup(2, "Group 2")
                )));

        mockMvc.perform(get("/student-groups"))
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getOne() throws Exception {
        given(studentGroupService.findOne(1)).willReturn(new StudentGroup(1, "Group 1"));

        mockMvc.perform(get("/student-groups/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
    }

    @Test
    void createUpdateDelete() throws Exception {
        StudentGroup studentGroup = new StudentGroup("Group 1");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(studentGroup);

        mockMvc.perform(post("/student-groups").contentType(APPLICATION_JSON).content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));

        studentGroup.setStudentGroupName("Group 2");

        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow2 = mapper.writer().withDefaultPrettyPrinter();
        String requestJson2 = ow2.writeValueAsString(studentGroup);

        mockMvc.perform(patch("/student-groups/1").contentType(APPLICATION_JSON).content(requestJson2))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));

        mockMvc.perform(delete("/student-groups/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
    }
}