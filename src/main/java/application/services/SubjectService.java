package application.services;

import application.jpa.entities.Subject;
import application.jpa.repositories.SubjectRepository;
import application.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SubjectService {
    private static final String SUBJECT_NOT_FOUND_ERROR_MESSAGE = "Subject with this ID was not found.";

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    public Subject findOne(Integer id) {
        Optional<Subject> subjectOptional = subjectRepository.findById(id);
        return subjectOptional.orElseThrow(() -> new EntityNotFoundException(SUBJECT_NOT_FOUND_ERROR_MESSAGE));
    }

    public Optional<Subject> findBySubjectName(String name) {
        return subjectRepository.findSubjectBySubjectName(name);
    }

    public Optional<Subject> findOptionalById(Integer id) {
        return subjectRepository.findById(id);
    }

    public Subject save(Subject subject) {
        subjectRepository.save(subject);
        return subject;
    }

    public void update(int id, Subject subject) {
        subject.setSubjectId(findOne(id).getSubjectId());
        subjectRepository.save(subject);
    }

    public void delete(int id) {
        subjectRepository.deleteById(id);
    }
}
