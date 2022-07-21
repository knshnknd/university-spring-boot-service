package application.services;

import application.dto.StudentGroupDTO;
import application.jpa.entities.StudentGroup;
import application.jpa.repositories.StudentGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class StudentGroupService {
    private final StudentGroupRepository studentGroupRepository;

    @Autowired
    public StudentGroupService(StudentGroupRepository studentGroupRepository) {
        this.studentGroupRepository = studentGroupRepository;
    }

    // find one
    // find all

    // Save
    @Transactional
    public void save(StudentGroup studentGroup) {
        studentGroupRepository.save(studentGroup);
    }

    public Optional<StudentGroup> findByName(String name) {
        return studentGroupRepository.findStudentGroupByStudentGroupName(name);
    }
}
