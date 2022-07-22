package application.dto.responses;

import application.jpa.entities.Student;

import java.util.List;

public class StudentResponseDto {
    private List<Student> students;

    public StudentResponseDto(List<Student> students) {
        this.students = students;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
