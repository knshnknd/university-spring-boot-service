package application.dto;

import application.jpa.entities.Subject;

import java.util.List;

public class SubjectResponse {
    private List<Subject> subjects;

    public SubjectResponse(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}
