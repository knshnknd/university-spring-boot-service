package application.dto;

import javax.validation.constraints.NotEmpty;

public class SubjectDTO {

    @NotEmpty(message = "Название дисциплины не должно быть пустым.")
    private String subjectName;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
