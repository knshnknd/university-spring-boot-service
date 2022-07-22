package application.controllers;

import application.jpa.entities.WorkshopLocation;
import application.services.WorkshopLocationService;
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
class WorkshopLocationControllerTest {

    public static final String APPLICATION_JSON = "application/json";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkshopLocationService workshopLocationService;

    @Test
    void getAll() throws Exception {
        given(workshopLocationService.findAll()).willReturn(
                new ArrayList<>(List.of(
                        new WorkshopLocation(1, "Main building, auditorium 205"),
                        new WorkshopLocation(2, "Library, auditorium 12")
                )));

        mockMvc.perform(get("/workshop-locations"))
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getOne() throws Exception {
        given(workshopLocationService.findOne(1)).willReturn(
                new WorkshopLocation(1, "Main building, auditorium 205"));

        mockMvc.perform(get("/workshop-locations/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
    }

    @Test
    void createUpdateDelete() throws Exception {
        WorkshopLocation workshopLocation = new WorkshopLocation("Main building, auditorium 205");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(workshopLocation);

        mockMvc.perform(post("/workshop-locations/").contentType(APPLICATION_JSON).content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));

        workshopLocation.setWorkshopLocationFullAddress("Library, auditorium 12");

        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow2 = mapper.writer().withDefaultPrettyPrinter();
        String requestJson2 = ow2.writeValueAsString(workshopLocation);

        mockMvc.perform(patch("/workshop-locations/1").contentType(APPLICATION_JSON).content(requestJson2))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));

        mockMvc.perform(delete("/workshop-locations/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
    }
}