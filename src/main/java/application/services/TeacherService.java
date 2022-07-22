package application.services;

import application.jpa.entities.Subject;
import application.jpa.entities.Teacher;
import application.jpa.repositories.TeacherRepository;
import application.util.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TeacherService {
    private static final String TEACHER_NOT_FOUND_ERROR_MESSAGE = "Teacher with this ID was not found.";

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    public Teacher findOne(Integer id) {
        Optional<Teacher> teacherOptional = teacherRepository.findById(id);
        return teacherOptional.orElseThrow(() -> new EntityNotFoundException(TEACHER_NOT_FOUND_ERROR_MESSAGE));
    }

    // Для поиска в WorkshopService при обогащении (enrich)
    public Optional<Teacher> findById(Integer id) {
        return teacherRepository.findById(id);
    }

    @Transactional
    public void save(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    @Transactional
    public void update(int id, Teacher teacher) {
        teacher.setTeacherId(findOne(id).getTeacherId());
        teacherRepository.save(teacher);
    }

    @Transactional
    public void delete(int id) {
        teacherRepository.deleteById(id);
    }
}