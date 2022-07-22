package application.services;

import application.jpa.entities.*;
import application.jpa.repositories.StudentGroupRepository;
import application.jpa.repositories.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class StudentServiceTest {

    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private StudentGroupRepository studentGroupRepository;
    @Autowired
    private StudentService studentService;

    @Test
    void findAllTest() {
        List<Student> student = new ArrayList<>(List.of(
                new Student(1, "Whoopi Goldberg")));

        given(studentRepository.findAll()).willReturn(student);
        List<Student> students = studentService.findAll();

        assertThat(student).isEqualTo(students);
        assertThat(students.size()).isEqualTo(1);

        verify(studentRepository).findAll();
    }

    @Test
    void findOneTest() {
        Student student = new Student(1, "Whoopi Goldberg");

        given(studentRepository.findById(1))
                .willReturn(Optional.of(new Student(1, "Whoopi Goldberg")));

        studentService.findOne(1);
        assertThat(student.getStudentId()).isEqualTo(1);
    }

    @Test
    void findOptionalByIdTest() {
        given(studentRepository.findById(1))
                .willReturn(Optional.of(new Student(1, "Whoopi Goldberg")));
        Optional<Student> studentOptional = studentService.findOptionalById(1);
        assertThat(studentOptional.get().getStudentId()).isEqualTo(1);
    }

    @Test
    void saveTest() {
        Student student = new Student(1, "Whoopi Goldberg",
                new StudentGroup(1, "Group 1"));
        when(studentRepository.save(any(Student.class))).thenReturn(
                new Student(1, "Whoopi Goldberg",
                        new StudentGroup(1, "Group 1")));

        given(studentGroupRepository.findById(1))
                .willReturn(Optional.of(new StudentGroup(1, "Group 1")));

        Student created = studentService.save(student);
        assertThat(created.getStudentId()).isSameAs(student.getStudentId());
    }

    @Test
    void updateTest() {
        Student student = new Student(1, "Whoopi Goldberg",
                new StudentGroup(1, "Group 1"));

        Student newStudent = new Student(1, "Sher",
                new StudentGroup(1, "Group 1"));

        given(studentRepository.findById(student.getStudentId()))
                .willReturn(Optional.of(student));

        given(studentGroupRepository.findById(1))
                .willReturn(Optional.of(new StudentGroup(1, "Group 1")));

        studentService.update(student.getStudentId(), newStudent);

        verify(studentRepository).save(newStudent);
        verify(studentRepository).findById(student.getStudentId());
    }

    @Test
    void deleteTest() {
        Student student = new Student(1, "Whoopi Goldberg");
        when(studentRepository.findById(student.getStudentId()))
                .thenReturn(Optional.of(student));

        studentService.delete(student.getStudentId());

        verify(studentRepository).deleteById(student.getStudentId());
    }

    @Test
    void getWorkshopsByDateTest() {
        List<Workshop> list = new ArrayList<>();

        given(studentRepository.findById(1))
                .willReturn(Optional.of(new Student(1, "Whoopi Goldberg",
                        new StudentGroup(1, "Group 1"), list)));

        List<Workshop> workshopList = studentService.getWorkshopsByDate(1, new Date());
        assertThat(workshopList.size()).isEqualTo(0);
    }
}