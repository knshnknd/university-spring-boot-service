package application.services;

import application.jpa.entities.Subject;
import application.jpa.repositories.StudentRepository;
import application.jpa.repositories.SubjectRepository;
import application.jpa.repositories.TeacherRepository;
import application.jpa.repositories.WorkshopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataPreloadService {
    private StudentRepository studentRepository;
    private SubjectRepository subjectRepository;
    private TeacherRepository teacherRepository;
    private WorkshopRepository workshopRepository;

    @Autowired
    public DataPreloadService(StudentRepository studentRepository, SubjectRepository subjectRepository,
                              TeacherRepository teacherRepository, WorkshopRepository workshopRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.workshopRepository = workshopRepository;
    }

    public void preloadData() {
        preloadSubjects();
    }

    private void preloadSubjects() {
        List<Subject> subjectList =List.of(
                new Subject("Математический анализ"),
                new Subject("Экономика"),
                new Subject("Алгоритмизация и программирование"),
                new Subject("История Древнего мира")
        );

        subjectRepository.saveAll(subjectList);
    }
}
