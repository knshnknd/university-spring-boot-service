package application.util.validators;

import application.jpa.entities.StudentGroup;
import application.services.StudentGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class StudentGroupValidator implements Validator {
    private final StudentGroupService studentGroupService;

    @Autowired
    public StudentGroupValidator(StudentGroupService studentGroupService) {
        this.studentGroupService = studentGroupService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return StudentGroup.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StudentGroup studentGroup = (StudentGroup) target;

        if (studentGroup.getStudentGroupName() == null || studentGroup.getStudentGroupName().equals("")) {
            errors.rejectValue("studentGroupName", "",
                    "The name of the student group must not be empty.");;
        }

        if(studentGroupService.findByName(studentGroup.getStudentGroupName()).isPresent()) {
            errors.rejectValue("studentGroupName", "",
                    "A student group with that name already exists.");
        }
    }
}
