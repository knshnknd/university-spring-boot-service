package application.services;

import application.jpa.entities.Student;
import application.jpa.entities.StudentGroup;
import application.jpa.repositories.StudentRepository;
import application.util.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private static final String STUDENT_NOT_FOUND_ERROR_MESSAGE = "Student with this ID was not found.";

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

    public Student findOne(Integer id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        return studentOptional.orElseThrow(() -> new EntityNotFoundException(STUDENT_NOT_FOUND_ERROR_MESSAGE));
    }

    @Transactional
    public void save(Student student) {
        enrichStudent(student);
        studentRepository.save(student);
    }

    public Optional<Student> findByName(String name) {
        return studentRepository.findStudentByStudentFullName(name);
    }

    @Transactional
    public void update(int id, Student student) {
        student.setStudentId(id);
        enrichStudent(student);
        studentRepository.save(student);
    }

    @Transactional
    public void delete(int id) {
        studentRepository.deleteStudentByStudentId(id);
    }

    private void enrichStudent(Student student) {
        // Ищем студенческую группу из БД по ID, которое пришло из JSON,
        // и вставляем объект из Hibernate persistence context
        student.setStudentGroup(studentGroupService.findById(student.getStudentGroup().getStudentGroupId()).get());
    }
}
