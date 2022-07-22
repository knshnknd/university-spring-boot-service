package application.validators;

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

        if (student.getStudentFullName() == null || student.getStudentFullName().equals("")) {
            errors.rejectValue("studentFullName", "",
                    "The name of the student must not be empty.");;
        }

        // Обработку на одинаковые ФИО не делаем, потому что могут существовать студенты с одинаковыми ФИО

        if (studentGroupService.findById(student.getStudentGroup().getStudentGroupId()).isEmpty()) {
            errors.rejectValue("studentGroup", "There is no such student group.");
        }
    }
}
