package application.services;

import application.jpa.entities.Student;
import application.jpa.entities.StudentGroup;
import application.jpa.repositories.StudentGroupRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class StudentGroupServiceTest {

    @MockBean
    private StudentGroupRepository studentGroupRepository;
    @Autowired
    private StudentGroupService studentGroupService;

    @Test
    void findAllTest() {
        given(studentGroupRepository.findAll())
                .willReturn(new ArrayList<StudentGroup>(List.of(
                        new StudentGroup(1, "Group 1"),
                        new StudentGroup(2, "Group 2")
                        )));
        List<StudentGroup> studentGroups = studentGroupService.findAll();
        assertThat(studentGroups.size()).isEqualTo(2);
    }

    @Test
    void findOneTest() {
        given(studentGroupRepository.findById(1))
                .willReturn(Optional.of(new StudentGroup(1, "Group 1")));
        StudentGroup studentGroup = studentGroupService.findOne(1);
        assertThat(studentGroup.getStudentGroupId()).isEqualTo(1);
    }

    @Test
    void findByStudentGroupNameTest() {
        given(studentGroupRepository.findStudentGroupByStudentGroupName("Group 1"))
                .willReturn(Optional.of(new StudentGroup(1, "Group 1")));
        StudentGroup studentGroup = studentGroupService.findByStudentGroupName("Group 1").get();
        assertThat(studentGroup.getStudentGroupName()).isEqualTo("Group 1");
    }

    @Test
    void findOptionalByIdTest() {
        given(studentGroupRepository.findStudentGroupByStudentGroupName("Group 1"))
                .willReturn(Optional.of(new StudentGroup(1, "Group 1")));
        Optional<StudentGroup> studentGroupOptional = studentGroupService.findOptionalById(1);
        StudentGroup studentGroup = studentGroupService.findByStudentGroupName("Group 1").get();
        assertThat(studentGroup.getStudentGroupName()).isEqualTo("Group 1");
    }

    @Test
    void saveTest() {

    }

    @Test
    void updateTest() {
    }

    @Test
    void deleteTest() {
    }
}