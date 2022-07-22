package application.controllers;

import application.dto.StudentGroupResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebMvcTest
class StudentGroupControllerIT {
    TestRestTemplate restTemplate = new TestRestTemplate();

    @Autowired
    // private MockMvc mvc;

    @Test
    void getAll() {

//        ResponseEntity<StudentGroupResponse> responseEntity = restTemplate.exchange("http://localhost:8080/student_groups",
//                HttpMethod.GET, null, new ParameterizedTypeReference<StudentGroupResponse>() {});

        // StudentGroupResponse studentGroupResponse = responseEntity.getBody();

        // Validate
//        assert studentGroupResponse != null;
//        assertThat(studentGroupResponse.getStudentGroups().size(), is(0));
    }

    @Test
    void getOne() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void handleException() {
    }

    @Test
    void testHandleException() {
    }
}