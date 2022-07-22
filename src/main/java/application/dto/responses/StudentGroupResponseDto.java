package application.dto.responses;

import application.jpa.entities.StudentGroup;

import java.util.List;

public class StudentGroupResponseDto {
    private List<StudentGroup> studentGroups;

    public StudentGroupResponseDto(List<StudentGroup> studentGroups) {
        this.studentGroups = studentGroups;
    }

    public List<StudentGroup> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(List<StudentGroup> studentGroups) {
        this.studentGroups = studentGroups;
    }
}
