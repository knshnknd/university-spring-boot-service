package application.dto;

import application.jpa.entities.Teacher;

import java.util.List;

public class TeacherResponse {
    private List<Teacher> teachers;

    public TeacherResponse(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }
}
