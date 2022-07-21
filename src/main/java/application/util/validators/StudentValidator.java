package application.util.validators;

import application.jpa.entities.Student;
import application.services.StudentGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class StudentValidator implements Validator {

    private final StudentGroupService studentGroupService;

    @Autowired
    public StudentValidator(StudentGroupService studentGroupService) {
        this.studentGroupService = studentGroupService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Student.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Student student = (Student) target;

        if (student.getStudentGroup() == null) {
            return;
        }

        if (studentGroupService.findByName(student.getStudentGroup().getStudentGroupName()).isEmpty()) {
            errors.rejectValue("studentGroupName", "Такой студенческой группы не существует.");
        }
    }
}
