package application.dto;

import application.jpa.entities.Student;

import java.util.List;

public class StudentResponse {
    private List<Student> students;

    public StudentResponse(List<Student> students) {
        this.students = students;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
