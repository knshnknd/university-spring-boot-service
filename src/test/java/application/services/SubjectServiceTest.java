package application.services;

import application.jpa.entities.Subject;
import application.jpa.repositories.SubjectRepository;
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
class SubjectServiceTest {

    @MockBean
    private SubjectRepository subjectRepository;
    @Autowired
    private SubjectService subjectService;

    @Test
    void findAllTest() {
        List<Subject> subjectList = new ArrayList<>(List.of(
                new Subject("Mathematics")));

        given(subjectRepository.findAll()).willReturn(subjectList);
        List<Subject> subjects = subjectService.findAll();

        assertThat(subjectList).isEqualTo(subjects);
        assertThat(subjects.size()).isEqualTo(1);

        verify(subjectRepository).findAll();
    }

    @Test
    void findOneTest() {
        Subject subject = new Subject(1,"Mathematics");

        given(subjectRepository.findById(1))
                .willReturn(Optional.of(new Subject(1, "Mathematics")));

        subjectService.findOne(1);
        assertThat(subject.getSubjectId()).isEqualTo(1);
    }

    @Test
    void findBySubjectNameTest() {
        given(subjectRepository.findSubjectBySubjectName("Mathematics"))
                .willReturn(Optional.of(new Subject(1, "Mathematics")));
        Subject subject = subjectService.findBySubjectName("Mathematics").get();
        assertThat(subject.getSubjectName()).isEqualTo("Mathematics");
    }

    @Test
    void findOptionalByIdTest() {
        given(subjectRepository.findById(1))
                .willReturn(Optional.of(new Subject(1, "Mathematics")));
        Optional<Subject> subject = subjectService.findOptionalById(1);
        assertThat(subject.get().getSubjectId()).isEqualTo(1);
    }

    @Test
    void saveTest() {
        Subject subject = new Subject(1,"Mathematics");
        when(subjectRepository.save(any(Subject.class))).thenReturn(
                new Subject(1,"Mathematics"));

        Subject created = subjectService.save(subject);
        assertThat(created.getSubjectId()).isSameAs(subject.getSubjectId());
    }

    @Test
    void updateTest() {
        Subject subject = new Subject(1,"Mathematics");

        Subject newSubject = new Subject(1,"Linguistics");

        given(subjectRepository.findById(subject.getSubjectId()))
                .willReturn(Optional.of(subject));

        subjectService.update(subject.getSubjectId(), newSubject);

        verify(subjectRepository).save(newSubject);
        verify(subjectRepository).findById(subject.getSubjectId());
    }

    @Test
    void deleteTest() {
        Subject subject = new Subject(1,"Mathematics");

        when(subjectRepository.findById(subject.getSubjectId()))
                .thenReturn(Optional.of(subject));

        subjectService.delete(subject.getSubjectId());

        verify(subjectRepository).deleteById(subject.getSubjectId());
    }
}