package application.dto;

import application.jpa.entities.StudentGroup;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class StudentDTO {

    @NotEmpty(message = "ФИО студента не может быть пустым.")
    private String studentFullName;

    @NotNull
    private StudentGroup studentGroup;

    public String getStudentFullName() {
        return studentFullName;
    }

    public void setStudentFullName(String studentFullName) {
        this.studentFullName = studentFullName;
    }

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }
}
