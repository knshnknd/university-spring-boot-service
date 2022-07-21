package application.dto;

import java.util.List;

public class StudentResponse {
    private List<StudentDTO> students;

    public StudentResponse(List<StudentDTO> students) {
        this.students = students;
    }

    public List<StudentDTO> getStudents() {
        return students;
    }

    public void setStudents(List<StudentDTO> students) {
        this.students = students;
    }
}
