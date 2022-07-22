package application.services;

import application.jpa.entities.Student;
import application.jpa.entities.Workshop;
import application.jpa.repositories.StudentRepository;
import application.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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

    public Optional<Student> findById(Integer id) {
        return studentRepository.findById(id);
    }

    @Transactional
    public void save(Student student) {
        enrichStudent(student);
        studentRepository.save(student);
    }

    @Transactional
    public void update(int id, Student student) {
        student.setStudentId(findOne(id).getStudentId());
        enrichStudent(student);
        studentRepository.save(student);
    }

    @Transactional
    public void delete(int id) {
        studentRepository.deleteById(id);
    }

    @Transactional
    public List<Workshop> getWorkshopsByDate(int id, Date date) {
        return new ArrayList<>(findOne(id).getWorkshops()
                .stream()
                .filter(workshop -> date.equals(workshop.getWorkshopDate()))
                .toList());
    }

    private void enrichStudent(Student student) {
        // Ищем студенческую группу из БД по ID, которое пришло из JSON,
        // и вставляем объект из Hibernate persistence context
        student.setStudentGroup(studentGroupService.findById(student.getStudentGroup().getStudentGroupId()).get());
    }
}
