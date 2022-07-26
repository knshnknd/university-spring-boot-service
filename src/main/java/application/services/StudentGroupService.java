package application.services;

import application.jpa.entities.StudentGroup;
import application.jpa.repositories.StudentGroupRepository;
import application.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class    StudentGroupService {
    private static final String STUDENT_GROUP_NOT_FOUND_ERROR_MESSAGE = "Student group with this ID was not found.";

    private final StudentGroupRepository studentGroupRepository;

    @Autowired
    public StudentGroupService(StudentGroupRepository studentGroupRepository) {
        this.studentGroupRepository = studentGroupRepository;
    }

    public List<StudentGroup> findAll() {
        return studentGroupRepository.findAll();
    }

    public StudentGroup findOne(Integer id) {
        Optional<StudentGroup> studentGroupOptional = studentGroupRepository.findById(id);
        return studentGroupOptional.orElseThrow(() -> new EntityNotFoundException(STUDENT_GROUP_NOT_FOUND_ERROR_MESSAGE));
    }

    public Optional<StudentGroup> findByStudentGroupName(String name) {
        return studentGroupRepository.findStudentGroupByStudentGroupName(name);
    }

    public Optional<StudentGroup> findOptionalById(Integer id) {
        return studentGroupRepository.findById(id);
    }

    public StudentGroup save(StudentGroup studentGroup) {
        studentGroupRepository.save(studentGroup);
        return studentGroup;
    }

    public void update(int id, StudentGroup studentGroup) {
        studentGroup.setStudentGroupId(findOne(id).getStudentGroupId());
        studentGroupRepository.save(studentGroup);
    }

    public void delete(int id) {
        studentGroupRepository.deleteById(id);
    }

}
