package application.controllers;

import application.jpa.entities.Subject;
import application.services.SubjectService;
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
class SubjectControllerTest {

    public static final String APPLICATION_JSON = "application/json";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubjectService subjectService;

    @Test
    void getAll() throws Exception {
        given(subjectService.findAll()).willReturn(
                new ArrayList<>(List.of(
                        new Subject(1, "Math"),
                        new Subject(2, "Literature")
                )));

        mockMvc.perform(get("/subjects"))
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getOne() throws Exception {
        given(subjectService.findOne(1)).willReturn(new Subject(1, "Math"));

        mockMvc.perform(get("/subjects/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
    }

    @Test
    void createUpdateDelete() throws Exception {
        Subject subject = new Subject("Math");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(subject);

        mockMvc.perform(post("/subjects/").contentType(APPLICATION_JSON).content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));

        subject.setSubjectName("Literature");

        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow2 = mapper.writer().withDefaultPrettyPrinter();
        String requestJson2 = ow2.writeValueAsString(subject);

        mockMvc.perform(patch("/subjects/1").contentType(APPLICATION_JSON).content(requestJson2))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));

        mockMvc.perform(delete("/subjects/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
    }
}