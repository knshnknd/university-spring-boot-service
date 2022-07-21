package application.services;

import application.jpa.entities.Student;
import application.jpa.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentGroupService studentGroupService;

    @Autowired
    public StudentService(StudentRepository studentRepository, StudentGroupService studentGroupService) {
        this.studentRepository = studentRepository;
        this.studentGroupService = studentGroupService;
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Transactional
    public void save(Student student) {
        enrichStudent(student);
        studentRepository.save(student);
    }

    private void enrichStudent(Student student) {
        // Ищем студенческую группу из БД по имени, которое пришло из JSON,
        // и вставляем объект из Hibernate persistence context
        student.setStudentGroup(studentGroupService.findByName(student.getStudentGroup().getStudentGroupName()).get());
    }
}
