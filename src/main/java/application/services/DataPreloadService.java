package application.services;

import application.jpa.entities.StudentGroup;
import application.jpa.entities.Subject;
import application.jpa.entities.Teacher;
import application.jpa.entities.WorkshopLocation;
import application.jpa.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DataPreloadService {
    private StudentRepository studentRepository;
    private StudentGroupRepository studentGroupRepository;
    private SubjectRepository subjectRepository;
    private TeacherRepository teacherRepository;
    private WorkshopRepository workshopRepository;
    private WorkshopLocationRepository workshopLocationRepository;

    @Autowired
    public DataPreloadService(StudentRepository studentRepository, StudentGroupRepository studentGroupRepository,
                              SubjectRepository subjectRepository, TeacherRepository teacherRepository,
                              WorkshopRepository workshopRepository, WorkshopLocationRepository workshopLocationRepository) {
        this.studentRepository = studentRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.workshopRepository = workshopRepository;
        this.workshopLocationRepository = workshopLocationRepository;
    }

    public void preloadData() {
        preloadSubjects();
        preloadTeachers();
        preloadWorkshopLocations();
        preloadStudentGroups();
    }

    private void preloadTeachers() {
        List<Teacher> teacherList = List.of(
                new Teacher("John Smith", "PhD in history"),
                new Teacher("Bob Smith", "MBA"),
                new Teacher("Jake Johnson", "PhD in medicine"),
                new Teacher("William I the Conqueror", "MBA")
        );

        teacherRepository.saveAll(teacherList);
    }

    private void preloadWorkshopLocations() {
        List<WorkshopLocation> workshopLocationList = List.of(
                new WorkshopLocation("Main building, auditorium 205"),
                new WorkshopLocation("Main building, auditorium 122"),
                new WorkshopLocation("Main building, gym"),
                new WorkshopLocation("Library, auditorium 12")
        );

        workshopLocationRepository.saveAll(workshopLocationList);
    }

    private void preloadSubjects() {
        List<Subject> subjectList = List.of(
                new Subject("Математический анализ"),
                new Subject("Экономика"),
                new Subject("Алгоритмизация и программирование"),
                new Subject("История Древнего мира")
        );

        subjectRepository.saveAll(subjectList);
    }

    private void preloadStudentGroups() {
        List<StudentGroup> studentGroups = List.of(
                new StudentGroup("B-History-2022"),
                new StudentGroup("B-Mathematics&IT-2022"),
                new StudentGroup("M-LanguageScience-2022")
        );

        studentGroupRepository.saveAll(studentGroups);
    }
}
