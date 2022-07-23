package application.validators;

import application.jpa.entities.Teacher;
import application.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TeacherValidator implements Validator {

    private final TeacherService teacherService;

    @Autowired
    public TeacherValidator(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Teacher.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Teacher teacher = (Teacher) target;

        // Обработку на одинаковые ФИО не делаем, потому что могут существовать преподаватели с одинаковыми ФИО

        if (teacher.getTeacherFullName() == null || teacher.getTeacherFullName().equals("")) {
            errors.rejectValue("teacherFullName", "",
                    "The name of the teacher must not be empty.");;
        }

        if (teacher.getTeacherScienceDegree() == null || teacher.getTeacherScienceDegree().equals("")) {
            errors.rejectValue("teacherScienceDegree", "",
                    "The science degree of the teacher must not be empty.");;
        }
    }
}
