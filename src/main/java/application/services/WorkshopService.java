package application.services;

import application.jpa.entities.Student;
import application.jpa.entities.Workshop;
import application.jpa.repositories.StudentRepository;
import application.jpa.repositories.WorkshopRepository;
import application.util.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkshopService {
    private static final String WORKSHOP_NOT_FOUND_ERROR_MESSAGE = "Workshop with this ID was not found.";

    private final WorkshopRepository workshopRepository;
    private final StudentRepository studentRepository;

    private final SubjectService subjectService;
    private final TeacherService teacherService;
    private final WorkshopLocationService workshopLocationService;

    @Autowired
    public WorkshopService(WorkshopRepository workshopRepository, StudentRepository studentRepository,
                           SubjectService subjectService, TeacherService teacherService,
                           WorkshopLocationService workshopLocationService) {
        this.workshopRepository = workshopRepository;
        this.studentRepository = studentRepository;
        this.subjectService = subjectService;
        this.teacherService = teacherService;
        this.workshopLocationService = workshopLocationService;
    }

    public List<Workshop> findAll() {
        return workshopRepository.findAll();
    }

    public Workshop findOne(Integer id) {
        Optional<Workshop> workshopOptional = workshopRepository.findById(id);
        return workshopOptional.orElseThrow(() -> new EntityNotFoundException(WORKSHOP_NOT_FOUND_ERROR_MESSAGE));
    }

    @Transactional
    public void save(Workshop workshop) {
        enrichWorkshop(workshop);
        workshopRepository.save(workshop);
    }

    @Transactional
    public void update(int id, Workshop workshop) {
        workshop.setWorkshopId(findOne(id).getWorkshopId());
        enrichWorkshop(workshop);
        workshopRepository.save(workshop);
    }

    @Transactional
    public void enroll(int workshopId, int studentId) {

    }

    @Transactional
    public void delete(int id) {
        workshopRepository.deleteById(id);
    }

    private void enrichWorkshop(Workshop workshop) {
        // Ищем нужные объекты из БД по ID, которое пришло из JSON,
        // и вставляем объект из Hibernate persistence context
        workshop.setSubject(subjectService.findById(workshop.getSubject().getSubjectId()).get());
        workshop.setTeacher(teacherService.findById(workshop.getTeacher().getTeacherId()).get());
        workshop.setWorkshopLocation(workshopLocationService.findById(workshop.getWorkshopLocation()
                                                                        .getWorkshopLocationId()).get());
    }

}
