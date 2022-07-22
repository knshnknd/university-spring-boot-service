package application.dto.responses;

import application.jpa.entities.Subject;

import java.util.List;

public class SubjectResponseDto {
    private List<Subject> subjects;

    public SubjectResponseDto(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}
