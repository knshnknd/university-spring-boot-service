package application.services;

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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class StudentGroupServiceTest {

    @MockBean
    private StudentGroupRepository studentGroupRepository;
    @Autowired
    private StudentGroupService studentGroupService;

    @Test
    void findAllTest() {
        List<StudentGroup> studentGroupList = new ArrayList<>(List.of(
                new StudentGroup(1, "Group 1")));

        given(studentGroupRepository.findAll()).willReturn(studentGroupList);
        List<StudentGroup> studentGroups = studentGroupService.findAll();

        assertThat(studentGroupList).isEqualTo(studentGroups);
        assertThat(studentGroups.size()).isEqualTo(1);

        verify(studentGroupRepository).findAll();
    }

    @Test
    void findOneTest() {
        StudentGroup studentGroup = new StudentGroup(1, "Group 1");

        given(studentGroupRepository.findById(1))
                .willReturn(Optional.of(new StudentGroup(1, "Group 1")));

        studentGroupService.findOne(1);
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
        given(studentGroupRepository.findById(1))
                .willReturn(Optional.of(new StudentGroup(1, "Group 1")));
        Optional<StudentGroup> studentGroupOptional = studentGroupService.findOptionalById(1);
        assertThat(studentGroupOptional.get().getStudentGroupId()).isEqualTo(1);
    }

    @Test
    void saveTest() {
        StudentGroup studentGroup = new StudentGroup(1, "Group 1");
        when(studentGroupRepository.save(any(StudentGroup.class))).thenReturn(
                new StudentGroup(1, "Group 1"));

        StudentGroup created = studentGroupService.save(studentGroup);
        assertThat(created.getStudentGroupId()).isSameAs(studentGroup.getStudentGroupId());
    }

    @Test
    void updateTest() {
        StudentGroup studentGroup = new StudentGroup(1, "Group 1");

        StudentGroup newStudentGroup = new StudentGroup(1, "Group 2");

        given(studentGroupRepository.findById(studentGroup.getStudentGroupId()))
                .willReturn(Optional.of(studentGroup));

        studentGroupService.update(studentGroup.getStudentGroupId(), newStudentGroup);

        verify(studentGroupRepository).save(newStudentGroup);
        verify(studentGroupRepository).findById(studentGroup.getStudentGroupId());
    }

    @Test
    void deleteTest() {
        StudentGroup studentGroup = new StudentGroup(1, "Group 1");
        when(studentGroupRepository.findById(studentGroup.getStudentGroupId()))
                .thenReturn(Optional.of(studentGroup));

        studentGroupService.delete(studentGroup.getStudentGroupId());

        verify(studentGroupRepository).deleteById(studentGroup.getStudentGroupId());
    }
}