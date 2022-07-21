package application.dto;

import application.jpa.entities.StudentGroup;

import java.util.List;

public class StudentGroupResponse {
    private List<StudentGroup> studentGroups;

    public StudentGroupResponse(List<StudentGroup> studentGroups) {
        this.studentGroups = studentGroups;
    }

    public List<StudentGroup> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(List<StudentGroup> studentGroups) {
        this.studentGroups = studentGroups;
    }
}
