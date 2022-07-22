package application.services;

import application.jpa.entities.*;
import application.jpa.repositories.*;
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
class WorkshopServiceTest {

    @MockBean
    private WorkshopRepository workshopRepository;
    @MockBean
    private StudentGroupRepository studentGroupRepository;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private SubjectRepository subjectRepository;
    @MockBean
    private TeacherRepository teacherRepository;
    @MockBean
    private WorkshopLocationRepository workshopLocationRepository;

    @Autowired
    private WorkshopService workshopService;

    @Test
    void findAllTest() {
        List<Workshop> workshopList = new ArrayList<>(List.of(
                new Workshop(1L)));

        given(workshopRepository.findAll()).willReturn(workshopList);
        List<Workshop> workshops = workshopService.findAll();

        assertThat(workshopList).isEqualTo(workshops);
        assertThat(workshops.size()).isEqualTo(1L);

        verify(workshopRepository).findAll();
    }

    @Test
    void findOneTest() {
        Workshop workshop = new Workshop(1L);

        given(workshopRepository.findById(1L))
                .willReturn(Optional.of(new Workshop(1L)));

        workshopService.findOne(1L);
        assertThat(workshop.getWorkshopId()).isEqualTo(1L);
    }

    @Test
    void saveTest() {
        Subject subject = new Subject(1, "Mathematics");
        Teacher teacher = new Teacher(1, "John Smith", "PhD");
        WorkshopLocation workshopLocation = new WorkshopLocation(1, "Main building, auditorium 444");
        List<Student> students = new ArrayList<>(List.of(
                new Student(1, "Jake Cooper", new StudentGroup(1, "Group 1"))
        ));

        Workshop workshop = new Workshop(subject, workshopLocation, teacher, new Date(1000), students);

        when(workshopRepository.save(any(Workshop.class))).thenReturn(
                new Workshop(subject, workshopLocation, teacher, new Date(100), students)
        );

        given(subjectRepository.findById(1))
                .willReturn(Optional.of(new Subject(1, "Mathematics")));

        given(teacherRepository.findById(1))
                .willReturn(Optional.of(new Teacher(1, "John Smith", "PhD")));

        given(workshopLocationRepository.findById(1))
                .willReturn(Optional.of(new WorkshopLocation(1, "Main building, auditorium 444")));

        given(studentGroupRepository.findById(1))
                .willReturn(Optional.of(new StudentGroup(1, "Group 1")));

        given(studentRepository.findById(1))
                .willReturn(Optional.of(new Student(1, "Jake Cooper",
                        new StudentGroup(1, "Group 1"))));


        Workshop created = workshopService.save(workshop);
        assertThat(created.getWorkshopId()).isSameAs(workshop.getWorkshopId());
    }

    @Test
    void updateTest() {
        Subject subject = new Subject(1, "Mathematics");
        Teacher teacher = new Teacher(1, "John Smith", "PhD");
        WorkshopLocation workshopLocation = new WorkshopLocation(1, "Main building, auditorium 444");
        List<Student> students = new ArrayList<>(List.of(
                new Student(1, "Jake Cooper", new StudentGroup(1, "Group 1"))
        ));

        Workshop workshop = new Workshop(subject, workshopLocation, teacher, new Date(1000), students);

        Workshop newWorkshop = new Workshop(subject, workshopLocation, teacher, new Date(2000), students);

        given(workshopRepository.findById(workshop.getWorkshopId()))
                .willReturn(Optional.of(workshop));

        given(subjectRepository.findById(1))
                .willReturn(Optional.of(new Subject(1, "Mathematics")));

        given(teacherRepository.findById(1))
                .willReturn(Optional.of(new Teacher(1, "John Smith", "PhD")));

        given(workshopLocationRepository.findById(1))
                .willReturn(Optional.of(new WorkshopLocation(1, "Main building, auditorium 444")));

        given(studentGroupRepository.findById(1))
                .willReturn(Optional.of(new StudentGroup(1, "Group 1")));

        given(studentRepository.findById(1))
                .willReturn(Optional.of(new Student(1, "Jake Cooper",
                        new StudentGroup(1, "Group 1"))));

        workshopService.update(workshop.getWorkshopId(), newWorkshop);

        verify(workshopRepository).save(newWorkshop);
        verify(workshopRepository).findById(workshop.getWorkshopId());
    }

    @Test
    void deleteTest() {
        Workshop workshop = new Workshop(1L);
        when(workshopRepository.findById(workshop.getWorkshopId()))
                .thenReturn(Optional.of(workshop));

        workshopService.delete(workshop.getWorkshopId());

        verify(workshopRepository).deleteById(workshop.getWorkshopId());
    }
}