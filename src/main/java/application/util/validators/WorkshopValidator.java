package application.util.validators;

import application.jpa.entities.Student;
import application.jpa.entities.Workshop;
import application.services.StudentGroupService;
import application.services.SubjectService;
import application.services.TeacherService;
import application.services.WorkshopLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class WorkshopValidator implements Validator {

    private final SubjectService subjectService;
    private final TeacherService teacherService;
    private final WorkshopLocationService workshopLocationService;

    @Autowired
    public WorkshopValidator(SubjectService subjectService, TeacherService teacherService,
                             WorkshopLocationService workshopLocationService) {
        this.subjectService = subjectService;
        this.teacherService = teacherService;
        this.workshopLocationService = workshopLocationService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Workshop.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Workshop workshop = (Workshop) target;

        // equals??
        if (workshop.getWorkshopDate() == null || workshop.getWorkshopDate().equals("")) {
            errors.rejectValue("date", "", "The date of the workshop must not be empty.");;
        }

        if (subjectService.findById(workshop.getSubject().getSubjectId()).isEmpty()) {
            errors.rejectValue("subject", "There is no such subject.");
        }

        if (teacherService.findById(workshop.getTeacher().getTeacherId()).isEmpty()) {
            errors.rejectValue("teacher", "There is no such teacher.");
        }

        if (workshopLocationService.findById(workshop.getWorkshopLocation().getWorkshopLocationId()).isEmpty()) {
            errors.rejectValue("workshopLocation", "There is no such workshop location.");
        }
    }
}
