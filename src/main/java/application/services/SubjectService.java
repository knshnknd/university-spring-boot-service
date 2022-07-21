package application.services;

import application.jpa.entities.Subject;
import application.jpa.repositories.SubjectRepository;
import application.util.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SubjectService {
    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    public Subject findOne(Integer id) {
        Optional<Subject> foundSubject = subjectRepository.findById(id);
        return foundSubject.orElseThrow(() -> new EntityNotFoundException("Дисциплина с таким ID не найдена."));
    }

    @Transactional
    public void save(Subject subject) {

        // Здесь может быть обращение к методу enrich для дополнительного
        // обогащения сущности данными (например, время создания)

        subjectRepository.save(subject);
    }
}
