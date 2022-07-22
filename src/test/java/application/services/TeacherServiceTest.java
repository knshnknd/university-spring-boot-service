package application.services;

import application.jpa.entities.Teacher;
import application.jpa.repositories.TeacherRepository;
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
class TeacherServiceTest {

    @MockBean
    private TeacherRepository teacherRepository;
    @Autowired
    private TeacherService teacherService;

    @Test
    void findAllTest() {
        List<Teacher> teacherList = new ArrayList<>(List.of(
                new Teacher("John Smith", "MBA")));

        given(teacherRepository.findAll()).willReturn(teacherList);
        List<Teacher> teachers = teacherService.findAll();

        assertThat(teacherList).isEqualTo(teachers);
        assertThat(teachers.size()).isEqualTo(1);

        verify(teacherRepository).findAll();
    }

    @Test
    void findOneTest() {
        Teacher teacher = new Teacher(1,"John Smith", "MBA");

        given(teacherRepository.findById(1))
                .willReturn(Optional.of(new Teacher(1,"John Smith", "MBA")));

        teacherService.findOne(1);
        assertThat(teacher.getTeacherId()).isEqualTo(1);
    }

    @Test
    void findOptionalByIdTest() {
        given(teacherRepository.findById(1))
                .willReturn(Optional.of(new Teacher(1,"John Smith", "MBA")));
        Optional<Teacher> teacher = teacherService.findOptionalById(1);
        assertThat(teacher.get().getTeacherId()).isEqualTo(1);
    }

    @Test
    void saveTest() {
        Teacher teacher = new Teacher(1,"John Smith", "MBA");
        when(teacherRepository.save(any(Teacher.class))).thenReturn(
                new Teacher(1,"John Smith", "MBA"));

        Teacher created = teacherService.save(teacher);
        assertThat(created.getTeacherId()).isSameAs(teacher.getTeacherId());
    }

    @Test
    void updateTest() {
        Teacher teacher = new Teacher(1,"John Smith", "MBA");
        Teacher newTeacher = new Teacher(1,"Katy Perry", "PhD");

        given(teacherRepository.findById(teacher.getTeacherId()))
                .willReturn(Optional.of(teacher));

        teacherService.update(teacher.getTeacherId(), newTeacher);

        verify(teacherRepository).save(newTeacher);
        verify(teacherRepository).findById(teacher.getTeacherId());
    }

    @Test
    void deleteTest() {
        Teacher teacher = new Teacher(1,"John Smith", "MBA");

        when(teacherRepository.findById(teacher.getTeacherId()))
                .thenReturn(Optional.of(teacher));

        teacherService.delete(teacher.getTeacherId());

        verify(teacherRepository).deleteById(teacher.getTeacherId());
    }
}