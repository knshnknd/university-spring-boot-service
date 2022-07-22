package application.services;

import application.jpa.entities.StudentGroup;
import application.jpa.repositories.StudentGroupRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class StudentGroupServiceTest {

    @Autowired
    private StudentGroupService studentGroupService;

    @MockBean
    private StudentGroupRepository studentGroupRepository;

    @Test
    void findAll() {
    }

    @Test
    void findOne() {
        given(studentGroupService.findOne(1)).willReturn(
                new StudentGroup("Group 1"));

        StudentGroup studentGroup = studentGroupService.findOne(1);

        assertEquals(1, studentGroup.getStudentGroupId());
    }

    @Test
    void findByName() {
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
        StudentGroup studentGroup = new StudentGroup("Group 1");
        studentGroupService.save(studentGroup);

        assertNotNull(studentGroup.getStudentGroupName());

        Mockito.verify(studentGroupRepository, Mockito.times(1)).save(studentGroup);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}