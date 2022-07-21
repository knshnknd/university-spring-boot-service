package application.services;

import application.jpa.entities.StudentGroup;
import application.jpa.entities.Subject;
import application.jpa.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
                new StudentGroup("ОБ-ИОБ-2022"),
                new StudentGroup("ОБ-ИИС-2022"),
                new StudentGroup("ОБ-ИИН-2022")
        );

        studentGroupRepository.saveAll(studentGroups);
    }
}
