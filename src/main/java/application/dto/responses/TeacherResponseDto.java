package application.dto.responses;

import application.jpa.entities.Teacher;

import java.util.List;

public class TeacherResponseDto {
    private List<Teacher> teachers;

    public TeacherResponseDto(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }
}
