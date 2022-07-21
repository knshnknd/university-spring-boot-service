package application.services;

import application.jpa.entities.StudentGroup;
import application.jpa.repositories.StudentGroupRepository;
import application.util.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class StudentGroupService {
    private final StudentGroupRepository studentGroupRepository;

    @Autowired
    public StudentGroupService(StudentGroupRepository studentGroupRepository) {
        this.studentGroupRepository = studentGroupRepository;
    }

    public List<StudentGroup> findAll() {
        return studentGroupRepository.findAll();
    }

    public StudentGroup findOne(Integer id) {
        Optional<StudentGroup> foundSubject = studentGroupRepository.findById(id);
        return foundSubject.orElseThrow(() -> new EntityNotFoundException("Student group with this ID was not found."));
    }

    @Transactional
    public void save(StudentGroup studentGroup) {
        studentGroupRepository.save(studentGroup);
    }

    public Optional<StudentGroup> findByName(String name) {
        return studentGroupRepository.findStudentGroupByStudentGroupName(name);
    }

    @Transactional
    public void update(int id, StudentGroup studentGroupUpdated) {
        studentGroupUpdated.setStudentGroupId(id);
        studentGroupRepository.save(studentGroupUpdated);
    }

    @Transactional
    public void delete(int id) {
        studentGroupRepository.deleteStudentGroupByStudentGroupId(id);
    }

}
