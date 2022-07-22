package application;

import static org.assertj.core.api.Assertions.assertThat;

import application.controllers.*;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SmokeTest {
    @Autowired
    private StudentGroupController studentGroupController;
    @Autowired
    private StudentController studentController;
    @Autowired
    private WorkshopLocationController workshopLocationController;
    @Autowired
    private WorkshopController workshopController;
    @Autowired
    private SubjectController subjectController;
    @Autowired
    private TeacherController teacherController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(studentGroupController).isNotNull();
        assertThat(studentController).isNotNull();
        assertThat(workshopLocationController).isNotNull();
        assertThat(workshopController).isNotNull();
        assertThat(subjectController).isNotNull();
        assertThat(teacherController).isNotNull();
    }


}
