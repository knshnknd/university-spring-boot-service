package application.validators;

import application.jpa.entities.StudentGroup;
import application.jpa.entities.Subject;
import application.services.StudentGroupService;
import application.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SubjectValidator implements Validator {
    private final SubjectService subjectService;

    @Autowired
    public SubjectValidator(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Subject.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Subject subject = (Subject) target;

        if (subject.getSubjectName() == null || subject.getSubjectName().equals("")) {
            errors.rejectValue("subjectName", "",
                    "The name of the subject must not be empty.");;
        }

        if(subjectService.findByName(subject.getSubjectName()).isPresent()) {
            errors.rejectValue("subjectName", "",
                    "A subject with that name already exists.");
        }
    }
}
